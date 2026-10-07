package io.xr.lab.platform.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import io.xr.harness.junit.Fn;
import io.xr.lab.platform.entity.ContractEntity;
import io.xr.lab.platform.entity.InspectionReportNameEntity;
import io.xr.lab.platform.entity.SampleEntity;
import io.xr.lab.platform.entity.SampleReceiptEntity;
import io.xr.lab.platform.repository.ContractRepository;
import io.xr.lab.platform.repository.InspectionReportNameRepository;
import io.xr.lab.platform.repository.SampleReceiptRepository;
import io.xr.lab.platform.repository.SampleRepository;
import io.xr.lab.shared.dto.DashboardStats;
import io.xr.lab.shared.dto.DashboardStatsFunnelByStage;
import io.xr.lab.shared.dto.FlowStatus;
import io.xr.lab.shared.dto.ReceiptResult;
import io.xr.lab.shared.dto.SummaryData;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** M05 报告汇总 + 仪表盘统计（B4）。2 子项单测。 */
class SummaryServiceTest {

  private static final String TENANT = "TENANT-001";

  private SampleReceiptRepository receiptRepo;
  private ContractRepository contractRepo;
  private SampleRepository sampleRepo;
  private InspectionReportNameRepository reportNameRepo;
  private SummaryService service;

  @BeforeEach
  void setUp() {
    receiptRepo = org.mockito.Mockito.mock(SampleReceiptRepository.class);
    contractRepo = org.mockito.Mockito.mock(ContractRepository.class);
    sampleRepo = org.mockito.Mockito.mock(SampleRepository.class);
    reportNameRepo = org.mockito.Mockito.mock(InspectionReportNameRepository.class);
    // 码表预载走 findAll()（空码表：材料映射全 null → 合格率全 0，个别测试自己覆盖）
    when(reportNameRepo.findAll()).thenReturn(java.util.List.of());
    service = new SummaryService(receiptRepo, contractRepo, sampleRepo, reportNameRepo);
  }

  // M05.F01.I01 report summary

  @Test
  @Fn({"M05.F01.I01"})
  void getReportSummary_all_returnsColumnsAndAllRows() {
    when(receiptRepo.summary(TENANT, "ALL", "", ""))
        .thenReturn(
            List.of(
                receipt("R-001", "CAT-A", FlowStatus.DATA_ENTRY, ReceiptResult.PASS, "RP-1"),
                receipt("R-002", "CAT-B", FlowStatus.REVIEW, null, null)));
    SummaryData out = service.getReportSummary(TENANT, null, null, null);
    assertEquals("报告汇总（ALL）", out.getSummaryName());
    assertEquals(6, out.getColumns().size());
    assertEquals(2, out.getRows().size());
    Map<String, String> row0 = out.getRows().get(0);
    assertEquals("R-001", row0.get("commissionCode"));
    assertEquals("CAT-A", row0.get("categoryCode"));
    assertEquals("data_entry", row0.get("flowStatus"));
    assertEquals("pass", row0.get("result"));
    assertEquals("RP-1", row0.get("reportCode"));
  }

  @Test
  @Fn({"M05.F01.I01"})
  void getReportSummary_byCategoryCode_passesThrough() {
    when(receiptRepo.summary(TENANT, "CAT-A", "", ""))
        .thenReturn(List.of(receipt("R-001", "CAT-A", FlowStatus.DATA_ENTRY, null, null)));
    SummaryData out = service.getReportSummary(TENANT, "CAT-A", null, null);
    assertTrue(out.getSummaryName().contains("CAT-A"));
    assertEquals(1, out.getRows().size());
  }

  @Test
  @Fn({"M05.F01.I01"})
  void getReportSummary_nullResultAndReportCode_yieldEmptyStrings() {
    when(receiptRepo.summary(TENANT, "ALL", "", ""))
        .thenReturn(List.of(receipt("R-002", "CAT-B", FlowStatus.RECEIVING, null, null)));
    SummaryData out = service.getReportSummary(TENANT, null, null, null);
    Map<String, String> row = out.getRows().get(0);
    assertEquals("", row.get("result"));
    assertEquals("", row.get("reportCode"));
  }

