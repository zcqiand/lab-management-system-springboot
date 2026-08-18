package io.xr.lab.platform.directory;

import io.xr.lab.shared.dto.CurrentUser;
import io.xr.lab.shared.dto.MyTenant;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置式 demo 目录（B1）。数据 1:1 镜像 lab-msw：
 *
 * <ul>
 *   <li>用户：admin / dev123456（USER-A，roleCode=admin）
 *   <li>租户：TENANT-001 city-lab / TENANT-002 district-lab / TENANT-003 third-party
 * </ul>
 *
 * <p>口令可用 lab.auth.dev-password 覆盖（避免硬编码扩散到测试）。
 */
@Component
public class ConfigUserDirectory implements UserDirectory {

  private static final CurrentUser DEMO_USER =
      new CurrentUser().id("USER-A").username("admin").displayName("管理员").roleCode("admin");

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

  public ConfigUserDirectory(@Value("${lab.auth.dev-password:dev123456}") String devPassword) {
    this.devPassword = devPassword;
  }

  @Override
  public Optional<CurrentUser> findByUsername(String username) {
    return DEMO_USER.getUsername().equals(username) ? Optional.of(DEMO_USER) : Optional.empty();
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
}
