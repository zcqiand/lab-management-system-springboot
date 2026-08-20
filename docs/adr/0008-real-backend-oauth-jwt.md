# 0008 — 真后端 OAuth 2.0 + JWT 签发

> 状态：已采纳
> 日期：2026-08-19
> 范围：lab-management-system (springboot + aspnetcore + shared)
> 取代：B1 时代 `alg=none` JWT + SSO mock 跳转(仅拼 saas 登录页 URL)

## Context

lab-management-system 多仓家族长期在 B1 "鉴权占位" 状态:

- **JWT 签发**:`alg=none` + 签名段 `.dev-placeholder`,任何篡改都能解;`SecurityConfig.DevJwtDecoder` / `Program.RequireSignedTokens=false` + `SignatureValidator` 主动绕过签名
- **SSO 跳转**:`SsoAuthorize` 仅拼 `saasBase + "/login?redirect=...&state=mock-state"`,**没调 saas `/oauth/authorize`**
- **SSO 回调**:`SsoCallback` 直接返回 admin 会话,**没调 saas `/oauth/token` 兑 token、没调 saas `/me/whoami` 拿用户**
- **refresh**:字符串拼接 `refresh-<user>-<epoch>`,无任何加密保护

业务面 "已上线",但鉴权层是 B1 时代占位。

## Decision

### 1. Algorithm: HMAC HS256(对称)

- 选用环境变量 `LAB_JWT_SECRET`(≥32 字节,启动时校验,缺失即抛 `IllegalStateException` 阻断 bean 创建)
- 头部 alg 字段固定 `HS256`,不允许 `alg=none`
- 三段格式 `base64url(header).base64url(payload).base64url(HMAC-SHA256)`,payload 字段按字典序输出
- 验证:`NimbusJwtDecoder.withSecretKey(signer.secretKey()).macAlgorithm(MacAlgorithm.HS256).build()` 强制签名验证 + iss + exp 全部开启

### 2. OAuth 2.0:Authorization Code flow 直连 saas

**lab 端持 `client_id` / `client_secret` 调 saas endpoint**:

- `POST /api/v1/oauth/authorize`(§4.1.1) → 拿 `code`
- `POST /api/v1/oauth/token`(§4.1.3 authorization_code grant) → 拿 saas `accessToken` + `refreshToken`
- `GET /api/v1/me` + `GET /api/v1/me/tenants`(Bearer) → 拿 `CurrentUser` + 租户列表

**Saas token 不出 lab**:后端用 saas access token 调 `/me/*`,把 user info 提取后立刻丢弃 saas token;sso refreshToken 嵌进 lab 自家 JWT 的 `saas_refresh_token` claim,lab 后端无 DB 持久化(saas refresh token 跟随 lab refresh token 生命周期,只要 lab refresh token 不过期就能续)。

### 3. State CSRF:HttpOnly Secure Cookie + HS256 签 state

> **修订(2026-08-20)**:本节方案已废弃,改为 RFC 6749 §10.12 标准 state —— 前端生成 csrfState、
> 后端 authorize 原样透传给 saas、回跳 `?state=` 由前端与 sessionStorage 比对;后端不生成、不
> 校验 state,`StateCookieManager` 已删除。原因:① 自造 cookie+nonce 配对与前端 csrfState 校验
> 语义冲突(后端偷换 state 导致前端比对必败);② cookie `SameSite=Lax` + 跨源前端(5173 → 8080)
> 根本带不上,机制实际是坏的;③ 非标准做法,与 react/vue/msw 各仓实现不一致。原方案留档如下。

- `authorize` 时生成随机 nonce + 业务载荷(`{redirect, ts}`) + HS256 签名 = cookie value `nonce.signature.payload`
- 后端 set `lab_sso_state` cookie(HttpOnly; Secure dev=false; SameSite=Lax; Path=/api/auth/sso/callback; Max-Age=300)
- saas `?state=` 写 nonce
- `callback` 时校验 `body.state == cookie nonce` + cookie 签名 + 未过期

签名密钥复用 `LAB_JWT_SECRET`(同进程只需要一个 HMAC 密钥;分仓验证会同时验 JWT sig + state sig)。

### 4. Refresh:走 saas `/oauth/token` grantType=refresh_token

- lab refresh token 是 HS256 JWT,`typ=refresh`,载荷固定 `sub` + `tenant_id`(可选) + `saas_refresh_token` + `iat` + `exp`(7d TTL)
- 收到 refresh 请求:验 lab refresh token sig → 抽 `saas_refresh_token` → 调 saas `/oauth/token` grantType=refresh_token → 拿新 saas accessToken + refreshToken → 再调 saas `/me/*` 确认 user → 签新 lab access + refresh token
- saas refresh token 嵌进 lab refresh token 内(本设计的核心妥协,见 "Trade-offs")

### 5. username → email 桥接(主键问题)

