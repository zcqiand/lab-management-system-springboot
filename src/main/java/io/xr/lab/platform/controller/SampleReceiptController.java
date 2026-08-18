package io.xr.lab.platform.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.xr.lab.platform.directory.ConfigUserDirectory;
import io.xr.lab.platform.service.SampleReceiptService;
import io.xr.lab.shared.api.ReceiptsApi;
import io.xr.lab.shared.dto.AssignTaskRequest;
import io.xr.lab.shared.dto.CreateSampleReceiptRequest;
import io.xr.lab.shared.dto.FlowHistoryEntry;
import io.xr.lab.shared.dto.FlowStatus;
import io.xr.lab.shared.dto.ReceiptsListReceipts200Response;
import io.xr.lab.shared.dto.SampleReceipt;
import io.xr.lab.shared.dto.UpdateSampleReceiptRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** M03.F01/F02/F05-M03.F09 接样单（B3，7 端点，task + history 拆出为 M03.F01.I06 + M03.F02.I01）。 */
@RestController
public class SampleReceiptController implements ReceiptsApi {

  private final SampleReceiptService service;
  private final ConfigUserDirectory directory;

  @SuppressFBWarnings(
      value = "EI_EXPOSE_REP2",
      justification = "Spring DI singleton: 控制器按规范持有 service / directory 的共享 bean 引用。")
  public SampleReceiptController(SampleReceiptService service, ConfigUserDirectory directory) {
    this.service = service;
    this.directory = directory;
  }

  @Override
  public ResponseEntity<ReceiptsListReceipts200Response> receiptsListReceipts(
      Integer page, Integer pageSize, String keyword, String contractId, FlowStatus flowStatus) {
    String tenant = InspectionCatalogController.currentTenantIdOrDefaultStatic(directory);
    List<SampleReceipt> list = service.list(tenant, contractId, flowStatus, keyword);
    return ResponseEntity.ok(new ReceiptsListReceipts200Response().items(list));
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
}
