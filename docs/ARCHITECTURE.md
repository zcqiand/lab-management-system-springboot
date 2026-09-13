# lab-management-system-springboot Architecture

> lab-management-system 7 仓家族的后端 B（端口 5205）。本文档聚焦「这一个 Spring Boot 仓」的结构、流程、与契约仓的同步协议、以及 DB-First schema 消费协议（ADR-0025/0033）。家族级议题（multi-repo-family 拓扑 / suite 级 ADR / 跨仓同步）见 [父仓 docs/ARCHITECTURE.md](../../../docs/ARCHITECTURE.md)。

> **范围**：本文档只描述 *架构*（结构 / 边界 / 数据流 / 决策）。编码细则见 [docs/conventions/](conventions/)，单个决策的 ADR 见 [docs/adr/](adr/)，产品需求见 [docs/requirements/](requirements/)，F/I 级功能清单见 [docs/functions/function-tree.md](functions/function-tree.md)。

---

## 0. 阅读路径

| 你是… | 直接看 |
|---|---|
| 新人，30 分钟搞懂这仓 | §1 → §2.1 → §4（启动链） |
| 改 shared API 契约后来同步 | §4.1（gen-shared.sh 两步 + marker） |
| 改 shared DB schema 后来同步 | §5（DB-First schema 消费协议 → scaffold-entities.sh） |
| 排 prod 502 / 部署不通 | §4.2 → memory/springboot-env-drift-502-trap.md |
| 加新接口 / 加新表 | §3 → §5 → [docs/adr/0003-function-tree-requires-human-approval.md](../../../docs/adr/0003-function-tree-requires-human-approval.md) |
| 调试 JWT / SSO / OAuth | §3.1 → [adr/0008](adr/0008-real-backend-oauth-jwt.md) |
| 调试菜单 | §3.1 → [adr/0009](adr/0009-menus-via-lab-backend.md) |
| 想问「为什么这样设计」 | §7（决策索引）→ 对应 ADR |

---

## 1. 角色与定位

**lab-management-system-springboot** 是 lab 7 仓家族的**后端 B 实现**——Java 21 + Spring Boot 3.4 + JPA，端口 5205。DB 侧是 **DB-First 消费层**（ADR-0025/0033）：schema 真源 = shared `src/db/schema.ts`，本仓**不拥有迁移**（Flyway 已随 ADR-0033 退役），entity 镜像由 `scaffold-entities.sh` 从真库反向工程产出、入 git 做漂移检测。

```
                ┌─────────────────────────────────────────────┐
                │         契约仓 (lab-shared)                  │
                │  TypeSpec (API)   src/db/schema.ts (DB)     │
                │  emit: openapi.yaml / drizzle → pg          │
                └──────┬──────────────────┬──────────────────┘
          gen-shared.sh│                  │scaffold-entities.sh
       (openapi-generator)                (JDBC 反向工程)
                       ▼                  ▼
           api/*Api + shared/dto/*     entity/Generated/*.java（镜像 POJO）
                       └────┬────────────┘
                            ▼
              ┌────────────────────────────────────────┐
              │   lab-management-springboot (:5205)    │
              │   Java 21 + Spring Boot 3.4 + JPA      │
              │   ddl-auto=validate + HS256 JWT 真签名 │
              │   + 真 saas OAuth 2.0 直连             │
              └────────────────────────────────────────┘
                            ▼
              lab_dev / lab_prod PostgreSQL（schema 由 shared 管，本仓只读）
```

**与 saas-springboot 的关键差异**：

| 维度 | saas-springboot | lab-springboot（本仓） |
|---|---|---|
| 产品域 | 多租户 OAuth IdP | 建筑工程实验室管理系统（合同/接样/样品/报告） |
| **DB schema** | 真源 shared `src/db/schema.ts`；entities 只在 `entity/Generated/`（即运行时实体），`ddl-auto: none` | 真源同左；**手写 entity 层保留**（@Convert 业务枚举）+ `entity/Generated/` 纯 POJO 镜像，`ddl-auto: validate` |
| **Flyway** | 已退役（pom 依赖删、yml 残留 `enabled: false`） | **已退役**（pom 依赖删、yml 块删、`db/migration/` 目录 git rm） |
| 业务表 | shared OAuth 表 | lab 业务表（contracts/receipts/samples/methods …） |
| JWT 鉴权 | HS256 真验签 | **HS256 真签名**（ADR-0008，no-sso profile 兜底） |
| SSO 链路 | — | **真 OAuth 2.0 直连 saas**（SaasAuthClient + SaasMeClient） |
| 菜单数据源 | — | **走 lab 后端 `/api/auth/menus`**（ADR-0009，saas 快照 + 30min 缓存 + demo 兜底） |
| DB schema 演进 | shared 改 `schema.ts` → `db:migrate` → 仓内 scaffold | 同左（§5） |

**家族定位总结**：lab-springboot 与 saas-springboot 同构——API 侧吃契约仓 `openapi.yaml` 本地 codegen，DB 侧消费 shared `src/db/schema.ts` 的 migrate 产物。差异只在 DB 消费的**形态**：saas 用 Generated 实体整体替换手写层；lab 因 91 文件引用手写 entity（@Convert 业务枚举 + 8 junction @IdClass），采用「手写 entity 照旧运行 + Generated/ 纯 POJO 镜像做漂移防线」的双层方案（§5.1）。

---

## 2. 目录骨架

### 2.1 顶层结构

