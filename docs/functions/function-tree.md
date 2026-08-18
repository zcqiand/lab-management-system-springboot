# 功能清单（Function Tree）— lab-management-system-springboot

> **全体系唯一锚点。** 需求、流程、设计、测试都引用这里的 ID。
> 不在这里的 ID 是悬空引用，L5 门会拦。**改功能，先改这份表。**

## 编号规则

| 层级 | 名称 | 格式 | 含义 |
|---|---|---|---|
| 一级 | 功能模块 | `M0x` | 业务域边界，通常对应一级菜单（实际命名见各仓模块总览） |
| 二级 | 功能 | `M0x.F0y` | 一个完整业务步骤 / 独立闭环流程 / 数据管理页面 |
| 三级 | 功能子项 | `M0x.F0y.I0z` | 最小操作单元。后端 = 端点（endpoint） |

**硬规则**

1. 编号单调递增，永不复用。废弃改状态，不删行。
2. 子项编号必须以父级为前缀。
3. 一个子项 = 一个权限点。权限码即 ID，不另起一套编码。
4. 拆不出子项的功能 → 它其实是子项，往上并。子项超 20 个 → 它其实是模块，往下拆。

**状态**：`规划` | `开发中` | `已上线` | `已废弃`
**子项类型**：`接口` | `查询`（后端仓无页面/按钮/标签页/报表类型）

## 本仓角色

**后端仓（Spring Boot）**。lab-management-system 7 仓家族的后端 B（端口 8080）。

- M00..M06 是 shared BASE 镜像：26 个 BASE F 级原样照抄（check_align 强制 F 集合跨仓一致）
- 本仓在 F 级别向下加 I 级子项（后端 I = 端点），随实现逐波 tree-change 推进
- 契约消费：`scripts/gen-shared.sh` 两步 codegen（shared emit openapi.yaml → openapi-generator spring interfaceOnly）
- DB：postgres 直连，Flyway replay `../lab-management-system-shared/sql/migrations` V001-V013

---

## 模块总览

| 模块 ID | 模块名称 | 业务域边界 | 状态 |
|---|---|---|---|
| M00 | 租户管理 | 当前用户关联租户列表、登录选租户、切换租户 | 规划 |
| M01 | 认证管理 | 权限管理（RBAC/动态菜单）、认证（登录/SSO/JWT） | 规划 |
| M02 | 资源管理 | 合同管理 | 规划 |
| M03 | 试验过程管理 | 接样 → 任务分配 → 数据录入 → 报告审核 → 批准 → 发放 → 归档 | 规划 |
| M04 | 基础数据 | 型号/规格/等级/牌号维护 | 规划 |
| M05 | 数据统计 | 报告汇总表（按报告名称） | 规划 |
| M06 | 检测能力 | 检测专项/项目/参数/标准/计算规则/技术要求/报告名称/参数界面 | 规划 |

---

## BASE F 级（M0x.F0y，shared BASE 镜像）

