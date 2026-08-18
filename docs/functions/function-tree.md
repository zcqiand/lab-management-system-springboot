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
| M03.F05 | 报告审核 | 报告审核流程 | 接口 | 已上线 |
| M03.F06 | 报告批准 | 报告批准流程 | 接口 | 已上线 |
| M03.F07 | 报告发放 | 报告发放流程 | 接口 | 已上线 |
| M03.F08 | 报告归档 | 报告归档流程 | 接口 | 已上线 |
| M03.F09 | 接样单详情 | 接样单查看（接样信息+样品信息+检测数据） | 接口 | 规划 |
| M04.F06 | 型号维护 | InspectionModel 官方数据码表维护，列表按检测专项过滤 | 接口 | 规划 |
| M04.F07 | 规格维护 | InspectionSpec 官方数据码表维护，列表按检测专项过滤 | 接口 | 规划 |
| M04.F08 | 等级维护 | InspectionGrade 官方数据码表维护，列表按检测专项过滤 | 接口 | 规划 |
| M04.F09 | 牌号维护 | InspectionBrand 官方数据码表维护，列表按检测专项过滤 | 接口 | 规划 |
| M05.F01 | 报告汇总 | 按报告类别输出试验报告汇总表 | 查询 | 规划 |
| M05.F02 | 仪表盘统计 | 工作台仪表盘：合同/接样/样品计数 + 按 3 桶聚合的报告状态 + 任务计数 | 查询 | 规划 |
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

> Batch B3（合同 + 接样单 + 样品 + 报告流程，M02.F01 + M03.F01/F02/F03 + M03.F05/F06，对应 ContractsApi 5 + ReceiptsApi 7 + SamplesApi 5 + ReportFlowApi 2 共 19 端点）。
> 合同是接样 FK 父表（sample_receipts.contract_id → contracts.id ON DELETE RESTRICT）。
> 接样单自身是 7 阶段状态机（receiving / task_assignment / data_entry / review / approval / issuance / archived）。
> 3 张 jsonb 列走 @JdbcTypeCode(SqlTypes.JSON)：judgment_basis/testing_basis/test_parameters（标准码数组）+ flow_history（FlowHistoryEntry[]）。
> 计算规则/技术要求/合同/接样的 8 个 PG enum 全部在 B2+V014 / V015 改为 TEXT + AttributeConverter 写 DTO @JsonValue 同款字符串。

