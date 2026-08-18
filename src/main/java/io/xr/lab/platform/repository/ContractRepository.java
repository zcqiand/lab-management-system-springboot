package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.ContractEntity;
import io.xr.lab.shared.dto.ContractStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** V001 — 合同管理（M02.F01）。tenant-scoped（V012）。 */
public interface ContractRepository extends JpaRepository<ContractEntity, String> {

  @Query(
      "SELECT c FROM ContractEntity c WHERE c.tenantId = :tenantId"
          + " AND (:keyword = '' OR LOWER(c.contractCode) LIKE LOWER(CONCAT('%', :keyword, '%'))"
          + "   OR LOWER(c.projectName) LIKE LOWER(CONCAT('%', :keyword, '%')))"
          + " AND (:status = '' OR c.status = :status)"
          + " ORDER BY c.updatedAt DESC, c.contractCode")
  List<ContractEntity> filter(
      @Param("tenantId") String tenantId,
      @Param("keyword") String keyword,
      @Param("status") ContractStatus status);

  Optional<ContractEntity> findByTenantIdAndId(String tenantId, String id);
}
