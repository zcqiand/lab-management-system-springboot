# lab-management-system-springboot Architecture

> lab-management-system 7 仓家族的后端 B（端口 8080）。本文档聚焦「这一个 Spring Boot 仓」的结构、流程、与契约仓的同步协议、以及 V014 永久分叉管理。家族级议题（multi-repo-family 拓扑 / suite 级 ADR / 跨仓同步）见 [父仓 docs/ARCHITECTURE.md](../../../docs/ARCHITECTURE.md)。

> **范围**：本文档只描述 *架构*（结构 / 边界 / 数据流 / 决策）。编码细则见 [docs/conventions/](conventions/)，单个决策的 ADR 见 [docs/adr/](adr/)，产品需求见 [docs/requirements/](requirements/)，F/I 级功能清单见 [docs/functions/function-tree.md](functions/function-tree.md)。

---

## 0. 阅读路径

| 你是… | 直接看 |
|---|---|
| 新人，30 分钟搞懂这仓 | §1 → §2.1 → §4（启动链） |
| 改 shared 契约后来同步 | §4.1 → §5（V014 永久分叉管理） |
| 排 prod 502 / 部署不通 | §5 → §4.2 → [memory/springboot-env-drift-502-trap.md](../../../memory/springboot-env-drift-502-trap.md) |
| 加新接口 / 加新表 | §3 → [docs/adr/0003-function-tree-requires-human-approval.md](../../../docs/adr/0003-function-tree-requires-human-approval.md) |
| 调试 JWT / SSO / OAuth | §3.1 → [adr/0008](adr/0008-real-backend-oauth-jwt.md) |
| 调试菜单 | §3.1 → [adr/0009](adr/0009-menus-via-lab-backend.md) |
| 想问「为什么这样设计」 | §7（决策索引）→ 对应 ADR |

---

## 1. 角色与定位

**lab-management-system-springboot** 是 lab 7 仓家族的**后端 B 实现**——Java 17（JDK 21 编译）+ Spring Boot 3.4 + JPA + Flyway，端口 8080。

```
                ┌─────────────────────────────────────────────┐
                │         契约仓 (lab-shared)                  │
                │  TypeSpec (API)  +  sql/migrations (DB)     │
                │  emit: openapi.yaml / V001-V017.sql         │
                └──────┬──────────────────┬──────────────────┘
                       ▼                  ▼
            lab-msw (:5173)         6 frontend (react/vue/nextjs)
                       └──── fetch ────────┘
                                  ▼
              ┌────────────────────────────────────────┐
              │   lab-management-springboot (:8080)    │
              │   Java 17 + Spring Boot 3.4 + JPA +     │
              │   Flyway-on + HS256 JWT 真签名 +        │
              │   真 saas OAuth 2.0 直连                │
              └────────────────────────────────────────┘
```

**与 saas-springboot 的关键差异**：

| 维度 | saas-springboot | lab-springboot（本仓） |
|---|---|---|
| 产品域 | 多租户 OAuth IdP | 实验室检测业务（合同/接样/样品/报告） |
| **Flyway** | `enabled: false` | **`enabled: true`** + V014 永久分叉 |
| `baseline-on-migrate` | — | `true` / `baseline-version: "13"` |
| 业务表 | shared OAuth 表 | lab 业务表（contracts/receipts/samples/methods …） |
| JWT 鉴权 | dev `alg=none`（`@Profile("dev")`） | **HS256 真签名**（ADR-0008，no-sso profile 兜底） |
| SSO 链路 | — | **真 OAuth 2.0 直连 saas**（SaasAuthClient + SaasMeClient） |
| 菜单数据源 | — | **走 lab 后端 `/api/auth/menus`**（ADR-0009，saas 快照 + 30min 缓存 + demo 兜底） |
| DB migration 编号 | 与 shared 一一对应 | 与 shared **错位**（V008 = shared V008；V009 = shared V008 init_report_names；V014 永久分叉 + V017 rename） |

