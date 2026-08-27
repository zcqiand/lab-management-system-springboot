package io.xr.lab.platform.service;

/**
 * 菜单快照不可用（GET /api/auth/menus miss）。
 *
 * <p>2026-08-27 起 demo 兜底菜单删除：快照 miss（密码登录未拉到 saas 菜单 / TTL 过期 / 进程重启） 不再返回假树，而是抛本异常由
 * GlobalExceptionHandler 映射 503 {@code MENUS_UNAVAILABLE}。前端 useBackendMenus 失败回退静态菜单（FALLBACK_NAV
 * / MENU_TREE），语义闭环。
 */
public class MenusUnavailableException extends RuntimeException {

  public MenusUnavailableException(String message) {
    super(message);
  }
}
