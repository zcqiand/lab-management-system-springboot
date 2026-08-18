# 流程与功能对齐 — 实验室管理系统SpringBoot后端

> 人填、人评审。机器只检查引用的功能 ID 是否存在。
> 评审时把流程图投出来，逐行念「这一步靠哪些功能完成」。念不出来的行，
> 要么流程是空的，要么功能是缺的。这就是对齐的全部意义。

## FLOW-01 认证与会话（B1）

```mermaid
flowchart TD
    S01[登录/SSO] --> S02[选租户]
    S02 --> S03[会话使用]
    S03 --> S04[登出]
    S03 --> S05[token 刷新]
    S05 --> S03
```

| 步骤 | 名称 | 角色 | 输入 | 输出 | 状态流转 | 支撑功能子项 |
|---|---|---|---|---|---|---|
| S01 | 登录（密码或 SSO 跳 saas） | 所有用户 | username/password 或 sso code | access/refresh token + 租户列表 | anonymous -> awaiting_tenant | M01.F05.I01, M01.F05.I02, M01.F05.I03 |
| S02 | 选租户换发 token | 所有用户 | tenantId | 携带 tenant_id claim 的新 token | awaiting_tenant -> authenticated | M00.F02.I01 |
| S03 | 会话使用（me/菜单/权限） | 所有用户 | Bearer token | user + tenants + currentTenantId / 菜单树 / 权限集 | authenticated | M00.F01.I01, M01.F04.I01, M01.F04.I02 |
| S04 | 登出 | 所有用户 | Bearer token | 204 | authenticated -> anonymous | M01.F05.I05 |
| S05 | token 刷新 | 所有用户 | refreshToken | 新 access token | authenticated（续期） | M01.F05.I04 |

### 评审时问这四个问题

1. 有没有哪个步骤的「支撑功能子项」是空的？→ 功能缺失，或这一步不该存在
2. 有没有功能子项从头到尾没出现在任何流程里？→ 见下方孤儿清单
3. 状态流转列里的状态名，和代码里的枚举一致吗？→ 不一致就是两套真相
4. 退回路径都画了吗？→ 只画正向流程，会漏掉一半功能

## FLOW-02 字典维护（B2：M04 基础数据 + M06 计算规则/技术要求）

> 「字典维护」是 M04/M06 子模块的批量录入/查询/编辑流程；
> 真实业务系统的试验流程（M03 业务链路）尚未落地，本批次只登记数据维护闭环。

```mermaid
flowchart LR
    S01[列出码表] --> S02[创建条目]
    S01 --> S03[查询条目]
    S03 --> S04[编辑条目]
    S03 --> S05[删除条目]
    S05 --> S01
```

| 步骤 | 名称 | 角色 | 输入 | 输出 | 状态流转 | 支撑功能子项 |
|---|---|---|---|---|---|---|
| S01 | 列出码表 | 配置员 | 4 过滤（object / keyword） | Model/Spec/Grade/Brand[] | — | M04.F06.I01, M04.F07.I01, M04.F08.I01, M04.F09.I01 |
| S02 | 创建条目 | 配置员 | 表单（code / name + 可选 object / remark / sortOrder） | 新增行 | — | M04.F06.I02, M04.F07.I02, M04.F08.I02, M04.F09.I02 |
| S03 | 查询条目 | 配置员 | 4 过滤（object / parameter / standard / status） | CalculationRule[] / TechnicalRequirement[] | — | M06.F05.I01, M06.F06.I01 |
| S04 | 编辑条目 | 配置员 | PATCH 表单 | 更新后行 | — | M04.F06.I03, M04.F07.I03, M04.F08.I03, M04.F09.I03, M06.F05.I04, M06.F06.I04 |
| S05 | 删除条目 | 配置员 | code | 204 | — | M04.F06.I04, M04.F07.I04, M04.F08.I04, M04.F09.I04, M06.F05.I05, M06.F06.I05 |

### 评审时问这四个问题（FLOW-02）

1. 有没有哪个步骤的「支撑功能子项」是空的？→ 功能缺失。
2. 有没有功能子项从头到尾没出现在任何流程里？→ M04.F09、F05 的「列表」已纳入；按需向前面步骤说清为什么合法。
3. 状态流转列里的状态名，和代码里的枚举一致吗？→ 本流程无状态机（纯字典维护），M06.F06 的 verificationStatus enum 出现在 I01 过滤参数。
4. 退回路径都画了吗？→ 本流程无退回（M04/M06 子项非流程审批对象）。

### 孤儿功能（B2）

| 功能 ID | 为什么合法 |
|---|---|
| M04.F06.I01 | 列表是 FLOW-02 字典维护的 S01（实际已纳入 S01） |
| M04.F07.I01 | 列表 S01 |
| M04.F08.I01 | 列表 S01 |
| M04.F09.I01 | 列表 S01 |
| M06.F05.I01 | 列表 S03 |
| M06.F06.I01 | 列表 S03 |

> 步骤表里已登记 26 个 I 级（4 list + 4 create + 4 update + 4 delete + 2 list + 2 list + 2 update + 2 delete = 26）。
> B2 全部子项已在 FLOW-02 找到归属，无孤儿。

---

## FLOW-03 （异常流程名）

> 异常流程单独成表，否则它承载的功能永远是孤儿。

### 孤儿功能

不在任何流程里但合法的功能。**没解释的孤儿 = 没人要的功能。**

| 功能 ID | 为什么合法 |
|---|---|

