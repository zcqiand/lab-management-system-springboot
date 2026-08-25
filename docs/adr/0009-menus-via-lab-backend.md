# 0009 — 菜单数据源切换：前端直连 saas 改为经 lab 后端 /api/auth/menus

> 状态：已采纳
> 日期：2026-08-25
> 范围：lab-management-system（springboot + react 前端 + shared 契约；vue/nextjs 待同链迁移）
> 关联：ADR-0008（真后端 OAuth 2.0 + JWT）、ADR-0014（env-driven 单 URL）

## Context

Sprint 2 起 lab 前端（react/vue/nextjs）的侧边栏菜单走**前端直连 saas** 链路：

- 浏览器 `fetch /api/saas/me/menus?appCode=lab-management`
- dev 期由各前端 vite.config.ts `server.proxy` 转发到 saas `GET /api/v1/me/menus`（同源避开 CORS preflight）；prod 期需 nginx 配等价转发 + saas 侧域名白名单
- lab-msw 明确不兜底该端点（handlers-extra.ts:149-152），saas 不可达时前端降级静态 `MENU_TREE`

该链路的问题（事实）：

1. **前端与 saas 耦合**：每个 lab 前端域都要 saas 白名单 + 部署层转发配置；saas 一处变更牵动 N 个前端仓
2. **lab JWT 鉴权链路不覆盖菜单**：菜单请求不吃 lab Bearer token，与 ADR-0008 建立的 lab 自家 JWT 体系平行
3. **ADR-0008 的约束**：lab 后端只在 SSO/refresh 瞬时持有 saas accessToken（用完即弃、无 DB 持久化），**平时没有 saas 凭据**可代表用户调 saas
4. shared 契约（openapi.yaml）已有 `GET /api/auth/menus` 端点定义，springboot v0.1.7 / react v0.2.13 前该端点无真实数据链

## Decision

菜单统一改走 **lab 后端 `GET /api/auth/menus`（orval `authGetMenus` + Bearer lab JWT）**，后端数据链采用**方案 B：saas 快照缓存 + demo 兜底**：

1. **快照采集**：`SaasMeClient.listMyMenus` 在 SSO callback / refresh token **瞬时持有 saas accessToken** 时拉 `saas /api/v1/me/menus?appCode=lab-management`（`EffectiveMenuNode[]` 树）
2. **缓存**：`MenuSnapshotCache` 按 userId 进程内缓存，TTL 30min；`cacheMenus` 失败只 warn 不阻塞登录
3. **读取**：`AuthService.menus(claims)` 缓存优先；miss 回退 `FALLBACK_MENUS`（原 demo 菜单提取为常量）——端点**永不 5xx**
4. **映射**：`SaasMenuMapper` 做 `name→label` / icon 按 type 兜底 / sortOrder 排序，对齐 shared 契约 `MenuNode{id,label,path?,icon?,children?}`
5. **前端适配**：react v0.2.13 `useBackendMenus`（sidebar-nav.tsx），`adaptContractMenu` 把契约节点适配为本地渲染树（有子节点即 group，否则 page）；请求失败仍回退静态 `MENU_TREE`（双层兜底：后端 demo → 前端静态）

## Alternatives considered

### 方案 A：前端继续直连 saas（维持现状）

被拒绝，因为前端×saas 的白名单/转发耦合不可收敛，菜单游离在 lab JWT 鉴权外，且 saas 故障直接击穿到前端静态兜底。

### 方案 C：lab 后端实时代理（每请求转发 saas /me/menus）

被拒绝，因为 lab 后端平时不持有 saas accessToken（ADR-0008：saas token 不出后端、无 DB 持久化）；每请求代理要么重放 saas refresh token（扩大其暴露面）要么必 401。

### 方案 D：菜单快照持久化进 lab 数据库

被拒绝，因为 lab 后端当前无 DB（ADR-0008 明确无持久化层），为菜单单独立库代价不成比例。

## Consequences

正面：

- 前端只认 lab 后端单 URL，与 ADR-0014 env-driven 架构一致；saas 域名/白名单配置从 N 个前端仓消失
- 菜单纳入 lab JWT 鉴权；未登录/无效 token 拿不到菜单
- saas 故障被后端快照 + demo 兜底隔离，sidebar 可用性不再依赖 saas 在线
- vue/nextjs 可按 react 同款 `useBackendMenus` 模式迁移，契约与后端零改动

负面（必填）：

- 快照 TTL 30min 内 saas 侧菜单变更不生效，需重新 SSO/refresh 或等 TTL 过期
- 进程内缓存重启即清；重启后首批用户拿到 demo 兜底菜单直至下次 SSO/refresh
- demo 兜底与 saas 实际配置可能不一致——用户看到「假菜单」时无显式标识，排查菜单来源多一层（后端 demo / 前端静态两级降级）
- `cacheMenus` 失败只 warn 不阻塞登录，快照缺失可能长期静默
