// scripts/scaffold-entities.mjs — JDBC 反向工程从真库生成 entity 镜像（DB-First, ADR-0025/ADR-0033）
//
// 设计（镜像 saas-identity-platform-springboot/scripts/scaffold-entities.mjs）：
// - 直接读 information_schema + pg_catalog，emit Java 类到
//   src/main/java/io/xr/lab/platform/entity/Generated/<Table>.java
// - 与 saas 版的差异：lab 仓保留手写 entity/ 层（ContractEntity 等，带 @Convert
//   业务枚举类型 + Hibernate ddl-auto=validate 对真库持续校验），Generated/ 是
//   **镜像产物**（无 @Entity，不参与运行时扫描）——JPA 的 @Convert 挂在字段上，
//   子类无法对继承字段补注解，「继承 overlay」在 JPA 不成立；镜像 + validate
//   双层给出与 saas（Generated 即运行时 entity）等价的漂移防线：
//   * scaffold 产物 git diff ≠ HEAD → DB 真演进 / 本地未同步（.sh 显式 exit 1）
//   * 手写 entity 与真库不一致 → RepositoryPgTest / 启动时 validate 红
//
// 用法：
//   node scripts/scaffold-entities.mjs                       # 需 DATABASE_URL
//   DATABASE_URL=postgresql://... node scripts/scaffold-entities.mjs
//
// 前提：shared 仓已 db:migrate（lab_dev schema 与 shared src/db/schema.ts 一致）

import { createRequire } from "node:module";
import { resolve, dirname } from "node:path";
import { fileURLToPath } from "node:url";
import { writeFileSync, mkdirSync } from "node:fs";

const __dirname = dirname(fileURLToPath(import.meta.url));
const ROOT = resolve(__dirname, "..");

// 借 pg driver（springboot 是 Java 仓，没 node_modules；走 lab 家族 nextjs sibling，
// 与 shared tests/drizzle.replay.test.ts 的借链第一优先级同源）
let pg;
try {
  const requireFromNext = createRequire(
    resolve(ROOT, "../lab-management-system-nextjs/package.json"),
  );
  pg = requireFromNext("pg");
} catch {
  const requireFromRuntime = createRequire(
    resolve("/app/node_modules", "pg/package.json"),
  );
  pg = requireFromRuntime("pg");
}
const { Client } = pg;

// DATABASE_URL 必填。CLAUDE.md §2「禁止 env 默认值兜底」：连接串含 secret，
// 不允许在脚本里写字面量兜底；缺则 fail-fast 让调用者补 env / .env.local。
const DATABASE_URL = process.env.DATABASE_URL;
if (!DATABASE_URL) {
  console.error("[scaffold-entities] FATAL: DATABASE_URL 未设（CLAUDE.md §2 禁字面量兜底）");
  console.error("[scaffold-entities]        dev 加载 .env.local；prod 由 deploy 脚本注入 env-file。");
  console.error("[scaffold-entities]        fix: export DATABASE_URL='postgresql://postgres:***@host:5432/dbname'");
  process.exit(2);
}

const OUTPUT_DIR = resolve(
  ROOT,
  "src/main/java/io/xr/lab/platform/entity/Generated",
);

const EXCLUDE_TABLES = new Set([
  // Drizzle tracking — 不是业务表
  "__drizzle_migrations",
]);

// PG 类型 → Java 类型（ADR-0025 rebaseline 后 lab 表基本全 text/uuid/jsonb）
const PG_TYPE_MAP = {
  uuid: { java: "UUID", imports: ["java.util.UUID"] },
  text: { java: "String" },
  "character varying": { java: "String" },
  integer: { java: "Integer", box: true },
  smallint: { java: "Short", box: true },
  bigint: { java: "Long", box: true },
  boolean: { java: "Boolean", box: true },
  jsonb: { java: "String" },
  "timestamp without time zone": {
    java: "OffsetDateTime",
    imports: ["java.time.OffsetDateTime"],
  },
  "timestamp with time zone": {
    java: "OffsetDateTime",
    imports: ["java.time.OffsetDateTime"],
  },
  date: { java: "LocalDate", imports: ["java.time.LocalDate"] },
};

// camelCase 转换：snake_case → camelCase
function snakeToCamel(s) {
  return s.replace(/_([a-z])/g, (_, c) => c.toUpperCase());
}

// PascalCase 转换：snake_case → PascalCase
function snakeToPascal(s) {
  const camel = snakeToCamel(s);
  return camel[0].toUpperCase() + camel.slice(1);
}

