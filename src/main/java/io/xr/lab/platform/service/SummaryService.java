package io.xr.lab.platform.service;

import io.xr.lab.platform.entity.InspectionReportNameEntity;
import io.xr.lab.platform.entity.SampleReceiptEntity;
import io.xr.lab.platform.repository.ContractRepository;
import io.xr.lab.platform.repository.InspectionReportNameRepository;
import io.xr.lab.platform.repository.SampleReceiptRepository;
import io.xr.lab.platform.repository.SampleRepository;
import io.xr.lab.shared.dto.DashboardStats;
import io.xr.lab.shared.dto.DashboardStatsFunnelByStage;
import io.xr.lab.shared.dto.DashboardStatsQualifiedRateByMaterial;
import io.xr.lab.shared.dto.DashboardStatsReportCountByStatus;
import io.xr.lab.shared.dto.DashboardStatsReportOutputByStatus;
import io.xr.lab.shared.dto.FlowStatus;
import io.xr.lab.shared.dto.MaterialQualifiedRate;
import io.xr.lab.shared.dto.SummaryColumn;
import io.xr.lab.shared.dto.SummaryData;
import java.time.LocalDate;
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
 * <p>仪表盘统计：合同/接样/样品 3 总数 + 按 flowStatus 聚合的 3 桶报告状态 + 任务计数。 2026-09-04 起扩展 M05.F01.I03（核心指标）+
 * M05.F01.I04（任务漏斗）4 段： todayTestCount / qualifiedRateByMaterial{concrete,rebar,sand} /
 * reportOutputByStatus{generated,pending,issued} /
 * funnelByStage{pending_collect,received,testing,reporting,reviewing,issued}。
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

  // 报告名称 summaryName → 材料类型映射（与 msw 端 handlers-extra.ts 同款语义）
  private static final Map<String, String> MATERIAL_KEYWORDS = new LinkedHashMap<>();

  static {
    MATERIAL_KEYWORDS.put("concrete", "混凝土|水泥");
    MATERIAL_KEYWORDS.put("rebar", "钢筋|钢材|焊接|机械连接|连接");
    MATERIAL_KEYWORDS.put("sand", "砂|碎（卵）石|轻集料|颗粒级配");
  }

  private final SampleReceiptRepository receiptRepo;
  private final ContractRepository contractRepo;
  private final SampleRepository sampleRepo;
  private final InspectionReportNameRepository reportNameRepo;

  public SummaryService(
      SampleReceiptRepository receiptRepo,
      ContractRepository contractRepo,
      SampleRepository sampleRepo,
      InspectionReportNameRepository reportNameRepo) {
    this.receiptRepo = receiptRepo;
    this.contractRepo = contractRepo;
    this.sampleRepo = sampleRepo;
    this.reportNameRepo = reportNameRepo;
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

  /** 仪表盘统计：合同/接样/样品 计数 + 按状态聚合的报告计数 + 待办任务计数 + 4 段扩展。 */
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

    // ─── M05.F01.I03 今日试验总数 ───
    String today = LocalDate.now().toString();
    int todayTestCount =
        (int)
            receipts.stream()
                .filter(
                    r -> {
                      String c = r.getCreatedAt() == null ? "" : String.valueOf(r.getCreatedAt());
                      String t =
                          r.getTestStartDate() == null ? "" : String.valueOf(r.getTestStartDate());
                      return c.startsWith(today) || t.startsWith(today);
                    })
                .count();

    // ─── M05.F01.I03 按材料类型合格率 ───
    // 码表全量预载（一次 JPQL），避免逐条查库的 N+1（见 materialOf 注释）。
    Map<String, String> summaryNameByCode = new LinkedHashMap<>();
    for (InspectionReportNameEntity rn : reportNameRepo.findAll()) {
      summaryNameByCode.put(rn.getCode(), rn.getSummaryName() == null ? "" : rn.getSummaryName());
    }
    Map<String, Integer> matTotal = new LinkedHashMap<>();
    Map<String, Integer> matPass = new LinkedHashMap<>();
    matTotal.put("concrete", 0);
    matTotal.put("rebar", 0);
    matTotal.put("sand", 0);
    matPass.put("concrete", 0);
    matPass.put("rebar", 0);
    matPass.put("sand", 0);
    for (SampleReceiptEntity r : receipts) {
      String mat = materialOf(r.getCategoryCode(), summaryNameByCode);
      if (mat == null) continue;
      matTotal.put(mat, matTotal.get(mat) + 1);
      if (r.getResult() != null && "pass".equals(r.getResult().getValue())) {
        matPass.put(mat, matPass.get(mat) + 1);
      }
    }
    DashboardStatsQualifiedRateByMaterial qrm = new DashboardStatsQualifiedRateByMaterial();
    for (Map.Entry<String, Integer> e : matTotal.entrySet()) {
      String mat = e.getKey();
      int total = e.getValue();
      int pass = matPass.get(mat);
      double rate = total > 0 ? Math.round((double) pass / total * 1000) / 1000.0 : 0.0;
      MaterialQualifiedRate mqr = new MaterialQualifiedRate().total(total).pass(pass).rate(rate);
      if ("concrete".equals(mat)) qrm.concrete(mqr);
      else if ("rebar".equals(mat)) qrm.rebar(mqr);
      else if ("sand".equals(mat)) qrm.sand(mqr);
    }

    // ─── M05.F01.I03 报告产出量 ───
    int generatedCount = (int) receipts.stream().filter(r -> r.getReportCode() != null).count();
    int pendingCount = reviewing;
    int issuedCount = issued;
    DashboardStatsReportOutputByStatus ros =
        new DashboardStatsReportOutputByStatus()
            .generated(generatedCount)
            .pending(pendingCount)
            .issued(issuedCount);

    // ─── M05.F01.I04 任务状态漏斗（6 段）───
    int dataEntryNoReport =
        (int)
            receipts.stream()
                .filter(
                    r -> r.getFlowStatus() == FlowStatus.DATA_ENTRY && r.getReportCode() == null)
                .count();
    int dataEntryWithReport =
        (int)
            receipts.stream()
                .filter(
                    r -> r.getFlowStatus() == FlowStatus.DATA_ENTRY && r.getReportCode() != null)
                .count();
    DashboardStatsFunnelByStage funnel =
        new DashboardStatsFunnelByStage()
            .pendingCollect(countByStatus(receipts, FlowStatus.RECEIVING))
            .received(countByStatus(receipts, FlowStatus.TASK_ASSIGNMENT))
            .testing(dataEntryNoReport)
            .reporting(dataEntryWithReport)
            .reviewing(reviewing)
            .issued(issuedCount);

    return new DashboardStats()
        .contractCount((int) contractCount)
        .receiptCount(receipts.size())
        .sampleCount((int) sampleCount)
        .reportCountByStatus(
            new DashboardStatsReportCountByStatus()
                .draft(draft)
                .reviewing(reviewing)
                .issued(issued))
        .pendingTaskCount(pending)
        .todayTestCount(todayTestCount)
        .qualifiedRateByMaterial(qrm)
        .reportOutputByStatus(ros)
        .funnelByStage(funnel);
  }

  /**
   * categoryCode → 材料类型。 码表全量预载后内存匹配——逐条 {@code summaryNameFor} 是 N+1（210 receipts × JPQL ≈
   * 46s，live contract-test 8s timeout 必挂），禁止回退。
   */
  private String materialOf(String categoryCode, Map<String, String> summaryNameByCode) {
    if (categoryCode == null) return null;
    String summaryName = summaryNameByCode.getOrDefault(categoryCode, "");
    for (Map.Entry<String, String> e : MATERIAL_KEYWORDS.entrySet()) {
      for (String kw : e.getValue().split("\\|")) {
        if (summaryName.contains(kw)) return e.getKey();
      }
    }
    return null;
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