**家族定位总结**：lab-springboot 是与 saas-springboot **同构的「真后端」**——同样吃契约仓的 `openapi.yaml` + `sql/migrations/*.sql`，但**启用 Flyway** 做 managed migrations、保留**永久分叉 V014**、走**真 OAuth + 真签名 JWT**。

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
│   ├── main/java/io/xr/lab/platform/   ← 业务代码 + codegen 产物
│   ├── main/resources/
│   │   ├── application.yml     ← 默认 profile=${LAB_PROFILE:no-sso}
│   │   └── db/migration/       ← V001-V017.sql（含 V014 永久分叉）
│   └── test/java/io/xr/harness/junit/  ← fn-Test harness
├── scripts/gen-shared.sh       ← 三步同步脚本
├── pom.xml                     ← Spring Boot 3.4 + Spotless + SpotBugs
├── openapitools.json           ← openapi-generator-cli 版本锁
├── spotbugs-exclude.xml        ← L2 已知误报排除
├── Dockerfile                  ← multi-stage builder + runner
└── .state/                     ← session.json / gate.json / trace.json
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
├── entity/                        ← JPA entity（@Entity + @Table），反射镜像 shared SQL
│   ├── *Entity.java              ← 26 entity（合约/接样/样品/检测/计算方法/技术要求/字典/码表）
│   └── enums/                    ← AttributeConverter 集中地（10 个 converter +
│                                    8 junction 复合主键 @IdClass）
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
├── application.yml                ← 默认 profile=no-sso + flyway.enabled=true + DB/LAB/CORS 配置
└── db/migration/                  ← Flyway replay（gen-shared.sh step 3 拷入）
    ├── README.md                  ← shared 同步说明（附带 cp 进来）
    ├── V001__init_contracts.sql
    ├── V002__init_sample_receipts_samples.sql
    ├── V003__init_test_records.sql
    ├── V004__init_inspection_catalog.sql
    ├── V005__init_technical_requirements.sql
    ├── V006__init_audit_events.sql
    ├── V007__indexes.sql
    ├── V008__init_inspection_dictionary.sql      ← shared V008 = init_inspection_dictionary
    ├── V009__init_report_names.sql               ← shared V008 = init_report_names（编号错位）
    ├── V010__init_param_interfaces.sql
    ├── V011__backwire_inspection_fks.sql
    ├── V012__add_tenant_isolation.sql
    ├── V013__rename_param_interface_tables.sql
    ├── V014__enums_to_text.sql                   ← ★ 永久分叉（详见 §5）
    ├── V015__smoke_seed_dict.sql
    └── V017__rename_calculation_rules_to_methods.sql