| 子项 ID | 名称 | 闭环定义 | 类型 | 状态 |
|---|---|---|---|---|
| M02.F01.I01 | 合同列表 | GET /api/contracts?keyword=&status=：按 tenant 收口 + 2 过滤，返回 Contract[] | 接口 | 已上线 |
| M02.F01.I02 | 合同详情 | GET /api/contracts/{id}：404 if 不存在 | 接口 | 已上线 |
| M02.F01.I03 | 创建合同 | POST /api/contracts：code/clientUnit/projectName/constructionUnit/witnessUnit/witness 必填，status 默认 ACTIVE | 接口 | 已上线 |
| M02.F01.I04 | 更新合同 | PUT /api/contracts/{id}：PATCH 语义 | 接口 | 已上线 |
| M02.F01.I05 | 删除合同 | DELETE /api/contracts/{id}：204；如果有接样引用 FK RESTRICT 拒 | 接口 | 已上线 |
| M03.F01.I01 | 接样单列表 | GET /api/receipts?contractId=&flowStatus=&keyword=：按 tenant 收口 + 3 过滤 | 接口 | 已上线 |
| M03.F01.I02 | 接样单详情 | GET /api/receipts/{id}：返回 SampleReceipt（含 flow_history） | 接口 | 已上线 |
| M03.F01.I03 | 创建接样单 | POST /api/receipts：contract_id FK 必存在；flow_status=receiving 起步；flow_history=[] | 接口 | 已上线 |
| M03.F01.I04 | 更新接样单 | PUT /api/receipts/{id}：PATCH 语义 | 接口 | 已上线 |
| M03.F01.I05 | 删除接样单 | DELETE /api/receipts/{id}：CASCADE 删除下属 samples | 接口 | 已上线 |
| M03.F01.I06 | 接样单流程历史 | GET /api/receipts/{id}/history：返回 FlowHistoryEntry[]（jsonb 展开为 List） | 接口 | 已上线 |
| M03.F02.I01 | 任务分配 | PUT /api/receipts/{id}/task：AssignTaskRequest 设 assigneeId/Name/plannedTestDate；非 receiving 阶段不自动 advance | 接口 | 已上线 |
| M03.F03.I01 | 样品列表 | GET /api/samples?receiptId=&keyword=：tenant + 2 过滤 | 接口 | 已上线 |
| M03.F03.I02 | 样品详情 | GET /api/samples/{id} | 接口 | 已上线 |
| M03.F03.I03 | 创建样品 | POST /api/samples：receipt_id FK 必存在；ext 默认 {} | 接口 | 已上线 |
| M03.F03.I04 | 更新样品 | PUT /api/samples/{id}：PATCH 语义 | 接口 | 已上线 |
| M03.F03.I05 | 删除样品 | DELETE /api/samples/{id}：204 | 接口 | 已上线 |
| M03.F03.I06 | 检测记录列表 | GET /api/test-records?sampleId=&page=&pageSize=：tenant 收口 + sampleId 过滤 + 分页；data-fn=nextjs/react/vue 仓 test-records 页面 | 接口 | 已上线 |
| M03.F03.I07 | 检测记录详情 | GET /api/test-records/{id}：返回 TestRecord | 接口 | 已上线 |
| M03.F03.I08 | 创建检测记录 | POST /api/test-records：sampleId/parameterCode/requirement/result 必填；tenant 从 token claim 注入 | 接口 | 已上线 |
| M03.F03.I09 | 更新检测记录 | PUT /api/test-records/{id}：PATCH 语义，未传字段保留 | 接口 | 已上线 |
| M03.F03.I10 | 删除检测记录 | DELETE /api/test-records/{id}：204 if exists | 接口 | 已上线 |
| M03.F03.I11 | 检测记录改判 | PUT /api/test-records/{id}/verdict：人工改判（M03.F05/F06 报告流程可触发） | 接口 | 已上线 |
| M03.F05.I01 | 审核队列 | GET /api/receipts/flow/queue?stage=：按 stage 过滤+按 tenant 收口，返回 ReceiptsListReceipts200Response（pageSize 默认 50，cap 200） | 接口 | 已上线 |
| M03.F05.I02 | 报告审核-查看详情 | GET /api/receipts/{id}：返回 SampleReceipt（含 flow_history）走 review 视角 | 接口 | 已上线 |
| M03.F05.I03 | 报告审核-通过/退回 | POST /api/receipts/flow：FlowActionRequest{ids, action, operator, reason}；review 视角下 action=SUBMIT 推进到 approval / RETURN 退回 data_entry | 接口 | 已上线 |
| M03.F06.I01 | 报告阶段审批推进 | POST /api/receipts/flow：FlowActionRequest{ids, action, operator, reason}；action=SUBMIT/RETURN/WITHDRAW；FAIL 单条结果进 FlowActionResult{ok, message} | 接口 | 已上线 |
| M03.F06.I02 | 报告批准-查看详情 | GET /api/receipts/{id}：返回 SampleReceipt（含 flow_history）走 approval 视角 | 接口 | 已上线 |
| M03.F06.I03 | 报告批准-批准/退回 | POST /api/receipts/flow：approval 视角下 action=SUBMIT 推进到 issuance / RETURN 退回 review | 接口 | 已上线 |
| M03.F07.I01 | 报告发放队列 | GET /api/receipts/flow/queue?stage=issuance：按 stage=issuance 过滤当前租户 receipt 列表 | 接口 | 已上线 |
| M03.F07.I02 | 报告发放-查看详情 | GET /api/receipts/{id}：返回 SampleReceipt（含 flow_history + issued_at）走 issuance 视角 | 接口 | 已上线 |
| M03.F07.I03 | 报告发放-发放/退回 | POST /api/receipts/flow：issuance 视角下 action=SUBMIT 推进到 archived / RETURN 退回 approval | 接口 | 已上线 |
| M03.F08.I01 | 报告归档队列 | GET /api/receipts/flow/queue?stage=archived：按 stage=archived 过滤当前租户 receipt 列表 | 接口 | 已上线 |
| M03.F08.I02 | 报告归档-查看详情 | GET /api/receipts/{id}：返回 SampleReceipt（含 flow_history）走 archived 视角 | 接口 | 已上线 |
| M03.F08.I03 | 报告归档-归档/退回 | POST /api/receipts/flow：archived 视角下 action=SUBMIT 推进终态 / RETURN 退回 issuance | 接口 | 已上线 |