```
lab-management-system-springboot/
├── CLAUDE.md                   ← 入口：技术栈 + 禁止事项（L0 上限 60 行）
├── .harness/stack.json         ← suite 门禁读的项目自描述（项目只能声明 L1-L4）
├── docs/
│   ├── functions/function-tree.md   ← F/I 级功能清单（BASE tree 镜像 + 本仓 I 子项）
│   ├── adr/                    ← 本仓 ADR（0008 / 0009）
│   ├── design/                 ← 流程/设计
│   ├── conventions/            ← 编码细则
│   └── requirements/
├── src/
│   ├── main/java/io/xr/lab/platform/   ← 业务代码 + codegen 产物 + entity 镜像
│   ├── main/resources/application.yml   ← 默认 profile=${LAB_PROFILE:sso} + datasource
│   └── test/java/io/xr/harness/junit/  ← fn-Test harness
├── scripts/gen-shared.sh       ← API 契约同步（两步 + ADR-0026 marker）
├── scripts/scaffold-entities.sh ← DB schema 同步（四步 + drift 检测 + marker）
├── scripts/scaffold-entities.mjs ← JDBC 反向工程实现（借 lab-nextjs 的 pg driver）
├── pom.xml                     ← Spring Boot 3.4 + Spotless + SpotBugs（无 Flyway）
├── openapitools.json           ← openapi-generator-cli 版本锁
├── spotbugs-exclude.xml        ← L2 已知误报排除
├── Dockerfile                  ← multi-stage builder + runner
└── .state/                     ← session.json / gate.json / trace.json / last-gen-shared.json
```

### 2.2 Java 包结构（`src/main/java/io/xr/lab/platform/`）

```
io.xr.lab.platform/
├── App.java                       ← @SpringBootApplication 入口
├── api/                           ← ★ openapi-generator codegen 产物（gitignored）
│   ├── AuthApi.java / ContractsApi.java / ReceiptsApi.java / SamplesApi.java
│   ├── TestRecordsApi.java / CalculationMethodsApi.java / TechnicalRequirementsApi.java
│   ├── InspectionCatalogApi.java / InspectionDictionaryApi.java / ReportNamesApi.java
│   ├── ParamInterfacesApi.java / ReportFlowApi.java / SummaryApi.java
│   ├── FrontendBindMetaApi.java / ApiUtil.java / ...      ← 14 个 Api 接口
│   └── ApiUtil.java
├── controller/                    ← 手写 Controller（构造器注入；禁字段注入）
│   └── *Controller.java          ← 13 个手写实现 implements 对应 *Api
├── service/                       ← 手写业务逻辑
│   └── *Service.java             ← 14 个：Auth / Contract / Receipt / Sample / Record /
│                                    CalculationMethod / TechnicalRequirement /
│                                    Catalog / InspectionDictionary / InspectionJunction /
│                                    InspectionReportName / ParamInterface / ReportFlow /
│                                    Summary
├── repository/                    ← Spring Data JPA
│   └── *Repository.java          ← 26 CRUD + 8 junction link/unlink repository
├── entity/                        ← 手写 JPA entity（@Entity + @Table，运行时实体）
│   ├── *Entity.java              ← 26 entity（合约/接样/样品/检测/计算方法/技术要求/字典/码表）
│   ├── enums/                    ← AttributeConverter 集中地（10 个 converter +
│   │                                8 junction 复合主键 @IdClass）
│   └── Generated/                ← ★ scaffold-entities.sh 产物：25 纯 POJO 镜像 +
│                                    12 复合主键 Id 镜像（无 @Entity，不参与运行时
│                                    扫描；入 git，git diff = DB 漂移检测，§5）
├── mapper/                        ← Entity ↔ DTO 映射（手写；无 MapStruct）
│   └── *Mapper.java              ← 11 mapper（手写映射）
├── directory/                     ← UserDirectory 接口 + ConfigUserDirectory 实现
│   ├── UserDirectory.java        ← findByEmail / findById / upsert
│   └── ConfigUserDirectory.java  ← in-memory（dev 模式种子）
├── auth/
│   ├── jwt/
│   │   ├── LabJwtSigner.java           ← HS256 真签名（ADR-0008 §1）
│   │   └── NimbusLabJwtDecoderFactory.java  ← 真验签 decoder bean
│   └── sso/
│       ├── SaasAuthClient.java         ← RestClient 调 saas /oauth/{authorize,token}
│       ├── SaasMeClient.java           ← 调 saas /me/{whoami,tenants} + /me/menus
│       ├── SaasMenuMapper.java         ← saas EffectiveMenuNode → lab MenuNode
│       ├── MenuSnapshotCache.java      ← process 内 30min TTL 缓存（ADR-0009）
│       ├── SaasHttp.java / SaasAuthException.java
│       └── NoopSaasAuthClient.java     ← no-sso profile 的 in-memory 兜底（@Profile）
├── config/
│   ├── SecurityConfig.java       ← SecurityFilterChain + CORS + JwtDecoder bean
│   ├── SsoBeansConfig.java       ← @Profile 切真/Noop 客户端
│   ├── LabConfig.java / EnumConvertersConfig.java / GlobalExceptionHandler.java
└── shared/dto/                   ← ★ openapi-generator DTO 产物（与 api 包并列）
    └── *.java                    ← ~80 DTO（Contract / Receipt / Sample / ...）
```

### 2.3 资源目录（`src/main/resources/`）

```
src/main/resources/
└── application.yml                ← 默认 profile=${LAB_PROFILE:sso} + datasource + JPA(ddl-auto=validate)
```

（原 `db/migration/` Flyway replay 目录已随 ADR-0033 退役 `git rm`——schema 真源 = shared `src/db/schema.ts`，迁移由 shared 仓 `db:migrate` 负责，本仓只读不写。）

### 2.4 仓根构件

| 构件 | 路径 | 作用 |
|---|---|---|
| `pom.xml` | 仓根 | Spring Boot 3.4.1 + JDK 21（Java 17 源）+ JPA + Security + actuator；构建期接 `spotless-maven-plugin`（L1）+ `spotbugs-maven-plugin`（L2）。**无 Flyway**（原 flyway-core + flyway-database-postgresql 已删） |
| `scripts/gen-shared.sh` | scripts/ | API 契约同步两步脚本（详见 §4.1） |
| `scripts/scaffold-entities.sh` + `.mjs` | scripts/ | DB schema 同步四步脚本（详见 §5） |
| `spotbugs-exclude.xml` | 仓根 | 排除 Spring DI singleton 的 `EI_EXPOSE_REP2` 已知误报 |
| `Dockerfile` | 仓根 | `eclipse-temurin:17-jre` builder + runner；`HEALTHCHECK` 打 `/actuator/health` |
| `.harness/stack.json` | .harness/ | 项目自描述（见 §6 L1-L4 门） |
| `openapitools.json` | 仓根 | npx `@openapitools/openapi-generator-cli` 版本锁（与 shared 仓同一工具链） |
| `deploy/` | 仓根 | env-file 模板 + 健康探针脚本（同 saas-springboot v0.1.7 之后约定） |

