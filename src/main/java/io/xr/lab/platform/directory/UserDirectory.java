package io.xr.lab.platform.directory;

import io.xr.lab.shared.dto.CurrentUser;
import io.xr.lab.shared.dto.MyTenant;
import java.util.List;
import java.util.Optional;

/**
 * 用户/租户目录（B1 dev 实现见 {@link ConfigUserDirectory}）。
 *
 * <p>lab_dev 只有业务表、无身份表（shared SQL SSOT 不含 users/tenants）。认证域先走配置式目录， 数据镜像 lab-msw
 * seeds（tenants.json + DEMO_USER）。将来落 V014 identity 表后换 DB 实现， 本接口不变。
 */
public interface UserDirectory {

  /** 按 username 找用户；dev 目录只有一个 demo 用户。 */
  Optional<CurrentUser> findByUsername(String username);

  /** 校验口令。命中返回 true。 */
  boolean checkPassword(String username, String password);

  /** 用户关联的租户列表（demo 用户关联全部 3 个）。 */
  List<MyTenant> tenantsOf(String username);

  /** 默认租户（token 无 tenant_id claim 时的 currentTenantId）。 */
  MyTenant defaultTenant();

  /** 按 tenantId 精确查找。 */
  Optional<MyTenant> findByTenantId(String tenantId);
}