```

### 2.4 仓根构件

| 构件 | 路径 | 作用 |
|---|---|---|
| `pom.xml` | 仓根 | Spring Boot 3.4.1 + JDK 21（Java 17 源）+ JPA + Flyway + Security + actuator；构建期接 `spotless-maven-plugin`（L1）+ `spotbugs-maven-plugin`（L2） |
| `scripts/gen-shared.sh` | scripts/ | 三步同步脚本（详见 §4.1） |
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
| SSO 客户端切换 | `SsoBeansConfig` 按 `@Profile` 切真 `SaasAuthClient`（`RestClient`）/ `NoopSaasAuthClient`（in-memory 假数据） | `application.yml` `spring.profiles.default: ${LAB_PROFILE:no-sso}` 默认 no-sso；CI 切 `default` 走真链 |
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

`io.xr.lab.platform.entity.*Entity` 是 **JPA entity 反射镜像 shared SQL**。Hibernate 不建表（`ddl-auto: validate`）——只校验 entity 与 Flyway 表 schema 一致。

| 模式 | 用途 | 示例 |
|---|---|---|
| 单字段 `@Id` | `UUID` 主键 | `ContractEntity.id` |
| 复合主键 `@IdClass` | 计算方法 / 技术要求 / junction 表 | `CalculationMethodEntity {inspectionObjectCode, inspectionParameterCode}` + `CalculationMethodKey implements Serializable` |
| `String` + `JsonNullable` | optional / 可空值 | `@JsonNullable<String> remark` |
| `@JdbcTypeCode(SqlTypes.JSON)` | PG `jsonb` 列 | `SampleReceiptEntity.testingBasis` (List<String>) / `flowHistory` (List<FlowHistoryEntry>) |
| `@Convert(converter = XxxConverter.class)` | PG `TEXT` 写枚举（小写 string） | `FlowStatus` (M03 7 阶段) / `ContractStatus` (ACTIVE/CLOSED) / `CalculationAlgorithmType` 等 8 个 |
| `@Enumerated(STRING)` role 字段 | role-based junction（role 字段直接存 PG enum 大写字面） | `ObjectStandardKey.role` (TESTING/JUDGMENT) — 无需 converter |

**enum converter 集中地**：10 个 `AttributeConverter` 注册到 `EnumConvertersConfig.java`——PG `TEXT` 列 ↔ Java enum，DTO 端走 `@JsonValue` 落到前端 enum 同款字符串。

### 3.6 DB Migration 层（`db/migration/V001-V017.sql`）

`src/main/resources/db/migration/` 是 Flyway replay 目录，由 `scripts/gen-shared.sh step 3` 拷入。

| 维度 | 行为 |
|---|---|
| Flyway enable | **`flyway.enabled: true`**（与 saas-springboot `false` 相反） |
| `baseline-on-migrate` | `true`，`baseline-version: "13"`（lab_dev 已由 nextjs sync 到 V013 等效状态时 baseline + skip） |
| 空库行为 | V001-V013 全量 replay → V014 永久分叉（白名单跳过 codegen cp，但 replay 走本地版本）→ V015 smoke seed → V017 rename |
| 单测 / CT | `lab_test` 库全量 replay 走空库路径（baseline 不触发） |
| prod | `lab_prod` flyway history 记录 V001-V017 checksum；改任一文件都触发「checksum mismatch」-app 启动失败 |
| `ddl-auto` | `validate`（Hibernate 只校验 entity 与 schema 一致，不 DDL） |

**V014 永久分叉管理**详见 §5。

---

## 4. 核心流程

### 4.1 与契约仓同步：`scripts/gen-shared.sh` 三步

```
1. [shared] 改 tsp/main.tsp 或 sql/migrations/V00N+1__*.sql + git push

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
   └─ step 3: DB — foreach shared/sql/migrations/V*.sql
        ├─ ver 在 DIVERGED_VERSIONS="V014" → SKIP（本地为准）
        ├─ target 不存在 → cp
        ├─ target 存在 && cmp -s 一致 → noop
        └─ target 存在 && cmp 不一致 → FATAL abort（exit 1，不覆盖）
   ↓ exit 0

4. mvn compile + mvn spotless:apply

5. mvn spring-boot:run -Dspring-boot.run.profiles=no-sso
   ├─ Spring Boot 启动（端口 8080）
   ├─ flyway.replay V001-V017（baseline if lab_dev 已 V013+）
   ├─ SecurityConfig 建 JwtDecoder bean（HS256 真签名）
   ├─ SsoBeansConfig 注入 NoopSaasAuthClient（no-sso profile）
   └─ Tomcat 监听 8080 + /actuator/health 200 OK

6. python scripts/gate.py -p lab-management-system-springboot   ← suite 根跑
   ↓ exit 0 = 全绿；1 = 按修复提示回代码；2 = 停下问人

7. git commit + git tag v<X>-<YYYYMMDD>