---

## 3. 核心模块

### 3.1 安全层（`config/SecurityConfig` + `auth/`）

```
HTTP request
  ├─ CORS preflight (corsConfigurationSource) ─→ 200 OK
  ├─ 公开端点 (/api/auth/login, /refresh, /sso/**, /actuator/**) ─→ permitAll
  ├─ 业务端点 (/api/contracts, /api/receipts, ...)     ─→ anyRequest().authenticated()
  ├─ JwtAuthenticationFilter 链 ─→ 解析 Authorization: Bearer <jwt>
  └─ JwtDecoder bean (HS256 真签名 Nimbus)
        ├─ 验签 (HMAC-SHA256 + iss + exp)
        └─ 失败: 401 Bearer Token Authentication Failed
```

**关键决策**：

| 决策 | 取向 | 备注 |
|---|---|---|
| JWT 算法 | **HS256 真签名**（`LAB_JWT_SECRET` ≥32B） | ADR-0008；saas B1 `alg=none` + dev-placeholder sig 已废弃 |
| 公开端点白名单 | `/api/auth/login` + `/api/auth/refresh` + `/api/auth/sso/**` + **`/actuator/**`** | 教训（saas-springboot v0.1.7）：漏 `/actuator/**` 让 Docker HEALTHCHECK + deploy 脚本 401，看起来像「wait 太短」，根因在 SecurityConfig |
| SSO 客户端切换 | `SsoBeansConfig` 按 `@Profile` 切真 `SaasAuthClient`（`RestClient`）/ `NoopSaasAuthClient`（in-memory 假数据） | `application.yml` `spring.profiles.default: ${LAB_PROFILE:sso}`；离线 dev 显式 `LAB_PROFILE=no-sso` |
| 菜单数据源 | `SaasMeClient.listMyMenus` 在 SSO callback 瞬时拉 saas → `MenuSnapshotCache` 30min 缓存 → `AuthService.menus(claims)` 缓存优先 → miss 回退 `FALLBACK_MENUS` → 端点**永不 5xx** | ADR-0009「方案 B：saas 快照缓存 + demo 兜底」 |
| CORS | `allowCredentials=true` + `LAB_CORS_ALLOWED_ORIGINS` 解析 CSV | react(5173) + vue(5174/5173) + nextjs(3000) 三前端覆盖 |
| Session | `STATELESS`（无服务端 session；JWT 自带 claims） | 不依赖 Redis/Session |

**TenantGuard**：tenant 隔离由各 Controller 顶部调 `UserDirectory.findById(claims.sub)` + `claims.tenantId` 注入到 JPA repository 查询条件。无独立 TenantGuard 中间件（与 saas 不同——saas 有独立 TenantContext.java）。

### 3.2 Controller 层（codegen 替接口契约，本仓写业务实现）

```
shared/openapi.yaml → openapi-generator -g spring interfaceOnly
                       │
                       ▼
       io.xr.lab.platform.api.*Api.java    ← 14 个 Api 接口契约（gitignored，每次 gen-shared 重建）
                       │
                       │ implements
                       ▼
       io.xr.lab.platform.controller.*Controller.java   ← 手写实现
                       │
                       │ /api/* HTTP endpoint
                       ▼
       @RequestMapping path matches codegen 路由
```

**CLAUDE.md §2 禁止事项**：① 禁字段注入（构造器注入）；② 禁 Controller 写业务逻辑（只参数校验 + 委派 Service）；③ 禁 `catch (Exception) {}` 吞异常。

**codegen 产物清单**（来自 `scripts/gen-shared.sh` step 2 拷入）：

| Api | basepath | FunctionTree I 数 |
|---|---|---|
| `AuthApi` | `/api/auth` | 9 |
| `ContractsApi` | `/api/contracts` | 5 |
| `ReceiptsApi` | `/api/receipts` | 7（含 `/history` + `/task`） |
| `SamplesApi` | `/api/samples` | 5 |
| `TestRecordsApi` | `/api/test-records` | 11（含 `/verdict`） |
| `CalculationMethodsApi` | `/api/calculation-methods` | 5 |
| `TechnicalRequirementsApi` | `/api/technical-requirements` | 5 |
| `InspectionCatalogApi` | `/api/catalog/{models,specs,grades,brands}` | 16（4 catalog × 4 CRUD） |
| `InspectionDictionaryApi` | `/api/inspection/{specialties,objects,parameters,standards}` | 16 |
| `ReportNamesApi` | `/api/report-names` | 5 + 3 link/unlink |
| `ParamInterfacesApi` | `/api/param-interfaces` | 5 + 1 link |
| `ReportFlowApi` | `/api/receipts/flow` + `/api/receipts/flow/queue` | 2（共用 POST /flow） |
| `SummaryApi` | `/api/summary` | 2 |
| `FrontendBindMetaApi` | `/api/frontend-bind` | 1 |

### 3.3 Service 层（手写业务）

```
Controller.{verb}(...)
  ├─ 校验 (Bean Validation @Valid)
  ├─ 查 tenant / 权限（UserDirectory + claims）
  ├─ 委派 Service.{businessOperation}()
  │       ├─ Repository.find...() / save() / delete()
  │       └─ Mapper.{entityToDto}(...)
  └─ return DTO（与 codegen 生成的 io.xr.lab.shared.dto.* 同款）
```

手写 14 个 Service。每个 Service：

