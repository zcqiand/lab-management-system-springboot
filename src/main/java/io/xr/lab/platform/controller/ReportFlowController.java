package io.xr.lab.platform.controller;

import io.xr.lab.platform.directory.ConfigUserDirectory;
import io.xr.lab.platform.service.ReportFlowService;
import io.xr.lab.shared.api.ReportFlowApi;
import io.xr.lab.shared.dto.FlowActionRequest;
import io.xr.lab.shared.dto.FlowActionResult;
import io.xr.lab.shared.dto.FlowStatus;
import io.xr.lab.shared.dto.ReceiptsListReceipts200Response;
import io.xr.lab.shared.dto.SampleReceipt;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** M03.F05 队列 + M03.F06 推进（B3，2 端点）。 */
@RestController
public class ReportFlowController implements ReportFlowApi {

  private final ReportFlowService service;
  private final ConfigUserDirectory directory;

  public ReportFlowController(ReportFlowService service, ConfigUserDirectory directory) {
    this.service = service;
    this.directory = directory;
  }

  @Override
  public ResponseEntity<ReceiptsListReceipts200Response> reportFlowListFlowQueue(
      FlowStatus stage, Integer page, Integer pageSize) {
    String tenant = InspectionCatalogController.currentTenantIdOrDefaultStatic(directory);
    int cap = (pageSize == null || pageSize <= 0) ? 50 : Math.min(pageSize, 200);
    List<SampleReceipt> items = service.flowQueue(tenant, stage).stream().limit(cap).toList();
    // 2026-09-16 T11 live 实证：envelope 缺省 page=1 / pageSize=20 对齐 nextjs oracle
    //（cap 只影响 items 截断，pageSize 字段缺省仍是家族约定的 20）。
    return ResponseEntity.ok(
        new ReceiptsListReceipts200Response()
            .items(items)
            .page(page == null ? 1 : page)
            .pageSize(pageSize == null ? 20 : pageSize)
            .total((long) items.size()));
  }

  @Override
  public ResponseEntity<List<FlowActionResult>> reportFlowSubmitFlowAction(
      FlowActionRequest flowActionRequest) {
    String tenant = InspectionCatalogController.currentTenantIdOrDefaultStatic(directory);
    List<FlowActionResult> results = service.submitAction(tenant, flowActionRequest);
    return ResponseEntity.ok(results);
  }
}
