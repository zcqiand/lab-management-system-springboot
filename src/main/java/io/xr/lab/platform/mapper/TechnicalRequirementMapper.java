package io.xr.lab.platform.mapper;

import io.xr.lab.platform.entity.TechnicalRequirementEntity;
import io.xr.lab.shared.dto.CreateTechnicalRequirementRequest;
import io.xr.lab.shared.dto.RequirementComparison;
import io.xr.lab.shared.dto.RequirementJudgmentMode;
import io.xr.lab.shared.dto.RequirementValueType;
import io.xr.lab.shared.dto.RequirementVerificationStatus;
import io.xr.lab.shared.dto.TechnicalRequirement;
import io.xr.lab.shared.dto.UpdateTechnicalRequirementRequest;

/** 技术要求（M06.F06）DTO ↔ Entity。 */
public final class TechnicalRequirementMapper {

  private TechnicalRequirementMapper() {}

  public static TechnicalRequirement toDto(TechnicalRequirementEntity e) {
    return new TechnicalRequirement()
        .tenantId(e.getTenantId())
        .inspectionObjectCode(e.getInspectionObjectCode())
        .inspectionParameterCode(e.getInspectionParameterCode())
        .judgmentStandardCode(e.getJudgmentStandardCode())
        .conditions(e.getConditions())
        .valueType(e.getValueType())
        .minValue(e.getMinValue())
        .maxValue(e.getMaxValue())
        .targetValue(e.getTargetValue())
        .expression(e.getExpression())
        .unit(e.getUnit())
        .comparison(e.getComparison())
        .judgmentMode(e.getJudgmentMode())
        .verificationStatus(e.getVerificationStatus())
        .clause(e.getClause())
        .sourcePage(e.getSourcePage())
        .sourceHash(e.getSourceHash())
        .brand(e.getBrand())
        .model(e.getModel())
        .grade(e.getGrade())
        .spec(e.getSpec())
        .sieve(e.getSieve())
        .remark(e.getRemark())
        .sortOrder(e.getSortOrder())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt());
  }

  public static TechnicalRequirementEntity fromCreate(
      CreateTechnicalRequirementRequest req, String tenantId, String now) {
    String obj = req.getInspectionObjectCode();
    String param = req.getInspectionParameterCode();
    String standard = req.getJudgmentStandardCode();
    if (obj == null || param == null || standard == null) {
      throw new IllegalArgumentException(
          "inspectionObjectCode/inspectionParameterCode/judgmentStandardCode are required");
    }
    TechnicalRequirementEntity e = new TechnicalRequirementEntity();
    e.setTenantId(tenantId);
    e.setInspectionObjectCode(obj);
    e.setInspectionParameterCode(param);
    e.setJudgmentStandardCode(standard);
    e.setConditions(req.getConditions());
    RequirementValueType valueType = req.getValueType();
    e.setValueType(valueType != null ? valueType : RequirementValueType.NUMERIC);
    e.setMinValue(req.getMinValue());
    e.setMaxValue(req.getMaxValue());
    e.setTargetValue(req.getTargetValue());
    e.setExpression(req.getExpression());
    e.setUnit(req.getUnit());
    RequirementComparison comparison = req.getComparison();
    e.setComparison(comparison != null ? comparison : RequirementComparison.u);
    RequirementJudgmentMode mode = req.getJudgmentMode();
    e.setJudgmentMode(mode != null ? mode : RequirementJudgmentMode.MANUAL);
    RequirementVerificationStatus status = req.getVerificationStatus();
    e.setVerificationStatus(status != null ? status : RequirementVerificationStatus.DRAFT);
    e.setClause(req.getClause());
    Integer sourcePage = req.getSourcePage();
    e.setSourcePage(sourcePage);
    String sourceHash = req.getSourceHash();
    e.setSourceHash(sourceHash);
    e.setBrand(req.getBrand());
    e.setModel(req.getModel());
    e.setGrade(req.getGrade());
    e.setSpec(req.getSpec());
    e.setSieve(req.getSieve());
    e.setRemark(req.getRemark());
    Integer sort = req.getSortOrder();
    e.setSortOrder(sort != null ? sort : 0);
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    return e;
  }

  public static void applyUpdate(
      TechnicalRequirementEntity e, UpdateTechnicalRequirementRequest req, String now) {
    if (req.getConditions() != null) e.setConditions(req.getConditions());
    if (req.getValueType() != null) e.setValueType(req.getValueType());
    if (req.getMinValue() != null) e.setMinValue(req.getMinValue());
    if (req.getMaxValue() != null) e.setMaxValue(req.getMaxValue());
    if (req.getTargetValue() != null) e.setTargetValue(req.getTargetValue());
    if (req.getExpression() != null) e.setExpression(req.getExpression());
    if (req.getUnit() != null) e.setUnit(req.getUnit());
    if (req.getComparison() != null) e.setComparison(req.getComparison());
    if (req.getJudgmentMode() != null) e.setJudgmentMode(req.getJudgmentMode());
    if (req.getVerificationStatus() != null) e.setVerificationStatus(req.getVerificationStatus());
    if (req.getClause() != null) e.setClause(req.getClause());
    if (req.getSourcePage() != null) e.setSourcePage(req.getSourcePage());
    if (req.getSourceHash() != null) e.setSourceHash(req.getSourceHash());
    if (req.getBrand() != null) e.setBrand(req.getBrand());
    if (req.getModel() != null) e.setModel(req.getModel());
    if (req.getGrade() != null) e.setGrade(req.getGrade());
    if (req.getSpec() != null) e.setSpec(req.getSpec());
    if (req.getSieve() != null) e.setSieve(req.getSieve());
    if (req.getRemark() != null) e.setRemark(req.getRemark());
    if (req.getSortOrder() != null) e.setSortOrder(req.getSortOrder());
    e.setUpdatedAt(now);
  }
}
