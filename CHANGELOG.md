# CHANGELOG — lab-management-system-springboot

格式参照 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.1.0/)。

## [0.1.26] — 2026-08-28

- fix(sso): SaasMeClient.listMyMenus 解析 saas /api/v1/me/menus 新形状 `Map<appCode, List<EffectiveMenuNode>>`
  （v0.2.12 saas-springboot MeService.getMyMenus 真实现后）。原 `?appCode=` query + `SaasMenuNode[]`
  扁平响应已弃，saas 一次性返用户在所有 app 下的有效菜单树，lab 按 `LAB_APP_CODE` 取子树。
- test: 新增 `SaasMeClientListMyMenusTest` 3 个用例 — 解析 Map 取 appCode、appCode 空数组、appCode 缺失返 null。
- 对称 saas-springboot v0.2.12：MeService 真实现 + gen-shared 脚本兼容 saas.identity.shared.api 残留清理。

## [0.1.22] — 2026-08-27

- 初始化台账：Java 21 + Spring Boot 3.4 后端。历史变更见 git log 与 `.state/session.json`。
