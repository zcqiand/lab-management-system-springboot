# 实验室管理系统 · Spring Boot 后端

建筑工程实验室管理系统的 Java 后端 —— codegen Controller + 手写 Service，对接 lab_prod PostgreSQL。

本仓为《Spring Boot 从入门到项目实践》（亚马逊电子书）案例一「实验室管理系统」（第 37-46 章）的可运行配套工程，是书稿代码块的 **source of truth**。

## 快速开始

```bash
mvn verify                    # 全量测试（含 Spotless / SpotBugs）
bash scripts/gen-shared.sh    # 改了 shared API 契约后同步 codegen 产物
bash scripts/scaffold-entities.sh  # 改了 shared DB schema（已 db:migrate）后同步 entity 镜像
mvn spring-boot:run           # 本地起服务
```

## 功能特性

- Controller 与 DTO 由 shared 仓 TypeSpec codegen 全覆盖（openapi-generator）
- 手写 Service 与 Repository；DB-First（ADR-0025/0033）：schema 真源 = shared `src/db/schema.ts`，entity 镜像 = `bash scripts/scaffold-entities.sh`
- OAuth2 resource server（JWT）；HS256 真签名（ADR-0008）

## 技术栈

| 技术 | 版本 |
| :--- | :--- |
| Java | 21 |
| Spring Boot | 3.4.1 |
| PostgreSQL driver | 随 spring-boot-starter-parent |
| JUnit 5 | 随 starter-test |
| Maven | 3.9+ |

> 依赖版本与 `version-lock.json` 的 `version_lock` 一致，不引入 lock 外的库。

## 配套书籍及章节映射

> 同一案例仓后续接入其他书籍时，在此节下新增书籍小节。

### 《Spring Boot 从入门到项目实践》（亚马逊电子书）

- 书稿基线：tag `v0.1.47-20260926`（冻结，正文代码清单以此为准）
- 书稿定位：案例一「实验室管理系统」，覆盖第 37-46 章

| 章 | 主题 | 对应源文件 |
| :--- | :--- | :--- |
| 37 | 项目立项与需求分析：从检测业务到功能清单 | `src/main/java/io/xr/lab/platform/entity/ContractEntity.java`、`src/main/java/io/xr/lab/platform/entity/SampleReceiptEntity.java` |
| 38 | 架构设计：分层架构与数据模型骨架 | `src/main/java/io/xr/lab/platform/controller/TestRecordController.java`、`src/main/java/io/xr/lab/platform/repository/SampleRepository.java`、`src/main/resources/application.yml` |
| 39 | 检测字典管理：专项、检测项目与参数的实体设计 | `src/main/java/io/xr/lab/platform/entity/InspectionSpecialtyEntity.java`、`src/main/java/io/xr/lab/platform/entity/InspectionObjectEntity.java`、`src/main/java/io/xr/lab/platform/entity/InspectionParameterEntity.java` |
| 40 | 接样登记与流程状态机：提交、退回与流转历史 | `src/main/java/io/xr/lab/platform/entity/SampleReceiptEntity.java`、`src/main/java/io/xr/lab/platform/entity/enums/FlowStatusConverter.java`、`src/main/java/io/xr/lab/platform/service/SampleReceiptService.java` |
| 41 | 检测记录管理：数据录入、更新与结果改判 | `src/main/java/io/xr/lab/platform/controller/TestRecordController.java`、`src/main/java/io/xr/lab/platform/repository/TestRecordRepository.java`、`src/main/java/io/xr/lab/platform/service/TestRecordService.java` |
| 42 | 报告审核流程：审核提交与流程服务设计 | `src/main/java/io/xr/lab/platform/service/ReportFlowService.java` |
| 43 | 统计汇总接口：报告汇总与状态聚合查询 | `src/main/java/io/xr/lab/platform/service/SummaryService.java` |
| 44 | 检测标准目录：标准、参数界面与连接表设计 | `src/main/java/io/xr/lab/platform/entity/InspectionStandardEntity.java`、`src/main/java/io/xr/lab/platform/entity/ParamInterfaceEntity.java`、`src/main/java/io/xr/lab/platform/entity/InspectionObjectParameterEntity.java` |
| 45 | Docker 部署实战：镜像构建与 VPS 交付脚本 | `Dockerfile`、`deploy/setup-vps.sh`、`deploy/nginx-vps.conf.example` |
| 46 | 项目总结：从需求到交付的全链路复盘 | — |

## 快速链接

- [CLAUDE.md](CLAUDE.md) — 开发约定与编码规范
- [系统架构.md](docs/ARCHITECTURE.md) — 结构 / 边界 / 数据流 / 决策
- [功能规格.md](docs/functions/function-tree.md) — 功能名称、描述与验收标准
- [未来开发计划](PLAN.md) — 待办与迭代方向
- [更新日志](CHANGELOG.md) — 版本变更记录
