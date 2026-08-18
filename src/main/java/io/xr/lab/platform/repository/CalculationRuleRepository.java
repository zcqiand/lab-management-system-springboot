package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.CalculationRuleEntity;
import io.xr.lab.platform.entity.CalculationRuleKey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** V009 — 计算规则（M06.F05）。平台级字典（无 tenant_id），list 按 object/parameter 双过滤；get 按复合主键。 */
public interface CalculationRuleRepository
    extends JpaRepository<CalculationRuleEntity, CalculationRuleKey> {

  @Query(
      "SELECT r FROM CalculationRuleEntity r"
          + " WHERE (:objectCode IS NULL OR r.inspectionObjectCode = :objectCode)"
          + " AND (:parameterCode IS NULL OR r.inspectionParameterCode = :parameterCode)"
          + " ORDER BY r.sortOrder, r.inspectionObjectCode, r.inspectionParameterCode")
  List<CalculationRuleEntity> filter(
      @Param("objectCode") String inspectionObjectCode,
      @Param("parameterCode") String inspectionParameterCode);
}
