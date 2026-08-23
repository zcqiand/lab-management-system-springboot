package io.xr.lab.platform.mapper;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.xr.lab.platform.entity.CalculationMethodEntity;
import io.xr.lab.shared.dto.CalculationAlgorithmType;
import io.xr.lab.shared.dto.CalculationMethod;
import io.xr.lab.shared.dto.CreateCalculationMethodRequest;
import io.xr.lab.shared.dto.UpdateCalculationMethodRequest;

/** 计算方法（M06.F05）DTO ↔ Entity。 */
public final class CalculationMethodMapper {

  private CalculationMethodMapper() {}

  public static CalculationMethod toDto(CalculationMethodEntity e) {
    return new CalculationMethod()
        .inspectionObjectCode(e.getInspectionObjectCode())
        .inspectionParameterCode(e.getInspectionParameterCode())
        .testingStandardCode(e.getTestingStandardCode())
        .reportNameCode(e.getReportNameCode())
        .algorithmType(e.getAlgorithmType())
        .specimenCount(e.getSpecimenCount())
        .formula(e.getFormula())
        .conditions(e.getConditions())
        .roundingRule(e.getRoundingRule())
        .remark(e.getRemark())
        .sortOrder(e.getSortOrder())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt());
  }

  @SuppressFBWarnings(
      value = "RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE",
      justification =
          "CreateCalculationMethodRequest 的两个 getter 已被 @NonNull 标，"
              + "SpotBugs 看不到 lombok 生成代码所以误报。nullcheck 是防御性编程。")
  public static CalculationMethodEntity fromCreate(CreateCalculationMethodRequest req, String now) {
    String obj = req.getInspectionObjectCode();
    String param = req.getInspectionParameterCode();
    if (obj == null || param == null) {
      throw new IllegalArgumentException(
          "inspectionObjectCode and inspectionParameterCode are required");
    }
    CalculationMethodEntity e = new CalculationMethodEntity();
    e.setInspectionObjectCode(obj);
    e.setInspectionParameterCode(param);
    String standard = req.getTestingStandardCode();
    e.setTestingStandardCode(standard);
    String report = req.getReportNameCode();
    e.setReportNameCode(report);
    CalculationAlgorithmType algo = req.getAlgorithmType();
    e.setAlgorithmType(algo != null ? algo : CalculationAlgorithmType.MANUAL);
    Integer specimenCount = req.getSpecimenCount();
    e.setSpecimenCount(specimenCount != null ? specimenCount : 1);
    e.setFormula(req.getFormula());
    e.setConditions(req.getConditions());
    e.setRoundingRule(req.getRoundingRule());
    e.setRemark(req.getRemark());
    Integer sort = req.getSortOrder();
    e.setSortOrder(sort != null ? sort : 0);
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    return e;
  }

  public static void applyUpdate(
      CalculationMethodEntity e, UpdateCalculationMethodRequest req, String now) {
    if (req.getTestingStandardCode() != null) {
      e.setTestingStandardCode(req.getTestingStandardCode());
    }
    if (req.getReportNameCode() != null) {
      e.setReportNameCode(req.getReportNameCode());
    }
    if (req.getAlgorithmType() != null) {
      e.setAlgorithmType(req.getAlgorithmType());
    }
    if (req.getSpecimenCount() != null) {
      e.setSpecimenCount(req.getSpecimenCount());
    }
    if (req.getFormula() != null) {
      e.setFormula(req.getFormula());
    }
    if (req.getConditions() != null) {
      e.setConditions(req.getConditions());
    }
    if (req.getRoundingRule() != null) {
      e.setRoundingRule(req.getRoundingRule());
    }
    if (req.getRemark() != null) {
      e.setRemark(req.getRemark());
    }
    if (req.getSortOrder() != null) {
      e.setSortOrder(req.getSortOrder());
    }
    e.setUpdatedAt(now);
  }
}
