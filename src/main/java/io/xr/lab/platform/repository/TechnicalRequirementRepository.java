package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.TechnicalRequirementEntity;
import io.xr.lab.platform.entity.TechnicalRequirementKey;
import io.xr.lab.shared.dto.RequirementVerificationStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * V005 — 技术要求（M06.F06）。tenant-scoped（V012）；list 支持 4 过滤（object/parameter/standard/status）。
 *
 * <p>PK 是业务三键 (object, parameter, judgmentStandard)；tenantId 走 WHERE 过滤（与 V012 约束一致）。
 */
public interface TechnicalRequirementRepository
    extends JpaRepository<TechnicalRequirementEntity, TechnicalRequirementKey> {

  @Query(
      "SELECT t FROM TechnicalRequirementEntity t"
          + " WHERE (:tenantId IS NULL OR t.tenantId = :tenantId)"
          + " AND (:objectCode IS NULL OR t.inspectionObjectCode = :objectCode)"
          + " AND (:parameterCode IS NULL OR t.inspectionParameterCode = :parameterCode)"
          + " AND (:standardCode IS NULL OR t.judgmentStandardCode = :standardCode)"
          + " AND (:status IS NULL OR t.verificationStatus = :status)"
          + " ORDER BY t.sortOrder, t.inspectionObjectCode,"
          + " t.inspectionParameterCode, t.judgmentStandardCode")
  List<TechnicalRequirementEntity> filter(
      @Param("tenantId") String tenantId,
      @Param("objectCode") String inspectionObjectCode,
      @Param("parameterCode") String inspectionParameterCode,
      @Param("standardCode") String judgmentStandardCode,
      @Param("status") RequirementVerificationStatus status);
}