// 根据 PG 列类型生成 Java 字段
function mapJavaType(pgType, udtName) {
  // 数组类型（lab 表当前无；保留与 saas 同款防御）
  if (pgType === "ARRAY") {
    const elemUdT = udtName.replace(/^_/, "");
    if (PG_TYPE_MAP[elemUdT]) {
      return {
        java: `List<String>`,
        imports: ["java.util.List"],
        columnType: `${elemUdT}[]`,
      };
    }
    throw new Error(`Unsupported array elem udt: ${elemUdT}`);
  }
  // enum 类型（lab 仅剩 audit_action）：镜像层用 String 记录类型名
  if (pgType === "USER-DEFINED") {
    return {
      java: "String",
      columnType: udtName,
      isEnum: true,
    };
  }
  const m = PG_TYPE_MAP[pgType];
  if (!m) throw new Error(`Unsupported PG type: ${pgType} (${udtName})`);
  return {
    java: m.java,
    imports: m.imports ?? [],
    columnType: m.columnDef ?? pgType,
    box: m.box,
  };
}

async function main() {
  const client = new Client({
    connectionString: DATABASE_URL,
    connectionTimeoutMillis: 10000,
  });
  await client.connect();
  console.log(`[scaffold-entities] 连接 ${DATABASE_URL.replace(/:[^:@/]+@/, ":***@")}`);

  // 1. 读所有表
  const { rows: tables } = await client.query(`
    SELECT table_name
    FROM information_schema.tables
    WHERE table_schema = 'public' AND table_type = 'BASE TABLE'
    ORDER BY table_name
  `);
  const filtered = tables.filter((t) => !EXCLUDE_TABLES.has(t.table_name));
  console.log(`[scaffold-entities] 发现 ${filtered.length} 张表`);

  // 2. 读所有列
  // 2a. 查 PK 形状（information_schema.table_constraints）
  //  - 单列 PK → 普通字段 + @Id 标记注释
  //  - 2+ 列 PK → 生成 {Table}Id @IdClass 配套类
  const { rows: pkRows } = await client.query(`
    SELECT tc.table_name, kcu.column_name, kcu.ordinal_position
    FROM information_schema.table_constraints tc
    JOIN information_schema.key_column_usage kcu
      ON tc.constraint_name = kcu.constraint_name
     AND tc.table_schema = kcu.table_schema
    WHERE tc.table_schema = 'public'
      AND tc.constraint_type = 'PRIMARY KEY'
    ORDER BY tc.table_name, kcu.ordinal_position
  `);
  const compositePKs = new Map(); // tableName -> [columnName, ...]
  const singlePKCol = new Map();  // tableName -> columnName
  const pkColsByTable = new Map();
  for (const r of pkRows) {
    if (!pkColsByTable.has(r.table_name)) pkColsByTable.set(r.table_name, []);
    pkColsByTable.get(r.table_name).push(r.column_name);
  }
  for (const [tableName, cols] of pkColsByTable) {
    if (cols.length >= 2) compositePKs.set(tableName, cols);
    else if (cols.length === 1) singlePKCol.set(tableName, cols[0]);
  }

  for (const tbl of filtered) {
    const { rows: cols } = await client.query(
      `
      SELECT column_name, data_type, udt_name, is_nullable, column_default,
             character_maximum_length
      FROM information_schema.columns
      WHERE table_schema = 'public' AND table_name = $1
      ORDER BY ordinal_position
    `,
      [tbl.table_name],
    );

    const className = snakeToPascal(tbl.table_name);
    const pkCols = compositePKs.get(tbl.table_name) ?? null;
    const isCompositePK = pkCols != null;

    // 收集 imports（镜像类是纯 POJO：无 jakarta 注解）
    const importsSet = new Set();
    for (const c of cols) {
      const mapped = mapJavaType(c.data_type, c.udt_name);
      mapped.imports?.forEach((i) => importsSet.add(i));
    }
    const imports = Array.from(importsSet).sort();

    // 字段
    const fields = cols.map((c) => {
      const mapped = mapJavaType(c.data_type, c.udt_name);
      const camel = snakeToCamel(c.column_name);
      const nullable = c.is_nullable === "YES";
      const isPKCol =
        pkCols?.includes(c.column_name) ||
        singlePKCol.get(tbl.table_name) === c.column_name;

      const flags = [];
      if (isPKCol) flags.push("PK");
      if (!nullable) flags.push("NOT NULL");
      if (mapped.isEnum) flags.push(`enum ${mapped.columnType}`);
      else if (mapped.columnType && mapped.columnType !== "text") flags.push(mapped.columnType);
      const flagComment = flags.length ? ` // ${flags.join(", ")}` : "";

      return `  private ${mapped.java} ${camel};${flagComment}`;
    });

    // Getters / setters
    const accessors = cols
      .map((c) => {
        const camel = snakeToCamel(c.column_name);
        const mapped = mapJavaType(c.data_type, c.udt_name);
        const pascal = camel[0].toUpperCase() + camel.slice(1);
        return [
          `  public ${mapped.java} get${pascal}() {`,
          `    return ${camel};`,
          `  }`,
          ``,
          `  public void set${pascal}(${mapped.java} ${camel}) {`,
          `    this.${camel} = ${camel};`,
          `  }`,
        ].join("\n");
      })
      .join("\n\n");

    const pkComment = isCompositePK
      ? ` * 真库复合 PK：(${pkCols.join(", ")})——手写层对应 @IdClass。`
      : "";
    const java = `package io.xr.lab.platform.entity.Generated;

${imports.map((i) => `import ${i};`).join("\n")}
/**
 * DB-First 镜像：${tbl.table_name}（ADR-0025/ADR-0033）。
 * 由 scripts/scaffold-entities.mjs 从 lab_dev 真库反推生成——<b>纯 POJO 镜像，
 * 无 &#64;Entity，不参与运行时</b>；手写运行时 entity 见上级
 * io.xr.lab.platform.entity（带 &#64;Convert 业务枚举，Hibernate ddl-auto=validate
 * 对真库持续校验）。
 *
 * <p>漂移防线：bash scripts/scaffold-entities.sh 对 git diff 检测本目录，
 * DB 真演进时产物变化 → 显式 commit（DB-First 标准工作流）。
 * 字段含义见 lab-management-system-shared/src/db/schema.ts ${snakeToCamel(tbl.table_name)}。
${pkComment}
 */
public class ${className} {

${fields.join("\n")}

${accessors}
}
`;

    const outPath = resolve(OUTPUT_DIR, `${className}.java`);
    mkdirSync(OUTPUT_DIR, { recursive: true });
    writeFileSync(outPath, java);
    console.log(
      `[scaffold-entities]   → ${tbl.table_name} → entity/Generated/${className}.java (${cols.length} 字段${isCompositePK ? ", composite PK " + pkCols.join("+") : ""})`,
    );

    // composite PK → 额外生成 <Table>Id.java（镜像复合 PK 形状）
    if (isCompositePK) {
      emitIdMirror(tbl.table_name, className, pkCols, cols);
    }
  }

  await client.end();
  console.log(`[scaffold-entities] OK — ${filtered.length} 镜像类已生成到 ${OUTPUT_DIR}`);
}

