package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.SampleReceiptEntity;
import io.xr.lab.shared.dto.FlowStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** V002 — 接样单（M03.F01/F02/F05-F09）。tenant-scoped，list 多过滤 + flow 队列。 */
public interface SampleReceiptRepository extends JpaRepository<SampleReceiptEntity, String> {

  @Query(
      "SELECT r FROM SampleReceiptEntity r"
          + " WHERE (:tenantId = '' OR r.tenantId = :tenantId)"
          + " AND (:contractId = '' OR r.contractId = :contractId)"
          + " AND (:flowStatus = '' OR r.flowStatus = :flowStatus)"
          + " AND (:keyword = '' OR LOWER(r.commissionCode) LIKE LOWER(CONCAT('%', :keyword, '%'))"
          + "   OR LOWER(r.projectName) LIKE LOWER(CONCAT('%', :keyword, '%')))"
          + " ORDER BY r.updatedAt DESC, r.commissionCode")
  List<SampleReceiptEntity> filter(
      @Param("tenantId") String tenantId,
      @Param("contractId") String contractId,
      @Param("flowStatus") FlowStatus flowStatus,
      @Param("keyword") String keyword);

  /** 审核/审批/发放等 stage 队列：按 flowStatus 过滤 + tenant。 */
  default List<SampleReceiptEntity> findByTenantAndStage(
      String tenantId, FlowStatus stage, int limit) {
    List<SampleReceiptEntity> all = filter(tenantId, "", stage, "");
    return all.size() > limit ? all.subList(0, limit) : all;
  }

  Optional<SampleReceiptEntity> findByTenantIdAndId(String tenantId, String id);
}
