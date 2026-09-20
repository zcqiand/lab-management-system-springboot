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

  /**
   * 三态 filter（5.57 入契约，SSOT = lab-nextjs db-queries.ts:53-58）：
   *
   * <ul>
   *   <li>filter="not_yet"：停在 flowStatus 环节待提交；无 flowStatus 时 =
   *       jsonb_array_length(flow_history)=0（无流转记录新单）
   *   <li>filter="submitted"：已从本环节 submit 至下一环节（history 有 submit from 本环节且当前 flowStatus ≠ 本环节）；无
   *       flowStatus 时 = 有流转记录且 last_submitted_by 非空
   * </ul>
   *
   * <p>jsonb 谓词 JPQL 表达不了（jsonb_array_elements 是集合返回函数），走 native SQL；flowStatus 传 wire 值字符串（'' =
   * 不过滤），DB 列 V014 起 TEXT 化可直接比较。其余参数语义与 {@link #filter} 相同；filter 为其它值时调用方应走 {@link
   * #filter}（等同不传）。
   */
  @Query(
      value =
          "SELECT r.* FROM sample_receipts r"
              + " WHERE (:tenantId = '' OR r.tenant_id = :tenantId)"
              + " AND (:contractId = '' OR r.contract_id = :contractId)"
              + " AND (:keyword = '' OR LOWER(r.commission_code) LIKE LOWER(CONCAT('%', :keyword, '%'))"
              + "   OR LOWER(r.project_name) LIKE LOWER(CONCAT('%', :keyword, '%')))"
              + " AND ("
              + "   :filter = 'not_yet' AND ("
              + "     (:flowStatus <> '' AND r.flow_status = :flowStatus)"
              + "  OR (:flowStatus = '' AND jsonb_array_length(r.flow_history) = 0))"
              + "  OR"
              + "   :filter = 'submitted' AND ("
              + "     (:flowStatus <> '' AND r.flow_status <> :flowStatus"
              + "       AND EXISTS (SELECT 1 FROM jsonb_array_elements(r.flow_history) h"
              + "                   WHERE h ->> 'action' = 'submit' AND h ->> 'from' = :flowStatus))"
              + "  OR (:flowStatus = '' AND jsonb_array_length(r.flow_history) > 0"
              + "       AND r.last_submitted_by IS NOT NULL))"
              + " )"
              + " ORDER BY r.updated_at DESC, r.commission_code",
      nativeQuery = true)
  List<SampleReceiptEntity> filterThreeState(
      @Param("tenantId") String tenantId,
      @Param("contractId") String contractId,
      @Param("flowStatus") String flowStatus,
      @Param("keyword") String keyword,
      @Param("filter") String filter);

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
