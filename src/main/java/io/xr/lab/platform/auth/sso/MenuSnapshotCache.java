package io.xr.lab.platform.auth.sso;

import io.xr.lab.shared.dto.MenuNode;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

/**
 * MenuSnapshotCache — SSO/refresh 时拉取的 saas 菜单快照，按 userId 进程内缓存。
 *
 * <p>背景（ADR-0008 B1 + 菜单统一走后端）：saas 无 client_credentials、lab 库无 menus 表， 唯一拿到「按用户角色过滤的菜单」的时点是 SSO
 * 回调/refresh 时瞬时持有 saas accessToken。 在那两个时点顺手调一次 saas /api/v1/me/menus，映射成契约 MenuNode 后存入本缓存； GET
 * /api/auth/menus 按 JWT sub 读，miss（密码登录/过期/重启）回退静态 demo 菜单。
 *
 * <p>局限（单实例部署下可接受）：进程内缓存多实例不共享；TTL 30min 后需 refresh 或重登 重新填充。菜单变更生效时延 = min(refresh 周期, TTL)。
 */
@Component
public class MenuSnapshotCache {

  /** 快照记录：菜单树 + 过期时刻。 */
  record MenuSnapshot(List<MenuNode> menus, Instant expiresAt) {}

  private final ConcurrentHashMap<String, MenuSnapshot> store = new ConcurrentHashMap<>();
  private final Clock clock;
  private final Duration ttl;

  public MenuSnapshotCache() {
    this(Clock.systemUTC(), Duration.ofMinutes(30));
  }

  /** 测试用构造器（可注入假时钟与短 TTL）。 */
  MenuSnapshotCache(Clock clock, Duration ttl) {
    this.clock = clock;
    this.ttl = ttl;
  }

  /** 写入/覆盖某用户的菜单快照。 */
  public void put(String userId, List<MenuNode> menus) {
    if (userId == null || menus == null) {
      return;
    }
    store.put(userId, new MenuSnapshot(List.copyOf(menus), clock.instant().plus(ttl)));
  }

  /** 读某用户的未过期快照；miss/过期返回 empty（调用方回退 demo 菜单）。 */
  public Optional<List<MenuNode>> get(String userId) {
    if (userId == null) {
      return Optional.empty();
    }
    MenuSnapshot snap = store.get(userId);
    if (snap == null) {
      return Optional.empty();
    }
    if (clock.instant().isAfter(snap.expiresAt())) {
      store.remove(userId);
      return Optional.empty();
    }
    return Optional.of(snap.menus());
  }

  /** 当前缓存条目数（监控/测试用）。 */
  public int size() {
    return store.size();
  }

  /** 供 Map copy 需要的只读视图（测试用）。 */
  Map<String, MenuSnapshot> snapshot() {
    return Map.copyOf(store);
  }
}