- **构造器注入** Repository / Mapper / UserDirectory（无字段注入）；
- **领域逻辑**：tenant 收口、复合主键校验、流程状态机推进（如 `ReportFlowService` 7 阶段状态机）；
- **事务**：默认 `@Transactional`，只读查询 `@Transactional(readOnly = true)`；
- **DTO/Entity 转换**：通过 `io.xr.lab.platform.mapper.*Mapper` 手写映射（无 MapStruct）；
- **异常**：业务错误抛自定义异常（`NotFoundException` / `ConflictException` / `FlowStatusConflictException`），由 `GlobalExceptionHandler` 映射为 HTTP 400/404/409。

### 3.4 Repository 层（Spring Data JPA）

```
Service.{operation}()
  ▼
*Repository  (interface extends JpaRepository<Entity, Key>)
  │ derived query methods + @Query 注解
  ▼
org.hibernate.Session → JDBC → Postgres driver → lab_dev / lab_prod
```

- **CRUD Repository**：26 个 entity；继承 `JpaRepository<Entity, KeyType>`；
- **junction Repository**：8 个 junction 表（4 dictionary + 3 report-name + 1 param-interface link），复合主键用 `@IdClass` + `Serializable`（防 SpotBugs `SE_NO_SERIALVERSIONID`）；
- **派生方法**：`findByIdAndTenantId` / `findByReceiptIdAndTenantId` 等 tenant 收口；
- **Custom 查询**：复合主键端点用 `@Query("...")` HQL/JPQL 返回 DTO；
- **禁止 Spec/ExampleMatcher**：所有查询写死条件，避免运行时动态拼 JPQL。

### 3.5 Model 层（`entity/` + `entity/enums/`）

`io.xr.lab.platform.entity.*Entity` 是**运行时 JPA entity**（手写，26 个）。Hibernate 不建表（`ddl-auto: validate`）——只校验 entity 与真库 schema（shared `schema.ts` 的 migrate 产物）一致。

| 模式 | 用途 | 示例 |
|---|---|---|
| 单字段 `@Id` | `UUID` 主键 | `ContractEntity.id` |
| 复合主键 `@IdClass` | 计算方法 / 技术要求 / junction 表 | `CalculationMethodEntity {inspectionObjectCode, inspectionParameterCode}` + `CalculationMethodKey implements Serializable` |
| `String` + `JsonNullable` | optional / 可空值 | `@JsonNullable<String> remark` |
| `@JdbcTypeCode(SqlTypes.JSON)` | PG `jsonb` 列 | `SampleReceiptEntity.testingBasis` (List<String>) / `flowHistory` (List<FlowHistoryEntry>) |
| `@Convert(converter = XxxConverter.class)` | PG 列 ↔ Java enum | `FlowStatus` (M03 7 阶段) / `ContractStatus` (ACTIVE/CLOSED) 等 8 个。新 SSOT 拓扑下仅 `audit_action` 还是 PG enum，其余 status 列已全为 `text`——converter 的 enum↔String 双向转换对 `text` 列照常工作 |
| `@Enumerated(STRING)` role 字段 | role-based junction（role 字段直接存大写字面） | `ObjectStandardKey.role` (TESTING/JUDGMENT) — 无需 converter |

**enum converter 集中地**：10 个 `AttributeConverter` 注册到 `EnumConvertersConfig.java`——PG 列 ↔ Java enum，DTO 端走 `@JsonValue` 落到前端 enum 同款字符串。

**`entity/Generated/`（scaffold 镜像，非运行时）**：`scaffold-entities.sh` 从真库反向工程出的 25 张表纯 POJO（无 `@Entity`，Hibernate 扫描不到）+ 12 个复合主键 Id 镜像。它们**不参与运行时**，角色是 DB-First 漂移防线（§5）——DB 没改时与 git HEAD 逐字节一致，DB 真演进时 `git diff` 显形。

### 3.6 DB schema 消费层（`entity/Generated/` + `scripts/scaffold-entities.*`）

本仓**不拥有迁移**。DB 侧协议（ADR-0025 DB-First + ADR-0033 lab 对齐）：

| 维度 | 行为 |
|---|---|
| schema 真源 | shared `src/db/schema.ts`（手写 Drizzle SSOT） |
| 物化 | shared 仓 `npm run db:generate` → `drizzle/0000_target_ddl.sql`；`db:migrate` 应用到 lab_dev/lab_prod |
| 本仓镜像 | `scripts/scaffold-entities.sh` → `entity/Generated/*.java`（入 git） |
| 漂移检测 | 脚本内 `git diff --exit-code entity/Generated/`：DB 没改 → 与 HEAD 一致（PASS）；DB 真演进 → diff = **标准工作流**，确认后 commit |
| 运行时校验 | `ddl-auto: validate`：26 手写 entity ↔ 真库 schema 每次启动校验 |
| 单测 / CT | `RepositoryPgTest`（`@Tag("pg")`）连 `lab_test`（结构 = shared schema 已 migrate），ddl-auto=validate |

---

## 4. 核心流程

### 4.1 与契约仓同步

**API 侧：`scripts/gen-shared.sh` 两步**

