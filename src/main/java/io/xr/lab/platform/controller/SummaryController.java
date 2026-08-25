package io.xr.lab.platform.controller;

import io.xr.lab.platform.directory.ConfigUserDirectory;
import io.xr.lab.platform.service.SummaryService;
import io.xr.lab.shared.api.SummaryApi;
import io.xr.lab.shared.dto.DashboardStats;
import io.xr.lab.shared.dto.SummaryData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * M05 报告汇总 + 仪表盘 controller（B4）。2 端点。
 *
 * <p>tenant_id 从 JWT claim 取（{@link InspectionCatalogController#currentTenantIdOrDefault} 镜像）， dev
 * fallback 到 {@link ConfigUserDirectory} 默认租户 TENANT-001。Service 不读 claims，由 controller 注入。
 */
@RestController
public class SummaryController implements SummaryApi {

  private final SummaryService service;
  private final ConfigUserDirectory directory;

  public SummaryController(SummaryService service, ConfigUserDirectory directory) {
    this.service = service;
    this.directory = directory;
  }

  @Override
  public ResponseEntity<SummaryData> summaryGetReportSummary(
      String categoryCode, String dateFrom, String dateTo) {
    return ResponseEntity.ok(
        service.getReportSummary(
            InspectionCatalogController.currentTenantIdOrDefaultStatic(directory),
            categoryCode,
            dateFrom,
            dateTo));
  }

  @Override
  public ResponseEntity<DashboardStats> summaryGetDashboardStats() {
    return ResponseEntity.ok(
        service.getDashboardStats(
            InspectionCatalogController.currentTenantIdOrDefaultStatic(directory)));
  }
}