> Batch B4（M05 报告汇总 + 仪表盘，M05.F01 + M05.F02 仪表盘新增 — 2 端点，对应 SummaryApi 全集）。
> SummaryData 是「列定义 + 行数据」动态表（rows: List<Map<String,String>>），列固定 6 列（委托编号/报告类别/工程名称/流程状态/结论/报告编号），行按 commissionDate DESC。
> 仪表盘统计：合同/接样/样品 3 总数 + 按 flowStatus 聚合的 draft/reviewing/issued 三桶 + 任务计数（task_assignment + data_entry + review）。

| 子项 ID | 名称 | 闭环定义 | 类型 | 状态 |
|---|---|---|---|---|
| M05.F01.I01 | 报告汇总 | GET /api/summary?categoryCode=&dateFrom=&dateTo=：categoryCode=ALL 不过滤，否则按报告类别过滤当前租户接样单；输出 SummaryData{summaryName, columns(6), rows}；data-fn=nextjs/react/vue 仓 SummaryPage | 查询 | 已上线 |
| M05.F02.I01 | 仪表盘统计 | GET /api/summary/stats：合同/接样/样品 计数 + 3 桶报告状态（draft=receiving+task_assignment+data_entry；reviewing=review+approval；issued=issuance+archived）+ pendingTaskCount（task_assignment+data_entry+review）；data-fn=nextjs/react/vue 仓 dashboard | 查询 | 已上线 |

> Batch B5（M06 字典 5 实体 22 端点 — 标准/参数/专项/报告名称/参数界面，平台级共享字典 per V012 不加 tenant_id）。
> 3 实体 specialty/parameter/standard 走 InspectionDictionaryApi（12 端点，list+create+update+delete 各 4）+ 新增 2 个 AttributeConverter 写 PG enum 小写值
> （InspectionParameterSourceTypeConverter / InspectionStandardStatusConverter）。
> parameter.aliases 走 jsonb（`List<String>`），report-name ext_fields 走 jsonb（`List<ExtFieldDef>`），param-interface config 走 jsonb（`Map<String,Object>`），
> Entity 端 String 装 Jackson 序列化值，DTO 端 List/Map 直传。
> M06.F02 objects + 4 junction link/unlink（specialty-object/object-parameter/object-standard/standard-parameter）+ report-name/param-interface 6 link/unlink
> 端点暂 stub 抛 UnsupportedOperationException，function-tree 在 B5 期间保持「规划」状态，target 下一批（B6 with Object）。

