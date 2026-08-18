package io.xr.lab.platform.entity;

import io.xr.lab.platform.entity.enums.CalculationAlgorithmTypeConverter;
import io.xr.lab.shared.dto.CalculationAlgorithmType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

/**
 * V009__init_report_names.sql — 计算规则（M06.F05）。平台级字典（无 tenant_id）。PK = (inspection_object_code,
 * inspection_parameter_code)。
 */
@Entity
@Table(name = "inspection_calculation_rules")
@IdClass(CalculationRuleKey.class)
public class CalculationRuleEntity {

  @Id
  @Column(name = "inspection_object_code", length = 64, nullable = false)
  private String inspectionObjectCode;

  @Id
  @Column(name = "inspection_parameter_code", length = 64, nullable = false)
  private String inspectionParameterCode;

  @Column(name = "testing_standard_code", length = 64)
  private String testingStandardCode;

  @Column(name = "report_name_code", length = 64)
  private String reportNameCode;

  /**
   * calculation_algorithm_type PG enum。Hibernate 6 NAMED_ENUM + EnumType.STRING 会传 enum
   * 常量名（SIMPLE_AVG），与 PG enum 小写标签不一致；改用 AttributeConverter 写 DTO @JsonValue 同款小写字符串（PG 会把 text
   * 自动强转 enum）。
   */
  @Convert(converter = CalculationAlgorithmTypeConverter.class)
  @Column(name = "algorithm_type", nullable = false)
  private CalculationAlgorithmType algorithmType;

  @Column(name = "specimen_count", nullable = false)
  private Integer specimenCount = 1;

  @Column(name = "formula")
  private String formula;

  @Column(name = "conditions")
  private String conditions;

  @Column(name = "rounding_rule")
  private String roundingRule;

  @Column(name = "remark")
  private String remark;

  @Column(name = "sort_order", nullable = false)
  private Integer sortOrder = 0;

  @Column(name = "created_at", nullable = false)
  private String createdAt = "";

  @Column(name = "updated_at", nullable = false)
  private String updatedAt = "";

  public String getInspectionObjectCode() {
    return inspectionObjectCode;
  }

  public void setInspectionObjectCode(String v) {
    this.inspectionObjectCode = v;
  }

  public String getInspectionParameterCode() {
    return inspectionParameterCode;
  }

  public void setInspectionParameterCode(String v) {
    this.inspectionParameterCode = v;
  }

  public String getTestingStandardCode() {
    return testingStandardCode;
  }

  public void setTestingStandardCode(String v) {
    this.testingStandardCode = v;
  }

  public String getReportNameCode() {
    return reportNameCode;
  }

  public void setReportNameCode(String v) {
    this.reportNameCode = v;
  }

  public CalculationAlgorithmType getAlgorithmType() {
    return algorithmType;
  }

  public void setAlgorithmType(CalculationAlgorithmType v) {
    this.algorithmType = v;
  }

  public Integer getSpecimenCount() {
    return specimenCount;
  }

  public void setSpecimenCount(Integer v) {
    this.specimenCount = v;
  }

  public String getFormula() {
    return formula;
  }

  public void setFormula(String v) {
    this.formula = v;
  }

  public String getConditions() {
    return conditions;
  }

  public void setConditions(String v) {
    this.conditions = v;
  }

  public String getRoundingRule() {
    return roundingRule;
  }

  public void setRoundingRule(String v) {
    this.roundingRule = v;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String v) {
    this.remark = v;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer v) {
    this.sortOrder = v;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String v) {
    this.createdAt = v;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String v) {
    this.updatedAt = v;
  }
}