| 功能 ID | 功能 | 闭环定义 | 类型 | 状态 |
|---|---|---|---|---|
| M00.F01 | 当前用户会话 | 当前用户信息 + 关联租户列表 + 当前选中租户（GET /auth/me） | 查询 | 规划 |
| M00.F02 | 登录选租户 | 登录后选择租户，换发携带 tenant_id claim 的 token（POST /auth/switch-tenant） | 接口 | 规划 |
| M01.F04 | 权限管理 | RBAC 角色权限、路由守卫、权限指令、动态菜单（身份平台下发） | 接口 | 规划 |
| M01.F05 | 认证管理 | 用户名+密码登录 + SSO 统一登录（对接身份平台），JWT 签发与校验 | 接口 | 规划 |
| M02.F01 | 合同管理 | 合同 CRUD、工程信息维护 | 接口 | 规划 |
| M03.F01 | 接样管理 | 接样单 CRUD、报告类别关联、流程状态 | 接口 | 规划 |
| M03.F02 | 任务分配 | 接样提交后安排检测人员/计划日期，提交进入数据录入；任务字段挂 SampleReceipt | 接口 | 规划 |
| M03.F03 | 数据录入 | 样品检测数据录入 | 接口 | 规划 |
| M03.F05 | 报告审核 | 报告审核流程 | 接口 | 规划 |
| M03.F06 | 报告批准 | 报告批准流程 | 接口 | 规划 |
| M03.F07 | 报告发放 | 报告发放流程 | 接口 | 规划 |
| M03.F08 | 报告归档 | 报告归档流程 | 接口 | 规划 |
| M03.F09 | 接样单详情 | 接样单查看（接样信息+样品信息+检测数据） | 接口 | 规划 |
| M04.F06 | 型号维护 | InspectionModel 官方数据码表维护，列表按检测专项过滤 | 接口 | 规划 |
| M04.F07 | 规格维护 | InspectionSpec 官方数据码表维护，列表按检测专项过滤 | 接口 | 规划 |
| M04.F08 | 等级维护 | InspectionGrade 官方数据码表维护，列表按检测专项过滤 | 接口 | 规划 |
| M04.F09 | 牌号维护 | InspectionBrand 官方数据码表维护，列表按检测专项过滤 | 接口 | 规划 |
| M05.F01 | 报告汇总 | 按报告类别输出试验报告汇总表 | 查询 | 规划 |
| M06.F01 | 检测专项 | InspectionSpecialty CRUD（检测能力字典根） | 接口 | 规划 |
| M06.F02 | 检测项目 | InspectionObject CRUD + 专项/参数关联 | 接口 | 规划 |
| M06.F03 | 检测参数 | InspectionParameter CRUD + 标准/参数关联 | 接口 | 规划 |
| M06.F04 | 检测标准 | InspectionStandard CRUD（含状态：active/superseded/draft） | 接口 | 规划 |
| M06.F05 | 计算规则 | CalculationRule 维护（复合主键，算法类型 + 公式） | 接口 | 规划 |
| M06.F06 | 技术要求 | TechnicalRequirement 维护，按四维度匹配；brand/model/grade/spec 改为 FK 引用实体 | 接口 | 规划 |
| M06.F07 | 报告名称 | InspectionReportName CRUD + extFields 模板 + 关联标准/参数 | 接口 | 规划 |
| M06.F08 | 参数界面 | ParamInterface 维护 + 参数↔界面 link | 接口 | 规划 |

---

## 子项级（M0x.F0y.I0z）

> Batch B1（认证域，M00.F01/F02 + M01.F04/F05，对应 AuthApi 9 端点）。
> 用户/租户来源：dev 配置式目录（镜像 lab-msw seeds：admin/dev123456 + TENANT-001/2/3），
> 无 DB 身份表（见 open question：V014 identity 表）。

| 子项 ID | 名称 | 闭环定义 | 类型 | 状态 |
|---|---|---|---|---|
| M00.F01.I01 | 当前会话 | GET /api/auth/me：user + 关联租户列表 + currentTenantId（token tenant_id claim，缺省 TENANT-001） | 接口 | 已上线 |
| M00.F02.I01 | 选租户换发 | POST /api/auth/switch-tenant：校验租户归属后换发携带 tenant_id claim 的 token | 接口 | 已上线 |
| M01.F04.I01 | 动态菜单 | GET /api/auth/menus：按角色下发导航树（5 根节点，镜像 lab-msw） | 接口 | 已上线 |
| M01.F04.I02 | 权限集 | GET /api/auth/permissions：RBAC 权限串列表（admin 全量 11 项） | 接口 | 已上线 |
| M01.F05.I01 | 密码登录 | POST /api/auth/login：用户名+密码校验，签发 access/refresh token + 租户列表 | 接口 | 已上线 |
| M01.F05.I02 | SSO 跳转 | GET /api/auth/sso/authorize?redirect=：构造 saas 身份平台登录跳转 URL + state | 接口 | 已上线 |
| M01.F05.I03 | SSO 回调 | POST /api/auth/sso/callback：dev 直发 demo 会话（真对接待 saas 端点可用） | 接口 | 已上线 |
| M01.F05.I04 | 刷新 token | POST /api/auth/refresh：refresh token 换发新 access token | 接口 | 已上线 |
| M01.F05.I05 | 登出 | POST /api/auth/logout：无状态 JWT 服务端无 session，前端清存储 | 接口 | 已上线 |

> Batch B2（码表+规则+技术要求，M04.F06-09 + M06.F05-06，对应 InspectionCatalogApi 16 + CalculationRulesApi 5 + TechnicalRequirementsApi 5 共 26 端点）。
> 4 码表结构一致（code+name+inspectionObjectCode+remark+sortOrder+tenantId）；4 表都已加 tenant_id 隔离（V012）；FK 引用关系见 technical_requirements 表 V005。
> 计算规则平台级（per V012 不加 tenant_id）；技术要求 tenant-scoped。