```
1. [shared] 改 tsp/main.tsp + git push

2. [shared] npm run build        ← emit:openapi + tsc --noEmit
   gate: python scripts/gate.py -p <shared>   ↓ exit 0

3. [本仓] bash scripts/gen-shared.sh
   ├─ step 1: (cd ../shared && npm run emit:openapi)    ← 拉新 openapi.yaml
   ├─ step 2: npx openapi-generator-cli generate -g spring \
   │       -i ../shared/generated/openapi/openapi.yaml \
   │       --library spring-boot --model-package io.xr.lab.shared.dto \
   │       --api-package io.xr.lab.shared.api \
   │       --additional-properties useTags=true,interfaceOnly=true,
   │           useBeanValidation=true,useSpringBoot3=true,dateLibrary=java8
   │     ← 14 Api 接口 + ~80 DTO 进 src/main/java/
   │     ← sed 删除 AuthState.getKind() 的协变 bug
   │     ← mvn -q spotless:apply 让 codegen 产物过 L1
   └─ 末尾: ADR-0026 marker 写 .state/last-gen-shared.json（api_synced_sha）

4. [shared] 改 src/db/schema.ts + npm run db:migrate 后：
   [本仓] bash scripts/scaffold-entities.sh              ← §5 四步

5. mvn compile + mvn spotless:apply

6. mvn spring-boot:run（§4.2 启动链）

7. python scripts/gate.py -p lab-management-system-springboot   ← suite 根跑
   ↓ exit 0 = 全绿；1 = 按修复提示回代码；2 = 停下问人

8. git commit + git tag v<X>-<YYYYMMDD>

9. [父仓] chore(submodule): 推进 lab-springboot 指针
```

**API 侧关键防护**（`gen-shared.sh` 内置）：

| 风险 | 防护 | 位置 |
|---|---|---|
| codegen 把 DTO 写到错路径 | `mkdir -p` + `rm -rf` 先清空，目录与包名一致 | step 2 末尾 |
| codegen 排版不过 L1（spotless） | 末尾 `mvn -q spotless:apply` | step 2 末尾 |
| AuthState `String getKind()` 协变不兼容 | `sed -i '/    public String getKind();$/d'` | step 2 末尾 |
| 同步信号丢失（suite staleness 报 UNKNOWN） | ADR-0026 marker（失败仅 WARN 不阻塞） | 末尾 |

### 4.2 启动链（`mvn spring-boot:run`）

```
Maven → SpringApplication.run(App.class, args)
  ├─ Profile 解析：LAB_PROFILE=${LAB_PROFILE:sso} → 真链默认；
  │     离线 dev 显式 LAB_PROFILE=no-sso
  ├─ SsoBeansConfig @Profile 判断:
  │     ├─ no-sso → 注册 NoopSaasAuthClient + NoopSaasMeClient
  │     └─ sso   → 注册 SaasAuthClient (RestClient) + SaasMeClient
  │                  → 启动时校验 4 env 缺一 fail-fast
  ├─ SecurityConfig 构建:
  │     ├─ SecurityFilterChain（permitAll /api/auth/{login,refresh,sso/**} + /actuator/**）
  │     ├─ JwtDecoder（Nimbus HS256 真签，由 LabJwtSigner 给密钥）
  │     └─ CorsConfigurationSource（解析 lab.cors.allowed-origins CSV）
  ├─ Datasource: DATABASE_URL（JDBC_URL 兜底兼容 db-env.sh，ADR-0019 缺失 throw）
  ├─ Hibernate EntityManager init（ddl-auto=validate）:
  │     ├─ 26 手写 entity 与 DB schema 一致性校验
  │     │    （schema = shared src/db/schema.ts 的 migrate 产物，本仓只读）
  │     └─ 10 个 AttributeConverter 注册
  │     └─ entity/Generated/ 纯 POJO 无 @Entity，不参与扫描
  ├─ Tomcat 启动 @ 5205（SERVER_PORT，缺省 throw）
  └─ /actuator/health → 200 OK
       Docker HEALTHCHECK + deploy 脚本 wget 探针
```

### 4.3 SSO 真链（ADR-0008）

```
Lab Frontend (5173/5174)
  │ GET /api/auth/sso/authorize?csrfState=<前端生成>
  ▼
AuthController.ssoAuthorize()
  │ 1. 原样透传 csrfState → saas
  │ 2. POST saas /api/v1/oauth/authorize  (client_id, state)  → code
  │ 3. 重定向到 saas login 页: Location: <saas>/login?...&state=<csrfState>
  ▼
Saas 前端
  │ 用户登录 → 重定向回 lab: <lab_sso_callback>/<code>&state=<csrfState>
  ▼
Lab Frontend GET /api/auth/sso/callback?code=&state=
  │ 比对 state == sessionStorage.csrfState (RFC 6749 §10.12 标准)
  │ POST /api/auth/sso/callback  { code, state }
  ▼
AuthController.ssoCallback()
  │ 1. POST saas /api/v1/oauth/token grant=authorization_code → access + refresh (saas)
  │ 2. GET saas /api/v1/me/whoami (Bearer saas-access)
  │ 3. GET saas /api/v1/me/tenants
  │ 4. SaasMeClient.listMyMenus → MenuSnapshotCache TTL 30min (ADR-0009)
  │ 5. UserDirectory.findByEmail / upsert(email, role=viewer)
  │ 6. LabJwtSigner.signHS256(sub, tenant_id, typ=access) → access JWT
  │ 7. LabJwtSigner.signHS256(sub, ..., saas_refresh_token, typ=refresh) → refresh JWT
  ▼
返回: { access_token, refresh_token, user, tenants, expires_in }
  │ 后续每请求带 Bearer <lab-access-jwt>
  ▼
JwtDecoder 验 HS256 sig + iss + exp → set SecurityContext → Controller 拿 claims
```

### 4.4 菜单数据流（ADR-0009）

SSO callback / Refresh 路径：`SaasAuthClient` 用瞬时 saas-access 调 `SaasMeClient.listMyMenus(appCode=lab-management)` → `SaasMenuMapper.mapEffectiveMenuNode()` → `MenuSnapshotCache.put(userId, MenuNode[], ttl=30min, process-internal)`（失败 → warn log，不阻塞登录）。

读路径：`GET /api/auth/menus (Bearer lab-jwt)` → `AuthService.menus(claims)`：cache hit 直返；miss → `cacheMenus` 重建失败回退 `FALLBACK_MENUS`（5 根节点 demo，原 react 静态菜单提取）→ 端点**永不 5xx**。

---

## 5. DB-First schema 消费协议（ADR-0025 / ADR-0033）

### 5.1 双层防线：手写 entity 运行 + Generated 镜像

lab 与 saas 的 DB 消费形态不同，根因在**存量手写 entity 的规模**：