  @Test
  @Fn({"M05.F01.I01"})
  void getReportSummary_blankCategoryCode_treatedAsAll() {
    when(receiptRepo.summary(TENANT, "ALL", "", "")).thenReturn(List.of());
    SummaryData out = service.getReportSummary(TENANT, "  ", null, null);
    assertEquals(0, out.getRows().size());
    assertTrue(out.getSummaryName().contains("ALL"));
  }

  // M05.F01.I06 dashboard stats（ADR-0033 阶段二自 M05.F02.I01 改挂）

  @Test
  @Fn({"M05.F01.I06"})
  void getDashboardStats_aggregatesByStatus() {
    when(receiptRepo.summary(TENANT, "ALL", "", ""))
        .thenReturn(
            List.of(
                receipt("R-001", "CAT-A", FlowStatus.RECEIVING, null, null),
                receipt("R-002", "CAT-A", FlowStatus.TASK_ASSIGNMENT, null, null),
                receipt("R-003", "CAT-A", FlowStatus.DATA_ENTRY, null, null),
                receipt("R-004", "CAT-A", FlowStatus.REVIEW, null, null),
                receipt("R-005", "CAT-A", FlowStatus.APPROVAL, null, null),
                receipt("R-006", "CAT-A", FlowStatus.ISSUANCE, null, null),
                receipt("R-007", "CAT-A", FlowStatus.ARCHIVED, null, null)));
    when(contractRepo.filter(TENANT, "", null))
        .thenReturn(List.of(contract("C-001"), contract("C-002")));
    when(sampleRepo.filter(TENANT, "", ""))
        .thenReturn(List.of(sample("S-001"), sample("S-002"), sample("S-003")));

    DashboardStats out = service.getDashboardStats(TENANT);
    assertEquals(2, out.getContractCount());
    assertEquals(7, out.getReceiptCount());
    assertEquals(3, out.getSampleCount());
    // draft: receiving(1) + task_assignment(1) + data_entry(1) = 3
    assertEquals(3, out.getReportCountByStatus().getDraft());
    // reviewing: review(1) + approval(1) = 2
    assertEquals(2, out.getReportCountByStatus().getReviewing());
    // issued: issuance(1) + archived(1) = 2
    assertEquals(2, out.getReportCountByStatus().getIssued());
    // pendingTask: task_assignment(1) + data_entry(1) + review(1) = 3
    assertEquals(3, out.getPendingTaskCount());
  }

  @Test
  @Fn({"M05.F01.I06"})
  void getDashboardStats_empty_returnsZeros() {
    when(receiptRepo.summary(TENANT, "ALL", "", "")).thenReturn(List.of());
    when(contractRepo.filter(TENANT, "", null)).thenReturn(List.of());
    when(sampleRepo.filter(TENANT, "", "")).thenReturn(List.of());
    DashboardStats out = service.getDashboardStats(TENANT);
    assertEquals(0, out.getContractCount());
    assertEquals(0, out.getReceiptCount());
    assertEquals(0, out.getSampleCount());
    assertEquals(0, out.getReportCountByStatus().getDraft());
    assertEquals(0, out.getPendingTaskCount());
  }

  // === M05.F01.I03 核心指标（材料映射走码表 summaryName，不是 categoryCode）===

  @Test
  @Fn({"M05.F01.I03"})
  void getDashboardStats_materialRate_usesReportNameSummaryName() {
    // 码表：CAT-A summaryName 含「混凝土」→ concrete；CAT-B 含「钢筋」→ rebar
    when(reportNameRepo.findAll())
        .thenReturn(List.of(reportName("CAT-A", "混凝土及砂浆汇总"), reportName("CAT-B", "钢筋原材料汇总")));
    when(receiptRepo.summary(TENANT, "ALL", "", ""))
        .thenReturn(
            List.of(
                receipt("R1", "CAT-A", FlowStatus.REVIEW, ReceiptResult.PASS, null),
                receipt("R2", "CAT-A", FlowStatus.REVIEW, ReceiptResult.FAIL, null),
                receipt("R3", "CAT-B", FlowStatus.REVIEW, ReceiptResult.PASS, null)));

    DashboardStats out = service.getDashboardStats(TENANT);

    assertEquals(2, out.getQualifiedRateByMaterial().getConcrete().getTotal());
    assertEquals(1, out.getQualifiedRateByMaterial().getConcrete().getPass());
    assertEquals(0.5, out.getQualifiedRateByMaterial().getConcrete().getRate(), 0.001);
    assertEquals(1, out.getQualifiedRateByMaterial().getRebar().getTotal());
    assertEquals(0, out.getQualifiedRateByMaterial().getSand().getTotal());
  }

