package io.xr.lab.platform.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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

  @SuppressFBWarnings(
      value = "EI_EXPOSE_REP2",
      justification = "Spring DI singleton: 控制器按规范持有 service / directory 的共享 bean 引用。")
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
    return ResponseEntity.ok(new ReceiptsListReceipts200Response().items(items));
  }

  @Override
  public ResponseEntity<List<FlowActionResult>> reportFlowSubmitFlowAction(
      FlowActionRequest flowActionRequest) {
    String tenant = InspectionCatalogController.currentTenantIdOrDefaultStatic(directory);
    List<FlowActionResult> results = service.submitAction(tenant, flowActionRequest);
    return ResponseEntity.ok(results);
  }
}
