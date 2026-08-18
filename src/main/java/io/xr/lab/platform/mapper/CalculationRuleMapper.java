package io.xr.lab.platform.mapper;

import io.xr.lab.platform.entity.CalculationRuleEntity;
import io.xr.lab.shared.dto.CalculationAlgorithmType;
import io.xr.lab.shared.dto.CalculationRule;
import io.xr.lab.shared.dto.CreateCalculationRuleRequest;
import io.xr.lab.shared.dto.UpdateCalculationRuleRequest;

/** 计算规则（M06.F05）DTO ↔ Entity。 */
public final class CalculationRuleMapper {

  private CalculationRuleMapper() {}

  public static CalculationRule toDto(CalculationRuleEntity e) {
    return new CalculationRule()
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

  public static CalculationRuleEntity fromCreate(CreateCalculationRuleRequest req, String now) {
    String obj = req.getInspectionObjectCode();
    String param = req.getInspectionParameterCode();
    if (obj == null || param == null) {
      throw new IllegalArgumentException(
          "inspectionObjectCode and inspectionParameterCode are required");
    }
    CalculationRuleEntity e = new CalculationRuleEntity();
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
      CalculationRuleEntity e, UpdateCalculationRuleRequest req, String now) {
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
