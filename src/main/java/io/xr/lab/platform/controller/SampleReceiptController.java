package io.xr.lab.platform.controller;

import io.xr.lab.platform.directory.ConfigUserDirectory;
import io.xr.lab.platform.service.ReportFlowService;
import io.xr.lab.platform.service.SampleReceiptService;
import io.xr.lab.shared.api.ReceiptsApi;
import io.xr.lab.shared.dto.AssignTaskRequest;
import io.xr.lab.shared.dto.CreateSampleReceiptRequest;
import io.xr.lab.shared.dto.FlowActionRequest;
import io.xr.lab.shared.dto.FlowActionResult;
import io.xr.lab.shared.dto.FlowHistoryEntry;
import io.xr.lab.shared.dto.FlowStatus;
import io.xr.lab.shared.dto.ReceiptsListReceipts200Response;
import io.xr.lab.shared.dto.SampleReceipt;
import io.xr.lab.shared.dto.UpdateSampleReceiptRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * M03.F01/F02/F03/F05/F06/F07/F08 接样单（B3）。
 *
 * <p>2026-09-17 重整（lab-shared commit 13122e9）： 原 ReportFlowApi 21 op + 4 list*queue + 2 batch 全部收敛为
 * 7 个 act op（POST /receipts/{stage}/act with body.action={SUBMIT|RETURN|WITHDRAW}），act op 在
 * receipts namespace 下生成，故本 controller 一并实现。原 ReportFlowController 已删。
 */
@RestController
public class SampleReceiptController implements ReceiptsApi {

  private final SampleReceiptService service;
  private final ReportFlowService flowService;
  private final ConfigUserDirectory directory;

  public SampleReceiptController(
      SampleReceiptService service, ReportFlowService flowService, ConfigUserDirectory directory) {
    this.service = service;
    this.flowService = flowService;
    this.directory = directory;
  }

  @Override
  public ResponseEntity<ReceiptsListReceipts200Response> receiptsListReceipts(
      Integer page, Integer pageSize, String keyword, String contractId, FlowStatus flowStatus) {
    String tenant = InspectionCatalogController.currentTenantIdOrDefaultStatic(directory);
    List<SampleReceipt> list = service.list(tenant, contractId, flowStatus, keyword);
    // 2026-09-16 T11 live 实证：list envelope 缺省 page=1 / pageSize=20 对齐 nextjs oracle。
    return ResponseEntity.ok(
        new ReceiptsListReceipts200Response()
            .items(list)
            .page(page == null ? Integer.valueOf(1) : page)
            .pageSize(pageSize == null ? Integer.valueOf(20) : pageSize)
            .total((long) list.size()));
  }

  @Override
  public ResponseEntity<SampleReceipt> receiptsGetReceipt(String id) {
    return ResponseEntity.ok(
        service.get(InspectionCatalogController.currentTenantIdOrDefaultStatic(directory), id));
  }

  @Override
  public ResponseEntity<SampleReceipt> receiptsCreateReceipt(
      CreateSampleReceiptRequest createSampleReceiptRequest) {
    return ResponseEntity.ok(
        service.create(
            InspectionCatalogController.currentTenantIdOrDefaultStatic(directory),
            createSampleReceiptRequest));
  }

  @Override
  public ResponseEntity<SampleReceipt> receiptsUpdateReceipt(
      String id, UpdateSampleReceiptRequest updateSampleReceiptRequest) {
    return ResponseEntity.ok(
        service.update(
            InspectionCatalogController.currentTenantIdOrDefaultStatic(directory),
            id,
            updateSampleReceiptRequest));
  }

  @Override
  public ResponseEntity<Void> receiptsDeleteReceipt(String id) {
    service.delete(InspectionCatalogController.currentTenantIdOrDefaultStatic(directory), id);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<List<FlowHistoryEntry>> receiptsGetReceiptHistory(String id) {
    return ResponseEntity.ok(
        service.history(InspectionCatalogController.currentTenantIdOrDefaultStatic(directory), id));
  }

  @Override
  public ResponseEntity<SampleReceipt> receiptsAssignTask(
      String id, AssignTaskRequest assignTaskRequest) {
    return ResponseEntity.ok(
        service.assignTask(
            InspectionCatalogController.currentTenantIdOrDefaultStatic(directory),
            id,
            assignTaskRequest));
  }

  // ============================================================
  // 7 个 act 端点（M03.F01-F08 流程动作全 act 模式，2026-09-17 收敛）
  // ============================================================

  /** M03.F01.I08/I09/I10 接样阶段 act。 */
  @Override
  public ResponseEntity<List<FlowActionResult>> receiptsActFlowReceiving(
      FlowActionRequest flowActionRequest) {
    String tenant = InspectionCatalogController.currentTenantIdOrDefaultStatic(directory);
    return ResponseEntity.ok(flowService.actReceiving(tenant, flowActionRequest));
  }

  /** M03.F02.I05/I06/I07 任务分配阶段 act。 */
  @Override
  public ResponseEntity<List<FlowActionResult>> receiptsActFlowAssigning(
      FlowActionRequest flowActionRequest) {
    String tenant = InspectionCatalogController.currentTenantIdOrDefaultStatic(directory);
    return ResponseEntity.ok(flowService.actAssigning(tenant, flowActionRequest));
  }

  /** M03.F03.I12/I13/I14 数据录入阶段 act。 */
  @Override
  public ResponseEntity<List<FlowActionResult>> receiptsActFlowDataEntry(
      FlowActionRequest flowActionRequest) {
    String tenant = InspectionCatalogController.currentTenantIdOrDefaultStatic(directory);
    return ResponseEntity.ok(flowService.actDataEntry(tenant, flowActionRequest));
  }

  /** M03.F05.I07/I08/I09 报告审核阶段 act。 */
  @Override
  public ResponseEntity<List<FlowActionResult>> receiptsActFlowReview(
      FlowActionRequest flowActionRequest) {
    String tenant = InspectionCatalogController.currentTenantIdOrDefaultStatic(directory);
    return ResponseEntity.ok(flowService.actReview(tenant, flowActionRequest));
  }

  /** M03.F06.I05/I06/I07 报告批准阶段 act。 */
  @Override
  public ResponseEntity<List<FlowActionResult>> receiptsActFlowApprove(
      FlowActionRequest flowActionRequest) {
    String tenant = InspectionCatalogController.currentTenantIdOrDefaultStatic(directory);
    return ResponseEntity.ok(flowService.actApprove(tenant, flowActionRequest));
  }

  /** M03.F07.I05/I06/I07 报告发放阶段 act。 */
  @Override
  public ResponseEntity<List<FlowActionResult>> receiptsActFlowIssuance(
      FlowActionRequest flowActionRequest) {
    String tenant = InspectionCatalogController.currentTenantIdOrDefaultStatic(directory);
    return ResponseEntity.ok(flowService.actIssuance(tenant, flowActionRequest));
  }

  /** M03.F08.I05/I06/I07 报告归档阶段 act（仅 SUBMIT）。 */
  @Override
  public ResponseEntity<List<FlowActionResult>> receiptsActFlowArchived(
      FlowActionRequest flowActionRequest) {
    String tenant = InspectionCatalogController.currentTenantIdOrDefaultStatic(directory);
    return ResponseEntity.ok(flowService.actArchived(tenant, flowActionRequest));
  }
}