  @Test
  @Fn({"M05.F01.I03"})
  void getDashboardStats_todayTestCountAndReportOutput() {
    // 今日试验总数：createdAt 今日前缀命中 1 条（另一条昨日字面）
    String today = LocalDate.now().toString();
    SampleReceiptEntity todayReceipt =
        receipt("R1", "CAT-A", FlowStatus.DATA_ENTRY, ReceiptResult.PASS, "RP-1");
    todayReceipt.setCreatedAt(today + "T03:00:00Z");
    when(receiptRepo.summary(TENANT, "ALL", "", ""))
        .thenReturn(
            List.of(
                todayReceipt,
                receipt("R2", "CAT-A", FlowStatus.REVIEW, ReceiptResult.PASS, "RP-2")));

    DashboardStats out = service.getDashboardStats(TENANT);

    assertEquals(1, out.getTodayTestCount());
    // 产出量：generated=有 reportCode（R1/R2）= 2；pending=reviewing 桶=1；issued=0
    assertEquals(2, out.getReportOutputByStatus().getGenerated());
    assertEquals(1, out.getReportOutputByStatus().getPending());
    assertEquals(0, out.getReportOutputByStatus().getIssued());
  }

  // === M05.F01.I04 任务漏斗（data_entry 按 reportCode 分 testing/reporting）===

  @Test
  @Fn({"M05.F01.I04"})
  void getDashboardStats_funnel_sixStages() {
    // 各状态 1 条 + data_entry 有/无 reportCode 分段（镜像 aspnetcore 同名测试）
    when(receiptRepo.summary(TENANT, "ALL", "", ""))
        .thenReturn(
            List.of(
                receipt("R1", "CAT-A", FlowStatus.RECEIVING, null, "RP-1"),
                receipt("R2", "CAT-A", FlowStatus.TASK_ASSIGNMENT, null, "RP-2"),
                receipt("R3", "CAT-A", FlowStatus.DATA_ENTRY, null, null),
                receipt("R4", "CAT-A", FlowStatus.REVIEW, null, "RP-4"),
                receipt("R5", "CAT-A", FlowStatus.ISSUANCE, null, "RP-5")));

    DashboardStats out = service.getDashboardStats(TENANT);

    DashboardStatsFunnelByStage f = out.getFunnelByStage();
    assertEquals(1, f.getPendingCollect());
    assertEquals(1, f.getReceived());
    assertEquals(1, f.getTesting()); // data_entry 无 reportCode
    assertEquals(0, f.getReporting()); // data_entry 有 reportCode 才进编制段
    assertEquals(1, f.getReviewing()); // review(1) + approval(0)
    assertEquals(1, f.getIssued()); // issuance(1) + archived(0)
  }

  // === helpers ===

  private static SampleReceiptEntity receipt(
      String id, String cat, FlowStatus status, ReceiptResult result, String reportCode) {
    SampleReceiptEntity e = new SampleReceiptEntity();
    e.setId(id);
    e.setTenantId(TENANT);
    e.setContractId("C-001");
    e.setCommissionCode(id);
    e.setCommissionDate("2026-08-18");
    e.setCategoryCode(cat);
    e.setReceivedBy("admin");
    e.setSampleSource("client");
    e.setTestCategory("concrete");
    e.setFlowStatus(status);
    e.setResult(result);
    e.setReportCode(reportCode);
    e.setCreatedAt("2026-08-18T10:00:00Z");
    e.setUpdatedAt("2026-08-18T10:00:00Z");
    return e;
  }

  private static ContractEntity contract(String id) {
    ContractEntity c = new ContractEntity();
    c.setId(id);
    c.setTenantId(TENANT);
    return c;
  }

  private static SampleEntity sample(String id) {
    SampleEntity s = new SampleEntity();
    s.setId(id);
    s.setTenantId(TENANT);
    return s;
  }

  private static InspectionReportNameEntity reportName(String code, String summaryName) {
    InspectionReportNameEntity e = new InspectionReportNameEntity();
    e.setCode(code);
    e.setSummaryName(summaryName);
    return e;
  }
}