| 子项 ID | 名称 | 闭环定义 | 类型 | 状态 |
|---|---|---|---|---|
| M06.F01.I01 | 专项列表 | GET /api/inspection/specialties?keyword=：按 code/name 模糊匹配，平台级 | 接口 | 已上线 |
| M06.F01.I02 | 创建专项 | POST /api/inspection/specialties：code/officialNo/name 必填；isOfficial/enabled 默认 true；sortOrder 默认 0 | 接口 | 已上线 |
| M06.F01.I03 | 更新专项 | PUT /api/inspection/specialties/{code}：PATCH 语义，未传字段保留 | 接口 | 已上线 |
| M06.F01.I04 | 删除专项 | DELETE /api/inspection/specialties/{code}：204 if exists，否则 404 | 接口 | 已上线 |
| M06.F03.I01 | 参数列表 | GET /api/inspection/parameters?keyword=&sourceType=：按 code/name 模糊 + sourceType 过滤（official/custom） | 接口 | 已上线 |
| M06.F03.I02 | 创建参数 | POST /api/inspection/parameters：code/name/rawName/canonicalName 必填；sourceType 默认 OFFICIAL；aliases 默认 [] | 接口 | 已上线 |
| M06.F03.I03 | 更新参数 | PUT /api/inspection/parameters/{code}：PATCH 语义；aliases 传则整体替换 | 接口 | 已上线 |
| M06.F03.I04 | 删除参数 | DELETE /api/inspection/parameters/{code}：204 if exists | 接口 | 已上线 |
| M06.F04.I01 | 标准列表 | GET /api/inspection/standards?keyword=&status=：按 code/name 模糊 + status 过滤（active/superseded/draft） | 接口 | 已上线 |
| M06.F04.I02 | 创建标准 | POST /api/inspection/standards：code/name 必填；status 默认 ACTIVE | 接口 | 已上线 |
| M06.F04.I03 | 更新标准 | PUT /api/inspection/standards/{code}：PATCH 语义 | 接口 | 已上线 |
| M06.F04.I04 | 删除标准 | DELETE /api/inspection/standards/{code}：204 if exists | 接口 | 已上线 |
| M06.F07.I01 | 报告名称列表 | GET /api/report-names?keyword=：按 code/name 模糊 | 接口 | 已上线 |
| M06.F07.I02 | 报告名称详情 | GET /api/report-names/{code}：返回 InspectionReportName（含 extFields 反序列化的 `List<ExtFieldDef>`） | 接口 | 已上线 |
| M06.F07.I03 | 创建报告名称 | POST /api/report-names：code/name 必填；extFields 默认 [] | 接口 | 已上线 |
| M06.F07.I04 | 更新报告名称 | PUT /api/report-names/{code}：PATCH 语义 | 接口 | 已上线 |
| M06.F07.I05 | 删除报告名称 | DELETE /api/report-names/{code}：204 if exists | 接口 | 已上线 |
| M06.F08.I01 | 参数界面列表 | GET /api/param-interfaces?keyword=：按 code/name 模糊 | 接口 | 已上线 |
| M06.F08.I02 | 参数界面详情 | GET /api/param-interfaces/{code}：返回 ParamInterface（含 config 反序列化的 Map<String,Object>） | 接口 | 已上线 |
| M06.F08.I03 | 创建参数界面 | POST /api/param-interfaces：code/componentPath 必填；config 默认 {} | 接口 | 已上线 |
| M06.F08.I04 | 更新参数界面 | PUT /api/param-interfaces/{code}：PATCH 语义 | 接口 | 已上线 |
| M06.F08.I05 | 删除参数界面 | DELETE /api/param-interfaces/{code}：204 if exists | 接口 | 已上线 |

> Batch B6（M06.F02 objects 4 CRUD + 8 个 junction link/unlink 端点 = 20 端点 / 20 I）：
> 4 CRUD object 走 InspectionDictionaryApi（list+create+update+delete）；
> 4 个 junction 表（specialty-object / object-parameter / object-standard(role) / standard-parameter）
> + 3 个 report-name junction（object / parameter / standard(role)）+ 1 个 param-interface link = 8 link/unlink 对。
> junction key 设计：4 字典 junction 都用复合主键（SpecialtyObjectKey / ObjectParameterKey /
> ObjectStandardKey(role) / StandardParameterKey）；3 报告名 junction 用 ObjectReportNameKey /
> ReportNameParameterKey / ReportNameStandardKey(role)；1 参数界面用 ParamInterfaceLinkKey。
> 实体 PK 全部 id 端走 @IdClass（Serializable + serialVersionUID，SpotBugs SE_NO_SERIALVERSIONID）。
> 3 张 role-based junction（ObjectStandardKey、ReportNameStandardKey、InspectionObjectStandardEntity.role）走
> @Enumerated(STRING) 写大写常量名（与 PG enum 标签同款），无需 AttributeConverter。
> param_interface_links.config 走 jsonb 序列化 String（M06.F08.I06 链路）。

