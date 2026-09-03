package io.xr.lab.platform.auth.sso;

import io.xr.lab.shared.dto.MyTenant;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MembershipSnapshotCache — SSO/refresh 时到手的 saas memberships 租户快照，按 userId 进程内缓存。
 *
 * <p>2026-09-03 租户体系对齐（aspnetcore 仓
 * docs/superpowers/specs/2026-09-03-me-tenant-alignment-design.md）： me() 对 SSO 用户必须返回 saas
 * memberships 租户（与 ssoCallback 同体系），否则前端 hydrateAuth 的
 * tenants.find(localStorage.activeTenantId=saas UUID) 跨体系失配 → awaiting_tenant → 卡「检查登录态…」。 miss 时
 * me() 抛 401（SecurityException）由前端 refresh 链自愈。
 *
 * <p>与 {@link MenuSnapshotCache} 同款局限：进程内、单实例、TTL 后需 refresh 重新填充。
 */
public class MembershipSnapshotCache {

  /** 快照记录：租户列表 + 过期时刻。 */
  record MembershipSnapshot(List<MyTenant> tenants, Instant expiresAt) {}

  private final ConcurrentHashMap<String, MembershipSnapshot> store = new ConcurrentHashMap<>();
  private final Clock clock;
  private final Duration ttl;

  public MembershipSnapshotCache() {
    this(Clock.systemUTC(), Duration.ofMinutes(30));
  }

  /** 测试用构造器（可注入假时钟与短 TTL）。 */
  MembershipSnapshotCache(Clock clock, Duration ttl) {
    this.clock = clock;
    this.ttl = ttl;
  }

  /** 写入/覆盖某用户的租户快照。空参静默忽略。 */
  public void put(String userId, List<MyTenant> tenants) {
    if (userId == null || tenants == null) {
      return;
    }
    store.put(userId, new MembershipSnapshot(List.copyOf(tenants), clock.instant().plus(ttl)));
  }

  /** 读某用户的未过期快照；miss/过期返回 empty。 */
  public Optional<List<MyTenant>> get(String userId) {
    if (userId == null) {
      return Optional.empty();
    }
    MembershipSnapshot snap = store.get(userId);
    if (snap == null) {
      return Optional.empty();
    }
    if (clock.instant().isAfter(snap.expiresAt())) {
      store.remove(userId);
      return Optional.empty();
    }
    return Optional.of(snap.tenants());
  }

  /** 当前缓存条目数（监控/测试用）。 */
  public int size() {
    return store.size();
  }
}
