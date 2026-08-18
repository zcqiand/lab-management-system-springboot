package io.xr.lab.platform.service;

import io.xr.lab.platform.entity.SampleReceiptEntity;
import io.xr.lab.platform.repository.ContractRepository;
import io.xr.lab.platform.repository.SampleReceiptRepository;
import io.xr.lab.platform.repository.SampleRepository;
import io.xr.lab.shared.dto.DashboardStats;
import io.xr.lab.shared.dto.DashboardStatsReportCountByStatus;
import io.xr.lab.shared.dto.FlowStatus;
import io.xr.lab.shared.dto.SummaryColumn;
import io.xr.lab.shared.dto.SummaryData;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * M05 报告汇总 + 仪表盘统计（B4）。2 端点。
 *
 * <p>按 tenant + 报告类别（categoryCode）过滤 sample_receipts，列结构镜像 lab-msw
 * summaryExtraHandlers；「ALL」=全租户，「RN-XXX」=按报告类别过滤（dateFrom/dateTo 暂存为可选过滤，sample_receipts 用
 * commission_date 作为时间维度）。
 *
 * <p>仪表盘统计：合同/接样/样品 3 总数 + 按 flowStatus 聚合的 3 桶报告状态 + 任务计数。
 */
@Service
public class SummaryService {

  /** 「ALL」特殊值 = 不按 category 过滤。 */
  public static final String CATEGORY_ALL = "ALL";

  private static final List<SummaryColumn> SUMMARY_COLUMNS =
      List.of(
          new SummaryColumn().key("commissionCode").label("委托编号"),
          new SummaryColumn().key("categoryCode").label("报告类别"),
          new SummaryColumn().key("projectName").label("工程名称"),
          new SummaryColumn().key("flowStatus").label("流程状态"),
          new SummaryColumn().key("result").label("结论"),
          new SummaryColumn().key("reportCode").label("报告编号"));

  private final SampleReceiptRepository receiptRepo;
  private final ContractRepository contractRepo;
  private final SampleRepository sampleRepo;

  public SummaryService(
      SampleReceiptRepository receiptRepo,
      ContractRepository contractRepo,
      SampleRepository sampleRepo) {
    this.receiptRepo = receiptRepo;
    this.contractRepo = contractRepo;
    this.sampleRepo = sampleRepo;
  }

  /**
   * 报告汇总：按 categoryCode（ALL = 全表）过滤当前租户的接样单，输出列 + 行。
   *
   * <p>dateFrom/dateTo 简化为 commissionDate 前缀匹配（YYYY-MM-DD 字符串字典序与日期序一致）。 null 视作无界。
   */
  public SummaryData getReportSummary(
      String tenantId, String categoryCode, String dateFrom, String dateTo) {
    String cat = categoryCode == null || categoryCode.isBlank() ? CATEGORY_ALL : categoryCode;
    String from = dateFrom == null ? "" : dateFrom;
    String to = dateTo == null ? "" : dateTo;

    List<SampleReceiptEntity> rows = receiptRepo.summary(tenantId, cat, from, to);

    List<Map<String, String>> rendered = rows.stream().map(SummaryService::renderRow).toList();
    return new SummaryData()
        .summaryName("报告汇总（" + cat + "）")
        .columns(SUMMARY_COLUMNS)
        .rows(rendered);
  }

  /** 仪表盘统计：合同/接样/样品 计数 + 按状态聚合的报告计数 + 待办任务计数。 */
  public DashboardStats getDashboardStats(String tenantId) {
    List<SampleReceiptEntity> receipts = receiptRepo.summary(tenantId, CATEGORY_ALL, "", "");
    long contractCount = contractRepo.filter(tenantId, "", null).size();
    long sampleCount = sampleRepo.filter(tenantId, "", "").size();

    int draft =
        countByStatus(receipts, FlowStatus.RECEIVING)
            + countByStatus(receipts, FlowStatus.TASK_ASSIGNMENT)
            + countByStatus(receipts, FlowStatus.DATA_ENTRY);
    int reviewing =
        countByStatus(receipts, FlowStatus.REVIEW) + countByStatus(receipts, FlowStatus.APPROVAL);
    int issued =
        countByStatus(receipts, FlowStatus.ISSUANCE) + countByStatus(receipts, FlowStatus.ARCHIVED);
    int pending =
        countByStatus(receipts, FlowStatus.TASK_ASSIGNMENT)
            + countByStatus(receipts, FlowStatus.DATA_ENTRY)
            + countByStatus(receipts, FlowStatus.REVIEW);

    return new DashboardStats()
        .contractCount((int) contractCount)
        .receiptCount(receipts.size())
        .sampleCount((int) sampleCount)
        .reportCountByStatus(
            new DashboardStatsReportCountByStatus()
                .draft(draft)
                .reviewing(reviewing)
                .issued(issued))
        .pendingTaskCount(pending);
  }

  private static int countByStatus(List<SampleReceiptEntity> rows, FlowStatus s) {
    int n = 0;
    for (SampleReceiptEntity r : rows) {
      if (r.getFlowStatus() == s) {
        n++;
      }
    }
    return n;
  }

  private static Map<String, String> renderRow(SampleReceiptEntity r) {
    Map<String, String> row = new LinkedHashMap<>();
    row.put("commissionCode", r.getCommissionCode() == null ? "" : r.getCommissionCode());
    row.put("categoryCode", r.getCategoryCode() == null ? "" : r.getCategoryCode());
    row.put("projectName", r.getProjectName() == null ? "" : r.getProjectName());
    row.put("flowStatus", r.getFlowStatus() == null ? "" : r.getFlowStatus().getValue());
    row.put("result", r.getResult() == null ? "" : r.getResult().getValue());
    row.put("reportCode", r.getReportCode() == null ? "" : r.getReportCode());
    return row;
  }
}
