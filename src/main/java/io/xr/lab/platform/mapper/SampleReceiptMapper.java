package io.xr.lab.platform.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.xr.lab.platform.entity.SampleReceiptEntity;
import io.xr.lab.shared.dto.CreateSampleReceiptRequest;
import io.xr.lab.shared.dto.FlowHistoryEntry;
import io.xr.lab.shared.dto.FlowStatus;
import io.xr.lab.shared.dto.SampleReceipt;
import io.xr.lab.shared.dto.UpdateSampleReceiptRequest;
import java.util.ArrayList;
import java.util.List;

/** M03.F01 + F02 接样单 DTO ↔ Entity。 */
public final class SampleReceiptMapper {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private SampleReceiptMapper() {}

  public static SampleReceipt toDto(SampleReceiptEntity e) {
    return new SampleReceipt()
        .id(e.getId())
        .contractId(e.getContractId())
        .commissionCode(e.getCommissionCode())
        .commissionDate(e.getCommissionDate())
        .commissionRegisterCode(e.getCommissionRegisterCode())
        .commissionRegisterDate(e.getCommissionRegisterDate())
        .categoryCode(e.getCategoryCode())
        .projectName(e.getProjectName())
        .clientUnit(e.getClientUnit())
        .buildingUnit(e.getBuildingUnit())
        .supervisorUnit(e.getSupervisorUnit())
        .constructionUnit(e.getConstructionUnit())
        .witnessUnit(e.getWitnessUnit())
        .samplingLocation(e.getSamplingLocation())
        .witness(e.getWitness())
        .witnessPhone(e.getWitnessPhone())
        .inspector(e.getInspector())
        .inspectorPhone(e.getInspectorPhone())
        .receivedBy(e.getReceivedBy())
        .sampleSource(e.getSampleSource())
        .testCategory(e.getTestCategory())
        .testEnvironment(e.getTestEnvironment())
        .mainEquipment(e.getMainEquipment())
        .testOperator(e.getTestOperator())
        .testStartDate(e.getTestStartDate())
        .testEndDate(e.getTestEndDate())
        .originalRecordNo(e.getOriginalRecordNo())
        .remark(e.getRemark())
        .judgmentBasis(parseStringList(e.getJudgmentBasis()))
        .testingBasis(parseStringList(e.getTestingBasis()))
        .testParameters(parseStringList(e.getTestParameters()))
        .flowStatus(e.getFlowStatus())
        .flowHistory(parseHistory(e.getFlowHistory()))
        .lastSubmittedBy(e.getLastSubmittedBy())
        .assigneeId(e.getAssigneeId())
        .assigneeName(e.getAssigneeName())
        .plannedTestDate(e.getPlannedTestDate())
        .reportCode(e.getReportCode())
        .reportDate(e.getReportDate())
        .conclusion(e.getConclusion())
        .result(e.getResult())
        .issuedAt(e.getIssuedAt() == null ? null : e.getIssuedAt().toString())
        .tenantId(e.getTenantId())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt());
  }

  public static SampleReceiptEntity fromCreate(
      CreateSampleReceiptRequest req, String id, String tenantId, String now) {
    SampleReceiptEntity e = new SampleReceiptEntity();
    e.setId(id);
    e.setTenantId(tenantId);
    e.setContractId(req.getContractId());
    e.setCommissionCode(req.getCommissionCode());
    e.setCommissionDate(req.getCommissionDate());
    e.setCommissionRegisterCode(req.getCommissionRegisterCode());
    e.setCommissionRegisterDate(req.getCommissionRegisterDate());
    e.setCategoryCode(req.getCategoryCode());
    e.setProjectName(req.getProjectName());
    e.setClientUnit(req.getClientUnit());
    e.setBuildingUnit(req.getBuildingUnit());
    e.setSupervisorUnit(req.getSupervisorUnit());
    e.setConstructionUnit(req.getConstructionUnit());
    e.setWitnessUnit(req.getWitnessUnit());
    e.setSamplingLocation(req.getSamplingLocation());
    e.setWitness(req.getWitness());
    e.setWitnessPhone(req.getWitnessPhone());
    e.setInspector(req.getInspector());
    e.setInspectorPhone(req.getInspectorPhone());
    e.setReceivedBy(req.getReceivedBy());
    e.setSampleSource(req.getSampleSource());
    e.setTestCategory(req.getTestCategory());
    e.setTestEnvironment(req.getTestEnvironment());
    e.setMainEquipment(req.getMainEquipment());
    e.setTestOperator(req.getTestOperator());
    e.setTestStartDate(req.getTestStartDate());
    e.setTestEndDate(req.getTestEndDate());
    e.setOriginalRecordNo(req.getOriginalRecordNo());
    e.setRemark(req.getRemark());
    e.setJudgmentBasis(serializeStringList(req.getJudgmentBasis()));
    e.setTestingBasis(serializeStringList(req.getTestingBasis()));
    e.setTestParameters(serializeStringList(req.getTestParameters()));
    e.setFlowStatus(FlowStatus.RECEIVING);
    e.setFlowHistory("[]");
    e.setResult(io.xr.lab.shared.dto.ReceiptResult.EMPTY);
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    return e;
  }

  public static void applyUpdate(
      SampleReceiptEntity e, UpdateSampleReceiptRequest req, String now) {
    if (req.getCommissionCode() != null) e.setCommissionCode(req.getCommissionCode());
    if (req.getCommissionDate() != null) e.setCommissionDate(req.getCommissionDate());
    if (req.getCommissionRegisterCode() != null)
      e.setCommissionRegisterCode(req.getCommissionRegisterCode());
    if (req.getCommissionRegisterDate() != null)
      e.setCommissionRegisterDate(req.getCommissionRegisterDate());
    if (req.getCategoryCode() != null) e.setCategoryCode(req.getCategoryCode());
    if (req.getProjectName() != null) e.setProjectName(req.getProjectName());
    if (req.getClientUnit() != null) e.setClientUnit(req.getClientUnit());
    if (req.getBuildingUnit() != null) e.setBuildingUnit(req.getBuildingUnit());
    if (req.getSupervisorUnit() != null) e.setSupervisorUnit(req.getSupervisorUnit());
    if (req.getConstructionUnit() != null) e.setConstructionUnit(req.getConstructionUnit());
    if (req.getWitnessUnit() != null) e.setWitnessUnit(req.getWitnessUnit());
    if (req.getSamplingLocation() != null) e.setSamplingLocation(req.getSamplingLocation());
    if (req.getWitness() != null) e.setWitness(req.getWitness());
    if (req.getWitnessPhone() != null) e.setWitnessPhone(req.getWitnessPhone());
    if (req.getInspector() != null) e.setInspector(req.getInspector());
    if (req.getInspectorPhone() != null) e.setInspectorPhone(req.getInspectorPhone());
    if (req.getReceivedBy() != null) e.setReceivedBy(req.getReceivedBy());
    if (req.getSampleSource() != null) e.setSampleSource(req.getSampleSource());
    if (req.getTestCategory() != null) e.setTestCategory(req.getTestCategory());
    if (req.getTestEnvironment() != null) e.setTestEnvironment(req.getTestEnvironment());
    if (req.getMainEquipment() != null) e.setMainEquipment(req.getMainEquipment());
    if (req.getTestOperator() != null) e.setTestOperator(req.getTestOperator());
    if (req.getTestStartDate() != null) e.setTestStartDate(req.getTestStartDate());
    if (req.getTestEndDate() != null) e.setTestEndDate(req.getTestEndDate());
    if (req.getOriginalRecordNo() != null) e.setOriginalRecordNo(req.getOriginalRecordNo());
    if (req.getRemark() != null) e.setRemark(req.getRemark());
    if (req.getJudgmentBasis() != null)
      e.setJudgmentBasis(serializeStringList(req.getJudgmentBasis()));
    if (req.getTestingBasis() != null)
      e.setTestingBasis(serializeStringList(req.getTestingBasis()));
    if (req.getTestParameters() != null)
      e.setTestParameters(serializeStringList(req.getTestParameters()));
    e.setUpdatedAt(now);
  }

  /** flow_status 切换后追加 flow_history 一条（JSON 数组末端 push）。 */
  public static String appendHistory(
      String currentJson,
      String action,
      String operator,
      String fromStage,
      String toStage,
      String reason) {
    String entry =
        String.format(
            "{\"action\":\"%s\",\"from\":\"%s\",\"to\":\"%s\",\"operator\":\"%s\",\"at\":\"%s\",\"reason\":\"%s\"}",
            js(action), js(fromStage), js(toStage), js(operator), js(nowIso()), js(reason));
    if (currentJson == null || currentJson.isBlank() || "[]".equals(currentJson)) {
      return "[" + entry + "]";
    }
    // 去掉末尾 ']'，加 ',' + entry + ']'
    return currentJson.substring(0, currentJson.length() - 1) + "," + entry + "]";
  }

  // === helpers ===

  private static List<String> parseStringList(String json) {
    if (json == null || json.isBlank()) return new ArrayList<>();
    try {
      return MAPPER.readValue(json, new com.fasterxml.jackson.core.type.TypeReference<>() {});
    } catch (java.io.IOException e) {
      return new ArrayList<>();
    }
  }

  private static String serializeStringList(List<String> list) {
    if (list == null || list.isEmpty()) return "[]";
    try {
      return MAPPER.writeValueAsString(list);
    } catch (java.io.IOException e) {
      return "[]";
    }
  }

  private static List<FlowHistoryEntry> parseHistory(String json) {
    if (json == null || json.isBlank() || "[]".equals(json)) return new ArrayList<>();
    try {
      return MAPPER.readValue(
          json, new com.fasterxml.jackson.core.type.TypeReference<List<FlowHistoryEntry>>() {});
    } catch (java.io.IOException e) {
      return new ArrayList<>();
    }
  }

  private static String js(String s) {
    return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"");
  }

  private static String nowIso() {
    return java.time.OffsetDateTime.now(java.time.ZoneOffset.UTC)
        .format(java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }
}