8. [父仓] chore(submodule): 推进 lab-springboot 指针
```

**关键防护**（`gen-shared.sh` 内置）：

| 风险 | 防护 | 触发 |
|---|---|---|
| codegen 把 DTO 写到错路径 | `mkdir -p` + `rm -rf` 先清空 | step 2 末尾 |
| codegen 排版不过 L1（spotless） | 末尾 `mvn -q spotless:apply` | step 2 末尾 |
| AuthState `String getKind()` 协变不兼容 | `sed -i '/    public String getKind();$/d'` | step 2 末尾 |
| 目标 V*.sql 已存在且内容不同 → 静默覆盖 → flyway checksum 起崩 | `cmp -s` + FATAL abort | step 3 foreach |
| V014 永久分叉被 shared 新版覆盖 → lab_prod checksum mismatch | `DIVERGED_VERSIONS="V014"` 白名单 | step 3 foreach |

### 4.2 启动链（`mvn spring-boot:run -Dspring-boot.run.profiles=no-sso`）

```
Maven → SpringApplication.run(App.class, args)
  ├─ Profile 解析：LAB_PROFILE=${LAB_PROFILE:no-sso} → 没设 env 默认 no-sso
  ├─ SsoBeansConfig @Profile 判断:
  │     ├─ no-sso → 注册 NoopSaasAuthClient + NoopSaasMeClient
  │     └─ sso   → 注册 SaasAuthClient (RestClient) + SaasMeClient
  │                  → 启动时校验 4 env 缺一 fail-fast
  ├─ SecurityConfig 构建:
  │     ├─ SecurityFilterChain（permitAll /api/auth/{login,refresh,sso/**} + /actuator/**）
  │     ├─ JwtDecoder（Nimbus HS256 真签，由 LabJwtSigner 给密钥）
  │     └─ CorsConfigurationSource（解析 lab.cors.allowed-origins CSV）
  ├─ Flyway replay（flyway.enabled: true）:
  │     ├─ lab_dev 已 V013 等效 → baseline-on-migrate @ V13，skip <=13
  │     ├─ 空库 → V001-V013 全量 replay
  │     ├─ V014 永久分叉 → 走 DIVERGED_VERSIONS 白名单，不动本地版本
  │     └─ V015 / V017 与 shared 同步（cmp 通过后 cp 进 db/migration）
  ├─ Hibernate EntityManager init（ddl-auto=validate）:
  │     ├─ 26 entity 与 DB schema 一致性校验
  │     └─ 10 个 AttributeConverter 注册
  ├─ Tomcat 启动 @ 8080
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

## 5. V014 永久分叉管理

### 5.1 现象

`db/migration/V014__enums_to_text.sql` 在**两份**不同代码库里：

| 来源 | 内容 | 用途 |
|---|---|---|
| **本仓**（演化版） | `ALTER TABLE inspection_calculation_methods ...` 把 PG enum 列转 TEXT + AttributeConverter | lab_prod 已应用，**flyway checksum 锁死 `(-1860597146)`** |
| **shared 仓**（fresh replay 版） | `ALTER TABLE inspection_calculation_rules ...` 同款语义但表名不同 | fresh replay 链（emit-schema / sql.replay.test / sync-db）必需 |

两版语义互补，**任何一边都不能替换另一边**：

- 把本仓 V014 改回 shared 版 → lab_prod 启动 fail（`Detected applied migration not resolved locally`）；
- 把 shared V014 同步到本仓 → lab_prod 上的 `inspection_calculation_rules` 表不存在（已被 V017 rename 为 `inspection_calculation_methods`）。

### 5.2 防护机制（`gen-shared.sh` 内置）

```bash
# scripts/gen-shared.sh step 3
DIVERGED_VERSIONS="V014"     # ★ 单一豁免点

for f in shared/sql/migrations/V*.sql; do
  ver=$(basename "$f" | cut -d_ -f1)
  if echo "$DIVERGED_VERSIONS" | grep -qw "$ver"; then
    echo "[gen-shared] SKIP diverged migration: $(basename "$f") (local version is authoritative)"
    continue
  fi
  target="src/main/resources/db/migration/$(basename "$f")"
  if [ -e "$target" ] && ! cmp -s "$f" "$target"; then
    echo "[gen-shared] FATAL: migration diverged..." >&2
    exit 1
  fi
  cp "$f" "$target"
done
```

| 版本 | 白名单? | 原因 |
|---|---|---|
| V001-V013 | 否 | 本仓与 shared 严格同步（cmp 一致 cp） |
| **V014** | **是** | **永久分叉，本地版本权威**——任何在 shared 改 V014 的尝试都会被本仓脚本的 SKIP 保护，shared 改动不影响本仓 `/db/migration/V014` 文件 |
| V015 | 否 | smoke seed（shared 已逐字节收敛） |
| V016 | 否 | （不存在，跳号保留） |
| V017 | 否 | rename calculation_rules → methods，shared 与本仓条件式同款 |

### 5.3 演化路径（未来）

| 工单 | 行为 |
|---|---|
| shared V015 改名 / V016 新增 | 本仓 `gen-shared.sh` 自动 cp；改名让 cmp 不一致则 FATAL，由人解决 |
| 共享侧想弃用 V014 旧语义 | shared 写 V018 把 fresh replay 路径上的 `inspection_calculation_rules` → `inspection_calculation_methods`（与本仓 V017 合并），新 fresh 库从 V001 走完也能到 V017 终态；本仓 V014 仍保留以兼容 `lab_prod` checksum |
| lab_prod 想升级 V014 之外的 migration | 只能新增 V018+，不能动 V001-V017 任何文件 |
| 弃用 V014 本仓版本 | blue-green deploy：建新库 + V001-V017（删 V014 本地版，shared 的 fresh 版接管）+ 数据迁移；架构级重构，需要 suite 评审 + ADR |

**白名单管理**：当未来出现 V018+ 永久分叉时，往 `DIVERGED_VERSIONS` 字符串里追加（`DIVERGED_VERSIONS="V014 V018"`），并在 [`docs/conventions/`](conventions/) 写明每个 whitelist entry 的「本地版本号 vs shared 语义」备忘。

---

## 6. 与契约仓同步——`scripts/gen-shared.sh` 详细说明

### 6.1 三步详解

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

**Step 3: DB SQL cp（含 cmp abort 防护 + DIVERGED_VERSIONS 白名单）**

```bash
SHARED_SQL="$SHARED_DIR/sql/migrations"
mkdir -p "$ROOT/src/main/resources/db/migration"
for f in "$SHARED_SQL"/V*.sql; do
  [ -e "$f" ] || continue
  ver=$(basename "$f" | cut -d_ -f1)
  if echo "$DIVERGED_VERSIONS" | grep -qw "$ver"; then
    echo "[gen-shared] SKIP diverged migration: $(basename "$f") (local version is authoritative)"
    continue
  fi
  target="$ROOT/src/main/resources/db/migration/$(basename "$f")"
  if [ -e "$target" ] && ! cmp -s "$f" "$target"; then
    echo "[gen-shared] FATAL: migration diverged: $(basename "$f") differs between shared and this repo." >&2
    exit 1
  fi
  cp "$f" "$target"
done
```

**关键防护点**（按 8/26 lab prod 502 40 分钟事故的根因订正）：

1. **`DIVERGED_VERSIONS` 白名单**：本仓 V014 永久分叉走 SKIP，shared 改 V014 不覆盖本仓；
2. **`cmp -s` 防护**：V001-V013 / V015 / V017 已有本地版本，shared 改文件必须先在本地**手动合并**（保留本地分叉或下游期望），未合并直接跑触发 FATAL abort；
3. **`[ -e "$f" ] || continue`**：保护 shared 仓目录可能临时缺某 V*.sql；
4. **`baseline-version="13"`**：lab_dev 不要求 V001-V013 已存在；首跑 missing migration 不会 crash。

### 6.2 不可信的同步模式（已废弃）

| ❌ 模式 | 后果 |
|---|---|
| 手动 `cp shared/sql/migrations/*.sql` 到本仓 `/db/migration/` | 易忘 cmp 防覆盖 |
| `sed -i 's/contracts-id/contracts_uuid/g'` 直接改 codegen 产物 | L1 排版错 + SpotBugs 警告；下次 gen-shared 重写 |
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
| [ADR-0007](../../../docs/adr/0007-shared-sql-ssot.md) | shared 仓扩到双 SSOT | `db/migration/` 读自 shared；ORM 只反射（`ddl-auto: validate`）；本仓 §3.5、§3.6 全部对齐 |
| [ADR-0014](../../../docs/conventions/multi-repo-family.md#4-后端配置env-driven-单-urladr-0014) | env-driven 单 URL | `LAB_DB_URL` / `LAB_JWT_SECRET` / `LAB_SAAS_*` env 切部署环境 |

### 7.3 隐性 ADR（本仓 §3.1 / §4.4 落地）

| 编号 | 主题 | 一句话 |
|---|---|---|
| (implicit) HS256 真签名覆盖 alg=none | JWT 算法不可降级 | `NimbusLabJwtDecoderFactory` 强制 HMAC 验证 + iss + exp；原 B1 dev `SecurityConfig.DevJwtDecoder (@Profile("dev"))` 已删除 |
| (implicit) 安全端点白名单含 `/actuator/**` | 健康探针匿名 | 教训（saas-springboot v0.1.7）：漏这行探针 401，deploy 120 次 wget 全失败 |
| (implicit) DIVERGED_VERSIONS 单点白名单 | V014 永久分叉保护 | `gen-shared.sh` line 65 唯一豁免点，文档化在 §5 |
| (implicit) cmp abort 防护 | 防止飞地式迁移覆盖 | `scripts/gen-shared.sh` line 77-82 FATAL abort；8/26 prod 502 40 分钟事故的根因订正 |
| (implicit) baseline-on-migrate | lab_dev 与 fresh 库双轨启动 | `application.yml` line 30-31 让本仓同时支持「lab_dev 已 sync 到 V013」与「空库全量 replay」 |

---

## 8. 术语表

| 术语 | 含义 | 在本仓的位置 |
|---|---|---|
| **SSOT** | Single Source of Truth | shared 仓承担双 SSOT（API + DB）；本仓 ORM 只反射（§3.5, §3.6） |
| **DIVERGED_VERSIONS** | 永久分叉白名单变量 | `gen-shared.sh` line 65 → §5 V014 |
| **永久分叉** | 本仓与 shared 在某 migration 上语义不同、互不可替换 | §5 V014 `(-1860597146)` 锁死 lab_prod |
| **baseline-on-migrate** | 已有 schema 但无 flyway history 时的自动 baseline | `application.yml` line 30-31 |
| **fnTest（spring 形态）** | JUnit5 `@Tag("Mxx.Fyy.Izz")` + `Fn` 注解 + `HarnessTraceListener` 自动落 `trace.json` | `src/test/java/io/xr/harness/junit/` |
| **codegen 产物** | openapi-generator 生成的 `io.xr.lab.platform.api.*Api` + `io.xr.lab.shared.dto.*` | §3.2；git ignored，每次 gen-shared 重建 |
| **手写 Controller** | `implements *Api` 接口 + 构造器注入 Service | §3.2-3.3 |
| **AttrConv 集中地** | `io.xr.lab.platform.entity.enums.*Converter` | §3.5；10 个 converter 把 PG TEXT ↔ Java enum |
| **trace.json** | 测试命中 fn-ID 的清单 | `trace_cmd` 产，禁止手写 |
| **stack.json** | 项目自描述（栈 + 门配置） | `.harness/stack.json`，本仓声明 L1-L4 |
| **TenantGuard** | tenant 隔离检查（无独立类） | 各 Controller 顶部 `claims.tenantId` 注入到 repository 查询条件 |
| **JWT 真签名** | HS256 + 强密钥，取代 B1 dev `alg=none` | ADR-0008；§3.1 |
| **dev 降级 profile** | `no-sso` profile → NoopSaasAuthClient | `application.yml` line 12-13 默认值 |
| **saas 快照缓存** | `MenuSnapshotCache` process 内 30min TTL | ADR-0009；§4.4 |
| **cmp abort** | `gen-shared.sh` FATAL 防护：`cmp -s` 不一致即 exit 1 | `gen-shared.sh` line 77-82 |

---

## 附录 A：与父仓 docs/ARCHITECTURE.md 的关系

本文档**不重复**父仓 docs/ARCHITECTURE.md 的家族级议题：

| 议题 | 看哪里 |
|---|---|
| 多仓家族 14 个仓的角色矩阵 | 父仓 §2 |
| 5 种角色（契约/Mock/前端/后端/suite）的禁止事项 | 父仓 §2.1 |
| 跨仓端到端流程图（改契约→三端同步） | 父仓 §5 |
| OAuth 2.0 + JWT（HS256）契约 + DevJwtDecoder 兜底 | 父仓 §3.4 |
| 端口 / CORS / env 全景 | 父仓 §6 |
| suite 门禁链（L0..L5） | 父仓 §5.4 |

**本仓独有**：本仓的 §3、§4、§5、§6（具体业务层 + Flyway V014 永久分叉管理 + cmp abort 防事故线）。

---

## 附录 B：与 saas-springboot 后端仓的对照

| 维度 | saas-springboot | lab-springboot（本仓） |
|---|---|---|
| **产品域** | 多租户 OAuth IdP（authorize/callback/refresh/menus） | 实验室检测业务（合同/接样/样品/检测/报告） |
| **Flyway** | `enabled: false`（schema 由 shared SQL + sync-db 全量灌入 + JPA validate） | **`enabled: true`**（V001-V017 replay，含 V014 永久分叉管理） |
| **DB migration 编号** | 与 shared 一一对应 | 与 shared **错位**（V008 = shared V008；V009 = shared V008 init_report_names；V014 永久分叉 + V017 rename） |
| **JWT 算法** | (历史：dev `alg=none` `DevJwtDecoder @Profile("dev")`)；Phase 2A 后 saas 也删 DevJwtDecoder，4 仓现在统一 HS256 真验签 | **HS256 真签名**（LabJwtSigner + Nimbus）；未走 dev `alg=none` 兼容 |
| **SSO 链路** | — | **真 OAuth 2.0 直连 saas**（SaasAuthClient + SaasMeClient） |
| **菜单数据源** | — | **经 lab 后端 /api/auth/menus**（快照缓存 + demo 兜底） |
| **业务表** | shared OAuth 表（tenants/users/apps/menus） | lab 业务表（13 张主表 + 8 junction） |
| **Codegen 模式** | 同款 `openapi-generator -g spring interfaceOnly` | 同款，参数镜像 saas v0.2.0 |
| **DB dialect** | postgres | postgres（lab-shared SQL 即 postgres 方言） |
| **默认 profile** | dev | `no-sso`（NoopSaasAuthClient 兜底；CI 切 `default` 走真 saas） |
| **env 漂移 502 风险** | 已知（[memory/springboot-env-drift-502-trap.md](../../../memory/springboot-env-drift-502-trap.md)） | 同款；default=no-sso 缓解 |
| **deploy 脚本读 fat jar** | `platform-<version>.jar` | 同款，本仓 artifactId=`lab-management-system-springboot` |
| **DevJwtDecoder 兼容性** | (历史) dev-only bean；Phase 2A 已删 | 本仓自始 HS256 真签，未用过 dev 降级 |
| **SecurityConfig `/actuator/**`** | permitAll（教训 v0.1.7） | 同款 |
| **持久化** | 无 OAuth refresh token 存储（state-cookie 含短时 nonce） | 无 OAuth refresh token 存储（saas_refresh_token 嵌进 lab refresh token JWT claim） |

---

## 附录 C：典型陷阱（cross-reference to memory/）

| 陷阱 | 解法 | 出处 |
|---|---|---|
| `SecurityConfig` 漏 `/actuator/**` | 加 `permitAll` | [memory/springboot-actuator-401-deploy-loop-trap.md](../../../memory/springboot-actuator-401-deploy-loop-trap.md) |
| env 漂移 → CF 502 → CORS 误诊 | 先查 VPS env-file 缺失项；改 env 必须重建容器 | [memory/springboot-env-drift-502-trap.md](../../../memory/springboot-env-drift-502-trap.md) |
| `DevJwtDecoder` 是 dev-only（saas 历史教训） | Phase 2A 已删；现在统一 `NimbusJwtDecoder.withSecretKey` HS256 真验签；env-file 别写 `JWT_SIGNING_KEY` | [memory/springboot-dev-jwt-decoder-gap.md](../../../memory/springboot-dev-jwt-decoder-gap.md) |
| 不知道 fat jar 名 | deploy 按 artifactId 找 jar：本仓 `lab-management-system-springboot-<version>.jar` | [memory/springboot-fat-jar-name.md](../../../memory/springboot-fat-jar-name.md) |
| 8/26 lab prod 502 40 分钟事故 | `gen-shared.sh` cmp abort 防护（§6.1）；DIVERGED_VERSIONS V014（§5） | session.json 2026-08-26 root-cause |
| codegraph 工具不解析 .tsp | 看本仓 `docs/functions/function-tree.md` 就够 | [memory/codegraph-typespec-mismatch.md](../../../memory/codegraph-typespec-mismatch.md) |
| JDK HttpClient h2c 打挂 msw | RestClient 调本地明文服务 EOF 时，强制 HTTP/1.1 | [memory/jdk-httpclient-h2c-breaks-msw.md](../../../memory/jdk-httpclient-h2c-breaks-msw.md) |