| 方案 | 为什么不选 / 为什么选 |
|---|---|
| saas 式：Generated 实体即运行时实体，删手写层 | 91 文件 import `platform.entity`、48 文件用业务 enum、24 repository、23 测试文件要跟着翻——风险与收益不成比例 |
| 「继承 overlay」：Generated 带注解，手写 entity 继承补 @Convert | **不可行**——JPA `@Convert` 锚定在字段声明上，子类无法给继承字段补注解 |
| **本仓方案（已采纳）**：手写 entity 照旧运行（@Convert 业务枚举 + junction @IdClass + validate），`entity/Generated/` 出**纯 POJO 镜像**（无 @Entity）做漂移防线 | 防线等价于 saas：scaffold 出 diff = DB 演进显形；`ddl-auto=validate` 再兜一层运行时校验。**对计划的字面偏差（overlay→mirror）已在 ADR-0033 执行记录中说明** |

### 5.2 scaffold 链四步（`scripts/scaffold-entities.sh`）

```
1. node scripts/scaffold-entities.mjs
   ├─ DATABASE_URL fail-fast（缺 → exit 2）
   ├─ 借 lab-nextjs 的 pg devDep 直连真库（本仓无 node_modules）
   ├─ information_schema + pg_catalog 反向工程 25 张表
   └─ 产 entity/Generated/<Table>.java（纯 POJO，字段尾注释标 PK/NOT NULL/enum）
      + 复合 PK 的 <Table>Id.java（implements Serializable）

2. rm -f target/spotless-index && mvn spotless:apply
   （JDBC 反推产物是裸 Java，必须重排版否则 L1 拦）

3. git diff --exit-code entity/Generated/
   ├─ 无 diff → DB-First sync 绿（DB 没改，产物与 HEAD 一致 = 预期）
   └─ 有 diff → FATAL exit 1：确认 shared 已 db:migrate 后
      git add entity/Generated/ && git commit（= DB 演进落仓）

4. ADR-0026 marker → .state/last-gen-shared.json（db_synced_sha/db_synced_cmd）
```

### 5.3 演化路径（未来）

| 工单 | 行为 |
|---|---|
| shared `schema.ts` 加列 / 加表 | shared `db:generate` + `db:migrate` → 本仓 scaffold → Generated/ diff → commit + 手写 entity 补字段（validate 会强制） |
| shared `schema.ts` 改列类型 | 同上；手写 entity 类型不同则启动 validate fail，跟改 |
| 删列 / 删表 | 先查本仓 repository/entity 引用，`/tree-change` 评估功能面，再跟 shared 演进 |
| 历史包袱（V014 永久分叉 / DIVERGED_VERSIONS / baseline-on-migrate） | **已随 Flyway 退役整体消亡**——迁移编号错位问题不复存在，DB 演进只发生在 shared `schema.ts` 一个真源上 |

### 5.4 真源纪律（ADR-0029 / ADR-0033）

本仓对 DB schema **只读不写**：发现「本仓需要 ≠ shared schema」必须列候选方案（改 shared / 改本仓 / 双边协商）停下问人；ADR-0033 是本仓与 shared 之间获批准的双边改造通道，不构成日常单方面改 shared 的许可。

---

## 6. 与契约仓同步——脚本详解

### 6.1 gen-shared.sh（API 侧）两步详解

**Step 1: shared emit**

```bash
(cd "$SHARED_DIR" && npm run emit:openapi)
```

→ shared 仓 `tsp compile` → `generated/openapi/openapi.yaml`。若失败（shared openapi 缺 `state` 字段等）会**早失败**，省去 codegen 半成品。

**Step 2: 本地 codegen（openapi-generator）**

```bash
npx --yes @openapitools/openapi-generator-cli generate \
  -g spring -i "$OPENAPI" -o "$ROOT/.openapi-tmp/java" \
  --library spring-boot \
  --model-package io.xr.lab.shared.dto \
  --api-package io.xr.lab.shared.api \
  --invoker-package io.xr.lab.shared \
  --additional-properties useTags=true,interfaceOnly=true,skipDefaultInterface=true,
    useBeanValidation=true,useSpringBoot3=true,dateLibrary=java8
```

参数镜像 saas-identity-platform-springboot 的 gen-shared.sh（v0.2.0 定案）：

| 参数 | 取值 | 作用 |
|---|---|---|
| `-g spring` | — | 生成器模板 |
| `--library spring-boot` | — | spring-boot 库 |
| `interfaceOnly=true` | — | 不生成实现，只生成接口契约（手写 Controller `implements`） |
| `skipDefaultInterface=true` | — | 跳过默认 stub 实现 |
| `useSpringBoot3=true` | — | 走 jakarta.* 包（Spring Boot 3.x） |
| `dateLibrary=java8` | — | `java.time.LocalDate` 而非 joda |
| `useBeanValidation=true` | — | `@Valid` 注解 |

**codegen 修补**：`sed -i '/^    public String getKind();$/d'` 删除 AuthState 协变不兼容方法 + `mvn -q spotless:apply` 让生成器产物过 L1。

**末尾：ADR-0026 marker**（python3 heredoc）写 `.state/last-gen-shared.json` 的 `api_synced_sha` / `api_synced_at` / `api_synced_cmd`，供 suite 跨仓 staleness check 使用。失败仅 WARN（staleness 是 warning 不是 build blocker，失败时 suite 报 UNKNOWN 让 reviewer 看到）。

### 6.2 不可信的同步模式（已废弃）