| 子项 ID | 名称 | 闭环定义 | 类型 | 状态 |
|---|---|---|---|---|
| M04.F06.I01 | 型号列表 | GET /api/catalog/models?inspectionObjectCode=&keyword=：按 tenant 收口 + 2 过滤，返回 InspectionModel[] | 接口 | 已上线 |
| M04.F06.I02 | 创建型号 | POST /api/catalog/models：body CreateCatalogEntryRequest（code/name 必填 + 可选 inspectionObjectCode/remark/sortOrder），返回 InspectionModel | 接口 | 已上线 |
| M04.F06.I03 | 更新型号 | PUT /api/catalog/models/{code}：body UpdateCatalogEntryRequest（PATCH 语义，未传字段保留），404 if 不存在 | 接口 | 已上线 |
| M04.F06.I04 | 删除型号 | DELETE /api/catalog/models/{code}：204；FK 被 technical_requirements.model 引用时 DB SET NULL | 接口 | 已上线 |
| M04.F07.I01 | 规格列表 | GET /api/catalog/specs?inspectionObjectCode=&keyword= | 接口 | 已上线 |
| M04.F07.I02 | 创建规格 | POST /api/catalog/specs | 接口 | 已上线 |
| M04.F07.I03 | 更新规格 | PUT /api/catalog/specs/{code} | 接口 | 已上线 |
| M04.F07.I04 | 删除规格 | DELETE /api/catalog/specs/{code} | 接口 | 已上线 |
| M04.F08.I01 | 等级列表 | GET /api/catalog/grades?inspectionObjectCode=&keyword= | 接口 | 已上线 |
| M04.F08.I02 | 创建等级 | POST /api/catalog/grades | 接口 | 已上线 |
| M04.F08.I03 | 更新等级 | PUT /api/catalog/grades/{code} | 接口 | 已上线 |
| M04.F08.I04 | 删除等级 | DELETE /api/catalog/grades/{code} | 接口 | 已上线 |
| M04.F09.I01 | 牌号列表 | GET /api/catalog/brands?inspectionObjectCode=&keyword= | 接口 | 已上线 |
| M04.F09.I02 | 创建牌号 | POST /api/catalog/brands | 接口 | 已上线 |
| M04.F09.I03 | 更新牌号 | PUT /api/catalog/brands/{code} | 接口 | 已上线 |
| M04.F09.I04 | 删除牌号 | DELETE /api/catalog/brands/{code} | 接口 | 已上线 |
| M06.F05.I01 | 计算规则列表 | GET /api/calculation-rules?inspectionObjectCode=&inspectionParameterCode=：平台级（无 tenant 过滤） | 接口 | 已上线 |
| M06.F05.I02 | 计算规则详情 | GET /api/calculation-rules/{inspectionObjectCode}/{inspectionParameterCode}：复合主键 | 接口 | 已上线 |
| M06.F05.I03 | 创建计算规则 | POST /api/calculation-rules：body CreateCalculationRuleRequest，algorithmType 默认 MANUAL、specimenCount 默认 1 | 接口 | 已上线 |
| M06.F05.I04 | 更新计算规则 | PUT /api/calculation-rules/{...}：PATCH 语义 | 接口 | 已上线 |
| M06.F05.I05 | 删除计算规则 | DELETE /api/calculation-rules/{...}：204 | 接口 | 已上线 |
| M06.F06.I01 | 技术要求列表 | GET /api/technical-requirements?inspectionObjectCode=&inspectionParameterCode=&judgmentStandardCode=&verificationStatus=：tenant 收口 + 4 过滤 | 接口 | 已上线 |
| M06.F06.I02 | 技术要求详情 | GET /api/technical-requirements/{object}/{param}/{standard}：复合三键 | 接口 | 已上线 |
| M06.F06.I03 | 创建技术要求 | POST /api/technical-requirements：tenant 从 token claim 注入；默认值 numeric/≥/manual/draft | 接口 | 已上线 |
| M06.F06.I04 | 更新技术要求 | PUT /api/technical-requirements/{...}：PATCH 语义 | 接口 | 已上线 |
| M06.F06.I05 | 删除技术要求 | DELETE /api/technical-requirements/{...}：204 | 接口 | 已上线 |
