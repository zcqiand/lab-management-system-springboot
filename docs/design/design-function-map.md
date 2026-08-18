# 设计与功能对齐 — 实验室管理系统SpringBoot后端

> 人填、人评审。机器只检查功能 ID 存在性。
> 回答一个问题：**这个功能子项，落到哪段代码、哪张表、哪个权限码上？**
> 答不上来的行，说明设计没做完，别开工。

## 映射表

| 功能子项 ID | 页面/组件 | 接口 | 数据表 | 权限码 | 设计稿 | 状态 |
|---|---|---|---|---|---|---|
| M00.F01.I01 | AuthController#authGetCurrentUser / AuthService#me | GET /api/auth/me | -（配置式目录） | M00.F01.I01 | - | 已上线 |
| M00.F02.I01 | AuthController#authSwitchTenant / AuthService#switchTenant | POST /api/auth/switch-tenant | -（配置式目录） | M00.F02.I01 | - | 已上线 |
| M01.F04.I01 | AuthController#authGetMenus / AuthService#menus | GET /api/auth/menus | - | M01.F04.I01 | - | 已上线 |
| M01.F04.I02 | AuthController#authGetPermissions / AuthService#permissions | GET /api/auth/permissions | - | M01.F04.I02 | - | 已上线 |
| M01.F05.I01 | AuthController#authLogin / AuthService#login | POST /api/auth/login | -（配置式目录） | M01.F05.I01 | - | 已上线 |
| M01.F05.I02 | AuthController#authSsoAuthorize / AuthService#ssoAuthorize | GET /api/auth/sso/authorize | - | M01.F05.I02 | - | 已上线 |
| M01.F05.I03 | AuthController#authSsoCallback / AuthService#ssoCallback | POST /api/auth/sso/callback | - | M01.F05.I03 | - | 已上线 |
| M01.F05.I04 | AuthController#authRefresh / AuthService#refresh | POST /api/auth/refresh | - | M01.F05.I04 | - | 已上线 |
| M01.F05.I05 | AuthController#authLogout / AuthService#logout | POST /api/auth/logout | - | M01.F05.I05 | - | 已上线 |
| M04.F06.I01 | InspectionCatalogController#catalogListModels / CatalogService#listModels | GET /api/catalog/models | inspection_models（V004，tenant_id V012） | M04.F06.I01 | - | 已上线 |
| M04.F06.I02 | InspectionCatalogController#catalogCreateModel / CatalogService#createModel | POST /api/catalog/models | inspection_models | M04.F06.I02 | - | 已上线 |
| M04.F06.I03 | InspectionCatalogController#catalogUpdateModel / CatalogService#updateModel | PUT /api/catalog/models/{code} | inspection_models | M04.F06.I03 | - | 已上线 |
| M04.F06.I04 | InspectionCatalogController#catalogDeleteModel / CatalogService#deleteModel | DELETE /api/catalog/models/{code} | inspection_models | M04.F06.I04 | - | 已上线 |
| M04.F07.I01 | InspectionCatalogController#catalogListSpecs / CatalogService#listSpecs | GET /api/catalog/specs | inspection_specs（V004） | M04.F07.I01 | - | 已上线 |
| M04.F07.I02 | InspectionCatalogController#catalogCreateSpec / CatalogService#createSpec | POST /api/catalog/specs | inspection_specs | M04.F07.I02 | - | 已上线 |
| M04.F07.I03 | InspectionCatalogController#catalogUpdateSpec / CatalogService#updateSpec | PUT /api/catalog/specs/{code} | inspection_specs | M04.F07.I03 | - | 已上线 |
| M04.F07.I04 | InspectionCatalogController#catalogDeleteSpec / CatalogService#deleteSpec | DELETE /api/catalog/specs/{code} | inspection_specs | M04.F07.I04 | - | 已上线 |
| M04.F08.I01 | InspectionCatalogController#catalogListGrades / CatalogService#listGrades | GET /api/catalog/grades | inspection_grades（V004） | M04.F08.I01 | - | 已上线 |
| M04.F08.I02 | InspectionCatalogController#catalogCreateGrade / CatalogService#createGrade | POST /api/catalog/grades | inspection_grades | M04.F08.I02 | - | 已上线 |
| M04.F08.I03 | InspectionCatalogController#catalogUpdateGrade / CatalogService#updateGrade | PUT /api/catalog/grades/{code} | inspection_grades | M04.F08.I03 | - | 已上线 |
| M04.F08.I04 | InspectionCatalogController#catalogDeleteGrade / CatalogService#deleteGrade | DELETE /api/catalog/grades/{code} | inspection_grades | M04.F08.I04 | - | 已上线 |
| M04.F09.I01 | InspectionCatalogController#catalogListBrands / CatalogService#listBrands | GET /api/catalog/brands | inspection_brands（V004） | M04.F09.I01 | - | 已上线 |
| M04.F09.I02 | InspectionCatalogController#catalogCreateBrand / CatalogService#createBrand | POST /api/catalog/brands | inspection_brands | M04.F09.I02 | - | 已上线 |
| M04.F09.I03 | InspectionCatalogController#catalogUpdateBrand / CatalogService#updateBrand | PUT /api/catalog/brands/{code} | inspection_brands | M04.F09.I03 | - | 已上线 |
| M04.F09.I04 | InspectionCatalogController#catalogDeleteBrand / CatalogService#deleteBrand | DELETE /api/catalog/brands/{code} | inspection_brands | M04.F09.I04 | - | 已上线 |
| M06.F05.I01 | CalculationRuleController#calculationRulesListCalculationRules / CalculationRuleService#list | GET /api/calculation-rules | inspection_calculation_rules（V009，平台级无 tenant_id） | M06.F05.I01 | - | 已上线 |
| M06.F05.I02 | CalculationRuleController#calculationRulesGetCalculationRule / CalculationRuleService#get | GET /api/calculation-rules/{object}/{parameter} | inspection_calculation_rules | M06.F05.I02 | - | 已上线 |
| M06.F05.I03 | CalculationRuleController#calculationRulesCreateCalculationRule / CalculationRuleService#create | POST /api/calculation-rules | inspection_calculation_rules | M06.F05.I03 | - | 已上线 |
| M06.F05.I04 | CalculationRuleController#calculationRulesUpdateCalculationRule / CalculationRuleService#update | PUT /api/calculation-rules/{object}/{parameter} | inspection_calculation_rules | M06.F05.I04 | - | 已上线 |
| M06.F05.I05 | CalculationRuleController#calculationRulesDeleteCalculationRule / CalculationRuleService#delete | DELETE /api/calculation-rules/{object}/{parameter} | inspection_calculation_rules | M06.F05.I05 | - | 已上线 |
| M06.F06.I01 | TechnicalRequirementController#technicalRequirementsListTechnicalRequirements / TechnicalRequirementService#list | GET /api/technical-requirements | inspection_technical_requirements（V005，V012 加 tenant_id） | M06.F06.I01 | - | 已上线 |
| M06.F06.I02 | TechnicalRequirementController#technicalRequirementsGetTechnicalRequirement / TechnicalRequirementService#get | GET /api/technical-requirements/{object}/{parameter}/{standard} | inspection_technical_requirements | M06.F06.I02 | - | 已上线 |
| M06.F06.I03 | TechnicalRequirementController#technicalRequirementsCreateTechnicalRequirement / TechnicalRequirementService#create | POST /api/technical-requirements | inspection_technical_requirements | M06.F06.I03 | - | 已上线 |
| M06.F06.I04 | TechnicalRequirementController#technicalRequirementsUpdateTechnicalRequirement / TechnicalRequirementService#update | PUT /api/technical-requirements/{object}/{parameter}/{standard} | inspection_technical_requirements | M06.F06.I04 | - | 已上线 |
| M06.F06.I05 | TechnicalRequirementController#technicalRequirementsDeleteTechnicalRequirement / TechnicalRequirementService#delete | DELETE /api/technical-requirements/{object}/{parameter}/{standard} | inspection_technical_requirements | M06.F06.I05 | - | 已上线 |
| M02.F01.I01 | ContractController#contractsListContracts / ContractService#list | GET /api/contracts | contracts（V001 + V012 tenant_id） | M02.F01.I01 | - | 已上线 |
| M02.F01.I02 | ContractController#contractsGetContract / ContractService#get | GET /api/contracts/{id} | contracts | M02.F01.I02 | - | 已上线 |
| M02.F01.I03 | ContractController#contractsCreateContract / ContractService#create | POST /api/contracts | contracts | M02.F01.I03 | - | 已上线 |
| M02.F01.I04 | ContractController#contractsUpdateContract / ContractService#update | PUT /api/contracts/{id} | contracts | M02.F01.I04 | - | 已上线 |
| M02.F01.I05 | ContractController#contractsDeleteContract / ContractService#delete | DELETE /api/contracts/{id} | contracts | M02.F01.I05 | - | 已上线 |
| M03.F01.I01 | SampleReceiptController#receiptsListReceipts / SampleReceiptService#list | GET /api/receipts | sample_receipts（V002 + V012 tenant_id） | M03.F01.I01 | - | 已上线 |
| M03.F01.I02 | SampleReceiptController#receiptsGetReceipt / SampleReceiptService#get | GET /api/receipts/{id} | sample_receipts | M03.F01.I02 | - | 已上线 |
| M03.F01.I03 | SampleReceiptController#receiptsCreateReceipt / SampleReceiptService#create | POST /api/receipts | sample_receipts（contract_id FK V002，flow_status=receiving 起步；flow_history 起步空数组） | M03.F01.I03 | - | 已上线 |
| M03.F01.I04 | SampleReceiptController#receiptsUpdateReceipt / SampleReceiptService#update | PUT /api/receipts/{id} | sample_receipts | M03.F01.I04 | - | 已上线 |
| M03.F01.I05 | SampleReceiptController#receiptsDeleteReceipt / SampleReceiptService#delete | DELETE /api/receipts/{id} | sample_receipts（CASCADE → samples） | M03.F01.I05 | - | 已上线 |
| M03.F01.I06 | SampleReceiptController#receiptsGetReceiptHistory / SampleReceiptService#history | GET /api/receipts/{id}/history | sample_receipts.flow_history (jsonb) | M03.F01.I06 | - | 已上线 |
| M03.F02.I01 | SampleReceiptController#receiptsAssignTask / SampleReceiptService#assignTask | PUT /api/receipts/{id}/task | sample_receipts（assignee_id/Name/plannedTestDate） | M03.F02.I01 | - | 已上线 |
| M03.F03.I01 | SampleController#samplesListSamples / SampleService#list | GET /api/samples | samples（V002 receipt_id FK） | M03.F03.I01 | - | 已上线 |
| M03.F03.I02 | SampleController#samplesGetSample / SampleService#get | GET /api/samples/{id} | samples | M03.F03.I02 | - | 已上线 |
| M03.F03.I03 | SampleController#samplesCreateSample / SampleService#create | POST /api/samples | samples（receipt_id FK 必存在；ext 默认 {}） | M03.F03.I03 | - | 已上线 |
| M03.F03.I04 | SampleController#samplesUpdateSample / SampleService#update | PUT /api/samples/{id} | samples | M03.F03.I04 | - | 已上线 |
| M03.F03.I05 | SampleController#samplesDeleteSample / SampleService#delete | DELETE /api/samples/{id} | samples | M03.F03.I05 | - | 已上线 |
| M03.F05.I01 | ReportFlowController#reportFlowListFlowQueue / ReportFlowService#flowQueue | GET /api/receipts/flow/queue?stage= | sample_receipts（按 flow_status 过滤 + tenant 收口，cap 默认 50） | M03.F05.I01 | - | 已上线 |
| M03.F06.I01 | ReportFlowController#reportFlowSubmitFlowAction / ReportFlowService#submitAction | POST /api/receipts/flow | sample_receipts.flow_status + flow_history | M03.F06.I01 | - | 已上线 |