| 子项 ID | 名称 | 闭环定义 | 类型 | 状态 |
|---|---|---|---|---|
| M06.F02.I01 | 项目列表 | GET /api/inspection/objects?inspectionSpecialtyCode=&keyword=：按 code/name 模糊 + 专项过滤 | 接口 | 已上线 |
| M06.F02.I02 | 创建项目 | POST /api/inspection/objects：code/inspectionSpecialtyCode/sourceProjectNo/sourceProjectName/name 必填；isOptionalForQualification 默认 false；isOfficial/enabled 默认 true | 接口 | 已上线 |
| M06.F02.I03 | 更新项目 | PUT /api/inspection/objects/{code}：PATCH 语义 | 接口 | 已上线 |
| M06.F02.I04 | 删除项目 | DELETE /api/inspection/objects/{code}：204 if exists | 接口 | 已上线 |
| M06.F02.I05 | 专项↔项目 link | POST /api/inspection/links/specialty-object：建立 specialty→object 关联，remark 可选 | 接口 | 已上线 |
| M06.F02.I06 | 专项↔项目 unlink | DELETE /api/inspection/links/specialty-object：404 if 不存在 | 接口 | 已上线 |
| M06.F02.I07 | 项目↔参数 link | POST /api/inspection/links/object-parameter：建立 object→parameter 关联，qualificationLevel 默认 QUALIFIED，sourcePage/remark 可选 | 接口 | 已上线 |
| M06.F02.I08 | 项目↔参数 unlink | DELETE /api/inspection/links/object-parameter：404 if 不存在 | 接口 | 已上线 |
| M06.F01.I05 | 项目↔标准 link | POST /api/inspection/links/object-standard：建立 object→standard(role) 关联，role 必填（TESTING/JUDGMENT） | 接口 | 已上线 |
| M06.F01.I06 | 项目↔标准 unlink | DELETE /api/inspection/links/object-standard：404 if 不存在 | 接口 | 已上线 |
| M06.F03.I05 | 标准↔参数 link | POST /api/inspection/links/standard-parameter：建立 standard→parameter 关联 | 接口 | 已上线 |
| M06.F03.I06 | 标准↔参数 unlink | DELETE /api/inspection/links/standard-parameter：404 if 不存在 | 接口 | 已上线 |
| M06.F07.I06 | 项目↔报告名称 link | POST /api/report-names/links/object：建立 object→report-name 关联，remark 可选 | 接口 | 已上线 |
| M06.F07.I07 | 报告名称↔标准 link | POST /api/report-names/links/standard：建立 report-name→standard(role) 关联，role 必填 | 接口 | 已上线 |
| M06.F07.I08 | 报告名称↔参数 link | POST /api/report-names/links/parameter：建立 report-name→parameter 关联 | 接口 | 已上线 |
| M06.F04.I07 | 报告名称↔标准 unlink | DELETE /api/report-names/links/standard：404 if 不存在 | 接口 | 已上线 |
| M06.F04.I06 | 报告名称↔参数 unlink | DELETE /api/report-names/links/parameter：404 if 不存在 | 接口 | 已上线 |
| M06.F04.I05 | 项目↔报告名称 unlink | DELETE /api/report-names/links/object：404 if 不存在 | 接口 | 已上线 |
| M06.F08.I06 | 参数↔界面 link | POST /api/param-interfaces/links：建立 parameter→interface 关联，reportNameCode/config 可选（config 走 jsonb） | 接口 | 已上线 |
| M06.F03.I07 | 参数↔界面 unlink | DELETE /api/param-interfaces/links：404 if 不存在 | 接口 | 已上线 |
