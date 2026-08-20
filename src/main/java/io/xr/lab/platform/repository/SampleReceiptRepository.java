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

  // flowStatus 为 enum、可为 null：`null = ''` 是 UNKNOWN 非 TRUE，会折叠整个 WHERE →
  // 列表恒空（同 ContractRepository 踩坑，用 IS NULL 判空）。
  @Query(
      "SELECT r FROM SampleReceiptEntity r"
          + " WHERE (:tenantId = '' OR r.tenantId = :tenantId)"
          + " AND (:contractId = '' OR r.contractId = :contractId)"
          + " AND (:flowStatus IS NULL OR r.flowStatus = :flowStatus)"
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

  /**
   * 报告汇总查询（B4 M05.F01）。tenant + 可选 categoryCode（ALL 字符串 = 不过滤） + 可选 commissionDate 前后缀（YYYY-MM-DD
   * 字典序 = 日期序）。无界传空串。
   */
  @Query(
      "SELECT r FROM SampleReceiptEntity r"
          + " WHERE (:tenantId = '' OR r.tenantId = :tenantId)"
          + " AND (:categoryCode = 'ALL' OR r.categoryCode = :categoryCode)"
          + " AND (:dateFrom = '' OR r.commissionDate >= :dateFrom)"
          + " AND (:dateTo = '' OR r.commissionDate <= :dateTo)"
          + " ORDER BY r.commissionDate DESC, r.commissionCode")
  List<SampleReceiptEntity> summary(
      @Param("tenantId") String tenantId,
      @Param("categoryCode") String categoryCode,
      @Param("dateFrom") String dateFrom,
      @Param("dateTo") String dateTo);

  Optional<SampleReceiptEntity> findByTenantIdAndId(String tenantId, String id);
}
