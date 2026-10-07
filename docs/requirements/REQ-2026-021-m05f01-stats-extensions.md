# REQ-2026-021 M05.F01 仪表盘统计扩展——I03 核心指标 + I04 任务漏斗（springboot）

| 项 | 值 |
|---|---|
| 提出人 | Claude 机器侧规划推进 |
| 提出日期 | 2026-10-07 |
| 优先级 | P1 |
| 状态 | 开发中 |
| 关联 ADR | ADR-0042（mirror 免批三类） |

## 1. 需求描述

springboot 侧 SummaryService 已于 2026-09-04 实现全部 I03/I04 扩展段
（todayTestCount/qualifiedRateByMaterial/reportOutputByStatus/funnelByStage，
javadoc 自证），aspnetcore/fastapi 同名行已上线，但 springboot 树两行仍规划、
SummaryServiceTest 只锚定 I01/I06——实现已落地而账面与测试锚滞后。

本需求补齐账面与锚定：树 2 行规划→开发中（随本 REQ，mirror --apply）、
设计映射 2 行（镜像 aspnetcore 同名行）、单元测试 @Fn 锚定 I03（材料合格率
码表映射 + todayTestCount + reportOutputByStatus）与 I04（六段漏斗分桶）。
不改动任何生产代码。

### 澄清记录

| 疑问 | 澄清结论 | 澄清人 | 日期 |
|---|---|---|---|
| 是否写新实现？ | 否——SummaryService 扩展段已在库，本 REQ 只补测试锚 + 账面 | Claude | 2026-10-07 |
| 漏斗语义锚点 | testing=data_entry 无 reportCode；reporting=data_entry 有 reportCode（与 aspnetcore 同款） | Claude | 2026-10-07 |

## 2. 验收标准

| 编号 | 场景（给定） | 操作（当） | 预期（则） |
|---|---|---|---|
| AC-1 | SummaryServiceTest 无 I03/I04 锚 | 补 @Fn 单测（码表映射合格率/今日试验/产出量/六段漏斗） | mvn test 绿，trace I03/I04 各锚 ≥1 |
| AC-2 | 树 I03/I04 规划 | tree_change.py 正门随本 REQ 推进开发中 | 树/设计映射/台账三账一致 |
| AC-3 | 全门 | gate.py | EXIT=0 |

## 3. 任务拆解

| 任务 ID | 任务描述 | 类型 | 负责人 | 预估 | 状态 |
|---|---|---|---|---|---|
| T-1 | REQ + 树 2 行推进开发中（mirror --apply）+ 设计映射 2 行 + 台账 | 流程 | Claude | 0.5h | 完成 |
| T-2 | SummaryServiceTest 补 @Fn I03/I04 单测 | 测试 | Claude | 1h | 完成 |
| T-3 | 全门（spotless/spotbugs/compile/test + trace + gate）+ 提交推送 | 门禁 | Claude | 0.5h | 完成 |

## 4. 功能影响（需求与功能对齐的唯一位置）

| 功能 ID | 功能名称 | 影响类型 | 变更 | 关联任务 |
|---|---|---|---|---|
| M05.F01.I03 | 核心指标卡 | 变更 | 规划→开发中：服务端扩展段已在库，补单测锚定（码表 summaryName 关键词映射，全量预载防 N+1） | T-1/T-2 |
| M05.F01.I04 | 任务状态漏斗 | 变更 | 规划→开发中：funnelByStage 六段已在库，补单测锚定（data_entry 按 reportCode 分 testing/reporting） | T-1/T-2 |