/**
 * emitIdMirror — composite PK 表生成 {Table}Id.java（复合 PK 形状镜像）。
 */
function emitIdMirror(tableName, entityClassName, pkColNames, allCols) {
  const pkColInfos = pkColNames.map((colName) => {
    const col = allCols.find((c) => c.column_name === colName);
    return {
      colName,
      javaField: snakeToCamel(colName),
      javaType: mapJavaType(col.data_type, col.udt_name).java,
      import: mapJavaType(col.data_type, col.udt_name).imports?.[0],
    };
  });

  const imports = new Set(["java.io.Serializable", "java.util.Objects"]);
  for (const info of pkColInfos) {
    if (info.import) imports.add(info.import);
  }
  const sortedImports = Array.from(imports).sort();

  const fields = pkColInfos
    .map((i) => `  private ${i.javaType} ${i.javaField}; // PK 列 ${i.colName}`)
    .join("\n");

  const ctor = pkColInfos
    .map((i) => `${i.javaType} ${i.javaField}`)
    .join(", ");

  const accessors = pkColInfos
    .map((i) => {
      const pascal = i.javaField[0].toUpperCase() + i.javaField.slice(1);
      return [
        `  public ${i.javaType} get${pascal}() {`,
        `    return ${i.javaField};`,
        `  }`,
        ``,
        `  public void set${pascal}(${i.javaType} ${i.javaField}) {`,
        `    this.${i.javaField} = ${i.javaField};`,
        `  }`,
      ].join("\n");
    })
    .join("\n\n");

  const equalsBody = pkColInfos
    .map((i) => `        Objects.equals(${i.javaField}, that.${i.javaField})`)
    .join(" &&\n");

  const hashBody = pkColInfos
    .map((i) => `${i.javaField}`)
    .join(", ");

  const java = `package io.xr.lab.platform.entity.Generated;

${sortedImports.map((i) => `import ${i};`).join("\n")}
/**
 * DB-First 镜像：${tableName} 复合 PK（ADR-0025/ADR-0033）。
 * 由 scripts/scaffold-entities.mjs 从 lab_dev 真库反推生成——纯 POJO 镜像，
 * 手写层 @IdClass 见 io.xr.lab.platform.entity。
 */
public class ${entityClassName}Id implements Serializable {

  private static final long serialVersionUID = 1L;

${fields}

  public ${entityClassName}Id() {}

  public ${entityClassName}Id(${ctor}) {
${pkColInfos
      .map((i) => `    this.${i.javaField} = ${i.javaField};`)
      .join("\n")}
  }

${accessors}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ${entityClassName}Id)) return false;
    ${entityClassName}Id that = (${entityClassName}Id) o;
    return ${equalsBody};
  }

  @Override
  public int hashCode() {
    return Objects.hash(${hashBody});
  }
}
`;

  const outPath = resolve(OUTPUT_DIR, `${entityClassName}Id.java`);
  writeFileSync(outPath, java);
  console.log(
    `[scaffold-entities]   → ${tableName} → entity/Generated/${entityClassName}Id.java (PK mirror for ${pkColNames.join("+")})`,
  );
}

main().catch((err) => {
  console.error("[scaffold-entities] FATAL:", err.message);
  console.error(err.stack);
  process.exit(1);
});