- saas `CurrentUser` 字段:`id` (uuid) + `email` + `displayName` + `memberships`(无 `username`)
- lab `ConfigUserDirectory` 主键从 `username="admin"` 改为 `email-style "admin@lab.local"`,`id="USER-A"`
- 增加 `UserDirectory.findByEmail(String)` + `findById(String)` + `upsert(id, email, displayName, roleCode)`
- SSO 路径:saas 拿 email → 查 lab directory → 不存在则 `upsert`(默认 roleCode="viewer")
- me/switchTenant:用 `sub` claim (user.id) 优先按 `findById` 查,退化 `findByEmail` / `findByUsername`

### 6. dev 降级:`no-sso` profile

- `application.yml` 加 `spring.config.activate.on-profile: ${LAB_PROFILE:no-sso}` 默认值
- `no-sso` 模式:`SsoBeansConfig` 注册 `NoopSaasAuthClient` + `NoopSaasMeClient`,行为固定为 admin session + 3 租户种子(镜像 lab-msw handlers-extra.ts)
- `default` 模式:注册真 HTTP `SaasAuthClient`(RestClient 调用)
- dev 不启 saas 也能跑完整 B1 流程;CI/CT 切 `default` profile 强制真对接

### 7. 用户 / 上下文

- springboot:`LabJwtSigner` (HMAC) + `SaasAuthClient` (RestClient) + `StateCookieManager` (HS256) + `SsoBeansConfig` (profile 切)
- aspnetcore:`LabJwtSigner` (HMAC) + `HttpSaasAuthClient` (HttpClient + `SaasErrorMappingHandler`) + `StateCookieManager` + `IServiceCollection` profile 切
- `mvn spotless:apply` / `dotnet format` 是 L1 必备,新增文件需过

## Trade-offs

### T1. saas refresh token 内嵌 lab JWT(放弃持久化)

| 选项 | 利 | 弊 |
|---|---|---|
| **A. 嵌进 lab JWT claim (本设计)** | 无 DB 依赖,无 schema 变更,无新增 host 表 | saas refresh token 7 天内强制续;泄漏面 = 全 lab refresh token |
| B. 新增 `lab_saas_token_store` 表(per-user encrypted) | saas refresh token 与 lab refresh token 解耦,泄漏面小 | 需要 V014 identity 表 + encryption key + 密钥轮转 schema,跨 sprint 工作量 |
| C. saas 端签长 token(>7d) | 与 A 等价但泄漏更严重 | 同 A + saas 端也要改 |

**结论**:选 A。本期可接受(sso 登录频次低,7 天 TTL 内一般能续);未来工单:**加 `lab_saas_token_store` 表 + per-user encrypt**,移除 saas_refresh_token claim。

### T2. username 主键改为 email(id 主键预留)

| 选项 | 利 | 弊 |
|---|---|---|
| **A. 主键 = email (本设计)** | 兼容 saas `CurrentUser.email`,无需 id 桥接 | email 重复会让 lab 误识别 |
| B. 主键 = saas uuid (id) | 唯一性强,saas CurrentUser.id 即唯一 | 需要重写 ConfigUserDirectory 缓存逻辑,影响所有 Service |
| C. 双主键(email + id 复合索引) | 兼容且唯一 | 复杂度上升,DevUserDirectory 内存结构变复杂 |

**结论**:选 A,长期方案记 "未来工单:目录主键改为 saas uuid,email 仅作展示"。

### T3. OAuth state 仅做"非空 + 签名",不做 bind to session

`state_cookie` 是 HttpOnly Secure + SameSite=Lax,攻击者跨站无法读 / 改,但仍可在同一浏览器内发起"并行"CSRF(同一用户)。

- 简化方案:仅依赖 Cookie + SameSite=Lax(已采用)
- 强方案:state cookie + session ID 绑定(需要后端 session 存储)
- 决定:简化方案;若发现 CSRF 真实攻击再升级

### T4. 跨仓时序

- `shared` 先发:
  - `auth.tsp` `SsoCallbackRequest` 必须先有 `state` 字段,否则两后端 codegen 拿不到
- `springboot` `aspnetcore` 并行 PR:
  - `gen-shared.sh` 脚本开头 `npm run build` 校验 shared openapi 已含 `state` 才执行 codegen(早失败)
- `function-tree.md` 三仓同 commit 改 I02/I03/I04 描述(per "改功能与改功能清单必须同一个 commit" 硬约束)

## Consequences

- ✅ JWT 真签名,任意篡改会被拒
- ✅ SSO 真调 saas `/oauth/authorize` + `/oauth/token`,saas 端能审计到 grant flow
- ✅ Refresh 真走 saas 续签,和 msw 模式兼容
- ✅ dev 离线模式保留(no-sso profile),前端 msw 链路不需要任何改动
- ⚠️ 跨仓时序敏感(env 缺失 / shared 未发都会 block)
- ⚠️ username 主键暂时改为 email,长期需迁移到 saas uuid

## 未来工单

1. `lab_saas_token_store` 表 + encrypt service(替代 T1 妥协)
2. `ConfigUserDirectory` 主键改 saas + 增 SQL identity schema
3. msw handler 字段对齐真后端 token(claims / exp / refresh token 形态)
4. state cookie 与 session ID 绑定(若观察到 CSRF)
