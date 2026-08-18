package io.xr.lab.platform.service;

import io.xr.lab.platform.mapper.SampleReceiptMapper;
import io.xr.lab.platform.repository.ContractRepository;
import io.xr.lab.platform.repository.SampleReceiptRepository;
import io.xr.lab.shared.dto.AssignTaskRequest;
import io.xr.lab.shared.dto.CreateSampleReceiptRequest;
import io.xr.lab.shared.dto.FlowHistoryEntry;
import io.xr.lab.shared.dto.FlowStatus;
import io.xr.lab.shared.dto.SampleReceipt;
import io.xr.lab.shared.dto.UpdateSampleReceiptRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.stereotype.Service;

/** M03.F01-F09 接样单。tenant-scoped + 7 阶段流程状态机。 */
@Service
public class SampleReceiptService {

  private final SampleReceiptRepository repo;
  private final ContractRepository contractRepo;

  public SampleReceiptService(SampleReceiptRepository repo, ContractRepository contractRepo) {
    this.repo = repo;
    this.contractRepo = contractRepo;
  }

  public List<SampleReceipt> list(
      String tenantId, String contractId, FlowStatus flowStatus, String keyword) {
    return repo.filter(tenantId, n(contractId), flowStatus, n(keyword)).stream()
        .map(SampleReceiptMapper::toDto)
        .toList();
  }

  /** M03.F05 审核队列：按 stage + limit 取。 */
  public List<SampleReceipt> flowQueue(String tenantId, FlowStatus stage, int limit) {
    return repo.findByTenantAndStage(tenantId, stage, limit).stream()
        .map(SampleReceiptMapper::toDto)
        .toList();
  }

  public SampleReceipt get(String tenantId, String id) {
    return SampleReceiptMapper.toDto(
        repo.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new NoSuchElementException("Receipt not found: " + id)));
  }

  /** M03.F01.I06 流程历史（jsonb → List<FlowHistoryEntry>）。 */
  public List<FlowHistoryEntry> history(String tenantId, String id) {
    var entity = getEntity(tenantId, id);
    return parseHistory(entity.getFlowHistory());
  }

  public SampleReceipt create(String tenantId, CreateSampleReceiptRequest req) {
    // contract_id 必须存在
    if (!contractRepo.findByTenantIdAndId(tenantId, req.getContractId()).isPresent()) {
      throw new NoSuchElementException("Contract not found: " + req.getContractId());
    }
    String now = nowIso();
    return SampleReceiptMapper.toDto(
        repo.save(SampleReceiptMapper.fromCreate(req, newId(), tenantId, now)));
  }

  public SampleReceipt update(String tenantId, String id, UpdateSampleReceiptRequest req) {
    var entity = getEntity(tenantId, id);
    SampleReceiptMapper.applyUpdate(entity, req, nowIso());
    return SampleReceiptMapper.toDto(repo.save(entity));
  }

  public void delete(String tenantId, String id) {
    repo.delete(getEntity(tenantId, id));
  }

  /** M03.F02 任务分配。 */
  public SampleReceipt assignTask(String tenantId, String id, AssignTaskRequest req) {
    var entity = getEntity(tenantId, id);
    if (req.getAssigneeId() != null) entity.setAssigneeId(req.getAssigneeId());
    if (req.getAssigneeName() != null) entity.setAssigneeName(req.getAssigneeName());
    if (req.getPlannedTestDate() != null) entity.setPlannedTestDate(req.getPlannedTestDate());
    if (entity.getFlowStatus() == FlowStatus.RECEIVING) {
      entity.setFlowStatus(FlowStatus.TASK_ASSIGNMENT);
      entity.setFlowHistory(
          SampleReceiptMapper.appendHistory(
              entity.getFlowHistory(),
              "submit",
              req.getAssigneeName(),
              FlowStatus.RECEIVING.getValue(),
              FlowStatus.TASK_ASSIGNMENT.getValue(),
              "M03.F02 任务分配"));
    }
    entity.setUpdatedAt(nowIso());
    return SampleReceiptMapper.toDto(repo.save(entity));
  }

  /** M03.F06 等阶段推进：把 receipt 推到 target stage 并写 history。 */
  public SampleReceipt transitionTo(
      String tenantId, String id, FlowStatus from, FlowStatus to, String operator, String reason) {
    var entity = getEntity(tenantId, id);
    if (entity.getFlowStatus() != from) {
      throw new IllegalStateException(
          "Receipt " + id + " not in expected stage " + from + " but " + entity.getFlowStatus());
    }
    entity.setFlowStatus(to);
    entity.setFlowHistory(
        SampleReceiptMapper.appendHistory(
            entity.getFlowHistory(), "submit", operator, from.getValue(), to.getValue(), reason));
    entity.setUpdatedAt(nowIso());
    return SampleReceiptMapper.toDto(repo.save(entity));
  }

  private io.xr.lab.platform.entity.SampleReceiptEntity getEntity(String tenantId, String id) {
    return repo.findByTenantIdAndId(tenantId, id)
        .orElseThrow(() -> new NoSuchElementException("Receipt not found: " + id));
  }

  /** 历史 jsonb 简化解析：用 Jackson ObjectMapper。 */
  private static List<FlowHistoryEntry> parseHistory(String json) {
    if (json == null || json.isBlank() || "[]".equals(json)) {
      return List.of();
    }
    try {
      var mapper = new com.fasterxml.jackson.databind.ObjectMapper();
      return mapper.readValue(
          json, new com.fasterxml.jackson.core.type.TypeReference<List<FlowHistoryEntry>>() {});
    } catch (java.io.IOException e) {
      return new ArrayList<>();
    }
  }

  private static String n(String s) {
    return s == null ? "" : s;
  }

  private static String nowIso() {
    return java.time.OffsetDateTime.now(java.time.ZoneOffset.UTC)
        .format(java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }

  private static String newId() {
    return "R-" + UUID.randomUUID().toString();
  }
}