| ❌ 模式 | 后果 |
|---|---|
| 手动 `cp shared/sql/migrations/*.sql` 到本仓 | Flyway 已退役，`db/migration/` 目录已删——此模式整体消亡 |
| `sed -i` 直接改 codegen 产物（api/*Api / shared/dto/*） | L1 排版错 + SpotBugs 警告；下次 gen-shared 重写 |
| 手改 `entity/Generated/*.java` | 下次 scaffold 静默重写；要改字段请走 shared `schema.ts`（§5.4 真源纪律） |
| 改 `src/main/java/io/xr/lab/platform/api/*Api.java`（gitignored 产物的 git blame 漏检） | 下次 gen-shared 静默丢失手改 |
| 通过 Maven 依赖 import shared 仓 Java client | 循环依赖；CI 跑不通 |

---

## 7. 决策索引

### 7.1 本仓 ADR（`docs/adr/`）

| 编号 | 主题 | 一句话 | 状态 |
|---|---|---|---|
| [0008](adr/0008-real-backend-oauth-jwt.md) | 真后端 OAuth 2.0 + JWT 签发 | HS256 真签（取代 B1 dev `alg=none` 占位）；SSO 真调 saas `/oauth/{authorize,token}`；refresh 内嵌 saas refresh_token claim（无 DB 持久化） | 已采纳（2026-08-19） |
| [0009](adr/0009-menus-via-lab-backend.md) | 菜单数据源切换 | 菜单走 lab 后端 `/api/auth/menus`（取代前端直连 saas）；saas 瞬时快照 + 30min 进程内缓存 + demo 兜底 | 已采纳（2026-08-25） |

### 7.2 父仓 ADR 引用

| ADR | 主题 | 本仓落地 |
|---|---|---|
| [ADR-0007](../../../docs/adr/0007-shared-sql-ssot.md) | shared 仓扩到双 SSOT | 历史基础：DB schema 真源在 shared（原 SQL 迁移目录，现 `schema.ts`）；ORM 只反射（`ddl-auto: validate`） |
| [ADR-0014](../../../docs/conventions/multi-repo-family.md#4-后端配置env-driven-单-urladr-0014) | env-driven 单 URL | `LAB_DATABASE_URL`（DATABASE_URL）/ `LAB_JWT_SECRET`（JWT_SIGNING_KEY）/ `LAB_SAAS_*` env 切部署环境 |
| [ADR-0025](../../../docs/adr/0025-db-first-drizzle-schema-ssot.md) | DB-First：Drizzle schema.ts 为 DB SSOT | 本仓退役 Flyway；`scaffold-entities.sh` 镜像链（§5） |
| [ADR-0026](../../../docs/adr/0026-last-gen-shared-staleness-marker.md) | last-gen-shared.json staleness marker | gen-shared.sh（api 类别）+ scaffold-entities.sh（db 类别）双 marker |
| [ADR-0033](../../../docs/adr/0033-lab-align-saas-transformation.md) | lab 家族对齐 saas 改造方案 | 本次改造的获批双边通道：db-first 消费 + 白名单清零 + e2e 仓 |

### 7.3 隐性 ADR（本仓 §3.1 / §4.4 落地）

| 编号 | 主题 | 一句话 |
|---|---|---|
| (implicit) HS256 真签名覆盖 alg=none | JWT 算法不可降级 | `NimbusLabJwtDecoderFactory` 强制 HMAC 验证 + iss + exp；原 B1 dev `SecurityConfig.DevJwtDecoder (@Profile("dev"))` 已删除 |
| (implicit) 安全端点白名单含 `/actuator/**` | 健康探针匿名 | 教训（saas-springboot v0.1.7）：漏这行探针 401，deploy 120 次 wget 全失败 |
| (implicit) Generated 镜像无 @Entity | 镜像不参与运行时 | scaffold 产物是纯 POJO——`@EntityScan` 扫不到，运行时实体仍是手写 26 个（§5.1） |
| (implicit) scaffold drift exit 1 | DB 演进必须显形落仓 | `scaffold-entities.sh` step 3：diff 非空即 FATAL，防 DB 演进被静默吞掉（§5.2） |
| (implicit) env fail-fast | DATABASE_URL / SERVER_PORT / JWT_SIGNING_KEY 缺失 throw | ADR-0019；无字面默认值兜底 |

---

## 8. 术语表

| 术语 | 含义 | 在本仓的位置 |
|---|---|---|
| **SSOT** | Single Source of Truth | shared 仓承担双 SSOT（API = TypeSpec，DB = `src/db/schema.ts`）；本仓 ORM 只校验（§3.5, §3.6） |
| **DB-First pull/scaffold 链** | 消费仓从真库反向工程镜像产物的同步链 | `scripts/scaffold-entities.sh` → `entity/Generated/`（§5.2） |
| **ADR-0026 marker** | `.state/last-gen-shared.json` 跨仓 staleness 标记 | gen-shared 写 api 字段；scaffold-entities 写 db 字段 |
| **entity/Generated/** | scaffold 镜像 POJO（无 @Entity，不入运行时） | §3.5 / §5.1；入 git，diff = DB 漂移检测 |
| **ddl-auto=validate** | Hibernate 只校验不建表 | `application.yml`；校验对象 = 手写 26 entity ↔ shared migrate 产物 |
| **fnTest（spring 形态）** | JUnit5 `@Tag("Mxx.Fyy.Izz")` + `Fn` 注解 + `HarnessTraceListener` 自动落 `trace.json` | `src/test/java/io/xr/harness/junit/` |
| **codegen 产物** | openapi-generator 生成的 `io.xr.lab.platform.api.*Api` + `io.xr.lab.shared.dto.*` | §3.2；git ignored，每次 gen-shared 重建 |
| **手写 Controller** | `implements *Api` 接口 + 构造器注入 Service | §3.2-3.3 |
| **AttrConv 集中地** | `io.xr.lab.platform.entity.enums.*Converter` | §3.5；10 个 converter 做 PG 列 ↔ Java enum |
| **trace.json** | 测试命中 fn-ID 的清单 | `trace_cmd` 产，禁止手写 |
| **stack.json** | 项目自描述（栈 + 门配置） | `.harness/stack.json`，本仓声明 L1-L4 |
| **TenantGuard** | tenant 隔离检查（无独立类） | 各 Controller 顶部 `claims.tenantId` 注入到 repository 查询条件 |
| **JWT 真签名** | HS256 + 强密钥，取代 B1 dev `alg=none` | ADR-0008；§3.1 |
| **dev 降级 profile** | `no-sso` profile → NoopSaasAuthClient | `LAB_PROFILE=no-sso` 显式切换（默认 sso 真链） |
| **saas 快照缓存** | `MenuSnapshotCache` process 内 30min TTL | ADR-0009；§4.4 |
| **lab_test / lab_dev / lab_prod** | 三库分工：pg 切片测试 / 开发 / 生产 | 结构统一由 shared `schema.ts` migrate；本仓只读 |

---

## 附录 A：与父仓 docs/ARCHITECTURE.md 的关系

本文档**不重复**父仓 docs/ARCHITECTURE.md 的家族级议题：

| 议题 | 看哪里 |
|---|---|
| 多仓家族 14 个仓的角色矩阵 | 父仓 §2 |
| 5 种角色（契约/Mock/前端/后端/suite）的禁止事项 | 父仓 §2.1 |
| 跨仓端到端流程图（改契约→三端同步） | 父仓 §5 |
| OAuth 2.0 + JWT（HS256）契约 | 父仓 §3.4 |
| 端口 / CORS / env 全景 | 父仓 §6 |
| suite 门禁链（L0..L5） | 父仓 §5.4 |

**本仓独有**：本仓的 §3、§4、§5、§6（具体业务层 + DB-First schema 消费协议 + 双层漂移防线）。

---

## 附录 B：与 saas-springboot 后端仓的对照

| 维度 | saas-springboot | lab-springboot（本仓） |
|---|---|---|
| **产品域** | 多租户 OAuth IdP（authorize/callback/refresh/menus） | 建筑工程实验室管理系统（合同/接样/样品/检测/报告） |
| **DB 真源** | shared `src/db/schema.ts`（ADR-0025） | 同左（ADR-0033 对齐） |
| **DB 消费形态** | `entity/Generated/` 即运行时实体（无手写层），`ddl-auto: none` | 手写 entity 运行（validate）+ `entity/Generated/` 纯 POJO 镜像（§5.1） |
| **Flyway** | 已退役（pom 依赖删；yml `enabled: false` 残留） | 已退役（pom 依赖删、yml 块删、`db/migration/` git rm——清得更彻底） |
| **JWT 算法** | HS256 真验签（Phase 2A 后统一） | **HS256 真签名**（LabJwtSigner + Nimbus）；未走过 dev `alg=none` |
| **SSO 链路** | — | **真 OAuth 2.0 直连 saas**（SaasAuthClient + SaasMeClient） |
| **菜单数据源** | — | **经 lab 后端 /api/auth/menus**（快照缓存 + demo 兜底） |
| **业务表** | shared OAuth 表（tenants/users/apps/menus） | lab 业务表（13 张主表 + 8 junction） |
| **Codegen 模式** | 同款 `openapi-generator -g spring interfaceOnly` | 同款，参数镜像 saas v0.2.0 |
| **ADR-0026 marker** | gen-shared.sh + scaffold-entities.sh | 同款（python3 heredoc 逐字对齐） |
| **默认 profile** | dev | `sso`（真链；离线 dev 显式 `LAB_PROFILE=no-sso`） |
| **env 漂移 502 风险** | 已知（memory/springboot-env-drift-502-trap.md） | 同款；ADR-0019 fail-fast 缓解 |
| **deploy 脚本读 fat jar** | `platform-<version>.jar` | 同款，本仓 artifactId=`lab-management-system-springboot` |
| **SecurityConfig `/actuator/**`** | permitAll（教训 v0.1.7） | 同款 |
| **持久化** | 无 OAuth refresh token 存储（state-cookie 含短时 nonce） | 无 OAuth refresh token 存储（saas_refresh_token 嵌进 lab refresh token JWT claim） |

---

## 附录 C：典型陷阱（cross-reference to memory/）

| 陷阱 | 解法 | 出处 |
|---|---|---|
| `SecurityConfig` 漏 `/actuator/**` | 加 `permitAll` | memory/springboot-actuator-401-deploy-loop-trap.md |
| env 漂移 → CF 502 → CORS 误诊 | 先查 VPS env-file 缺失项；改 env 必须重建容器 | memory/springboot-env-drift-502-trap.md |
| 不知道 fat jar 名 | deploy 按 artifactId 找 jar：本仓 `lab-management-system-springboot-<version>.jar` | memory/springboot-fat-jar-name.md |
| 手改 `entity/Generated/` 被下次 scaffold 静默重写 | 改字段走 shared `schema.ts`（§5.4 真源纪律）；`/tree-change` 评估功能面 | ADR-0025/0029 |
| scaffold 出 diff 就慌 | DB 没改 → 无 diff 是预期；有 diff = DB 真演进 = 标准工作流，确认 shared 已 migrate 后 commit | memory/db-first-drift-is-feature.md |
| codegen 目录错位（包名 ≠ 目录） | 目录必须与包名一致（`io.xr.lab.shared.api` → `shared/api/`）；全量编译能过但 spring-boot:run 增量编译炸 | memory/springboot-gen-shared-dir-mismatch.md |
| codegraph 工具不解析 .tsp | 看本仓 `docs/functions/function-tree.md` 就够 | memory/codegraph-typespec-mismatch.md |
| JDK HttpClient h2c 打挂 msw | RestClient 调本地明文服务 EOF 时，强制 HTTP/1.1 | memory/jdk-httpclient-h2c-breaks-msw.md |

---

> **历史注记（2026-09-13 ADR-0033 阶段一）**：本文档原 §5「V014 永久分叉管理」、§3.6「DB Migration 层」及全文 Flyway/V014/DIVERGED_VERSIONS/baseline-on-migrate 相关内容已随 Flyway 退役删除。历史细节见 git log（`db/migration/` 的最后版本与 `gen-shared.sh` 旧 step 3 cmp-abort 防护随本仓 DB-First 改造 commit 入档）。
