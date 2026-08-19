package io.xr.lab.platform.directory;

import io.xr.lab.shared.dto.CurrentUser;
import io.xr.lab.shared.dto.MyTenant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置式 demo 目录（B1）。
 *
 * <p>数据 1:1 镜像 lab-msw：
 *
 * <ul>
 *   <li>用户：admin@lab.local / dev123456（USER-A，roleCode=admin）— ADR-0008 后主键从 username 改为 email，SSO
 *       路径用 saas {@code CurrentUser.email} 回查
 *   <li>租户：TENANT-001 city-lab / TENANT-002 district-lab / TENANT-003 third-party
 *   <li>运行时 upsert：不在 seed 里的 SSO 用户落到 {@code upserted} 内存 Map
 * </ul>
 *
 * <p>口令可用 lab.auth.dev-password 覆盖（避免硬编码扩散到测试）。
 */
@Component
public class ConfigUserDirectory implements UserDirectory {

  private static final CurrentUser DEMO_USER =
      new CurrentUser()
          .id("USER-A")
          .username("admin@lab.local")
          .displayName("管理员")
          .roleCode("admin");

  private static final List<MyTenant> TENANTS =
      List.of(
          new MyTenant()
              .tenantId("TENANT-001")
              .code("city-lab")
              .name("市住建工程质量检测中心")
              .roleIds(List.of("admin")),
          new MyTenant()
              .tenantId("TENANT-002")
              .code("district-lab")
              .name("区检测站")
              .roleIds(List.of("technician")),
          new MyTenant()
              .tenantId("TENANT-003")
              .code("third-party")
              .name("第三方检测实验室")
              .roleIds(List.of("viewer")));

  private final String devPassword;
  private final ConcurrentHashMap<String, CurrentUser> upserted = new ConcurrentHashMap<>();

  public ConfigUserDirectory(@Value("${lab.auth.dev-password:dev123456}") String devPassword) {
    this.devPassword = devPassword;
  }

  @Override
  public Optional<CurrentUser> findByUsername(String username) {
    if (username == null) {
      return Optional.empty();
    }
    if (DEMO_USER.getUsername().equals(username)) {
      return Optional.of(DEMO_USER);
    }
    return upserted.values().stream().filter(u -> username.equals(u.getUsername())).findFirst();
  }

  @Override
  public Optional<CurrentUser> findByEmail(String email) {
    if (email == null) {
      return Optional.empty();
    }
    if (email.equals(DEMO_USER.getUsername())) {
      return Optional.of(DEMO_USER);
    }
    return upserted.values().stream().filter(u -> email.equals(u.getUsername())).findFirst();
  }

  @Override
  public Optional<CurrentUser> findById(String id) {
    if (id == null) {
      return Optional.empty();
    }
    if (id.equals(DEMO_USER.getId())) {
      return Optional.of(DEMO_USER);
    }
    return upserted.values().stream().filter(u -> id.equals(u.getId())).findFirst();
  }

  @Override
  public boolean checkPassword(String username, String password) {
    return DEMO_USER.getUsername().equals(username) && devPassword.equals(password);
  }

  @Override
  public List<MyTenant> tenantsOf(String username) {
    return TENANTS;
  }

  @Override
  public MyTenant defaultTenant() {
    return TENANTS.get(0);
  }

  @Override
  public Optional<MyTenant> findByTenantId(String tenantId) {
    return TENANTS.stream().filter(t -> t.getTenantId().equals(tenantId)).findFirst();
  }

  @Override
  public CurrentUser upsert(String id, String email, String displayName, String roleCode) {
    // 优先按 email 找，找到则更新 displayName/roleCode（id 与 saas 可能变化）
    CurrentUser existing = upserted.get(email);
    if (existing != null) {
      return existing;
    }
    CurrentUser user =
        new CurrentUser()
            .id(id)
            .username(email)
            .displayName(displayName)
            .roleCode(roleCode == null || roleCode.isEmpty() ? "viewer" : roleCode);
    upserted.put(email, user);
    return user;
  }
}