> B1 说明：lab_dev 无身份表（shared SQL SSOT 不含 users/tenants），认证域用户/租户走
> `io.xr.lab.platform.directory.ConfigUserDirectory`（配置式，镜像 lab-msw seeds）。
> 「数据表」列的 `-（配置式目录）` 即指此处；V014 identity 表落地后回填。

> B2 说明：码表 4 表 + 计算规则 + 技术要求共 6 表都已在 shared 仓 sql/migrations V004/V005/V009 + V012 落地，本仓直接读 Flyway baseline。PK 设计：码表 4 表 = (tenant_id, code) 复合主键（V012 约束对齐）；计算规则 = (object, parameter) 复合主键；技术要求 PK = 业务三键 (object, parameter, standard)，tenant_id 走 WHERE 过滤。

> B3 说明：合同 V001（PK = id text）+ V012 加 tenant_id；接样单 V002 PK = id text、FK 到 contracts（RESTRICT）+ samples（CASCADE）+ 3 个 jsonb 列（judgment_basis/testing_basis/test_parameters/FlowHistoryEntry[]）走 @JdbcTypeCode(SqlTypes.JSON) 映射；8 个 PG enum（contract_status/flow_status/receipt_result/calculation_algorithm_type/4 requirement_*）经 V014+V015 改为 TEXT + AttributeConverter。

## 约定

1. **权限码 = 功能子项 ID。** 前端按钮的权限判断直接写 ID。
2. 一个接口服务多个子项时，多行重复写。不要为表好看而合并 —— 合并后看不清接口还有没有别的调用方。
3. 状态列必须与功能清单一致。不一致以功能清单为准。

## 评审时问这三个问题

1. 有没有子项没有权限码？→ 那它就是任何人都能点的按钮
2. 有没有一张表被三个以上模块直接写入？→ 边界破了
3. 「开发中」的行里接口和表填了吗？→ 没填就是还在纸上，别报进度
