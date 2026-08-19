package io.xr.lab.platform.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.xr.harness.junit.Fn;
import io.xr.lab.platform.entity.SampleReceiptEntity;
import io.xr.lab.platform.repository.SampleReceiptRepository;
import io.xr.lab.shared.dto.FlowAction;
import io.xr.lab.shared.dto.FlowActionRequest;
import io.xr.lab.shared.dto.FlowActionResult;
import io.xr.lab.shared.dto.FlowStatus;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** M03.F05 队列 + M03.F06 推进 2 子项单测。 */
class ReportFlowServiceTest {

  private static final String TENANT = "TENANT-001";

  private SampleReceiptService receiptService;
  private SampleReceiptRepository repo;
  private ReportFlowService service;

  @BeforeEach
  void setUp() {
    receiptService = org.mockito.Mockito.mock(SampleReceiptService.class);
    repo = org.mockito.Mockito.mock(SampleReceiptRepository.class);
    service = new ReportFlowService(receiptService, repo);
  }

  // M03.F05.I01 queue
  // service.flowQueue(stage) 同一段同时支撑 F05/F07/F08 的 3 个阶段队列（共用 ReportFlowController#flowQueue）

  @Test
  @Fn({"M03.F05.I01", "M03.F07.I01", "M03.F08.I01"})
  void flowQueue_delegatesToReceiptService() {
    var stub =
        new io.xr.lab.shared.dto.SampleReceipt()
            .id("R-001")
            .commissionCode("CM-001")
            .flowStatus(FlowStatus.REVIEW);
    when(receiptService.flowQueue(TENANT, FlowStatus.REVIEW, 50)).thenReturn(List.of(stub));
    var out = service.flowQueue(TENANT, FlowStatus.REVIEW);
    assertEquals(1, out.size());
    assertEquals("R-001", out.get(0).getId());
  }

  // M03.F06.I01 advance
  // service.submitAction(stage, action) 同一段同时支撑 F05/F06/F07/F08 4 个阶段 I03 推进/退回
  // 测试内部 transitionTo 的具体 stage-pair 是 REVIEW→APPROVAL（F05 推进），但底层 transitionTo 被 F05/F06/F07/F08
  // 共同消费

  @Test
  @Fn({"M03.F05.I03", "M03.F06.I01", "M03.F06.I03", "M03.F07.I03", "M03.F08.I03"})
  void submitAction_advance_reviewToApproval_success() {
    SampleReceiptEntity existing = entity("R-001", FlowStatus.REVIEW);
    when(repo.findByTenantIdAndId(TENANT, "R-001")).thenReturn(Optional.of(existing));
    when(receiptService.transitionTo(
            org.mockito.ArgumentMatchers.eq(TENANT),
            org.mockito.ArgumentMatchers.eq("R-001"),
            org.mockito.ArgumentMatchers.eq(FlowStatus.REVIEW),
            org.mockito.ArgumentMatchers.eq(FlowStatus.APPROVAL),
            org.mockito.ArgumentMatchers.anyString(),
            org.mockito.ArgumentMatchers.isNull()))
        .thenAnswer(inv -> invocationReturn(inv));
    FlowActionRequest req =
        new FlowActionRequest()
            .ids(List.of("R-001"))
            .action(FlowAction.SUBMIT)
            .operator("reviewer");
    List<FlowActionResult> results = service.submitAction(TENANT, req);
    assertEquals(1, results.size());
    assertTrue(results.get(0).getOk());
    assertEquals(FlowStatus.APPROVAL, results.get(0).getFlowStatus());
    verify(receiptService, times(1))
        .transitionTo(
            org.mockito.ArgumentMatchers.eq(TENANT),
            org.mockito.ArgumentMatchers.eq("R-001"),
            org.mockito.ArgumentMatchers.eq(FlowStatus.REVIEW),
            org.mockito.ArgumentMatchers.eq(FlowStatus.APPROVAL),
            org.mockito.ArgumentMatchers.anyString(),
            org.mockito.ArgumentMatchers.any());
  }

  @Test
  @Fn({"M03.F05.I03", "M03.F06.I01", "M03.F06.I03", "M03.F07.I03", "M03.F08.I03"})
  void submitAction_return_approvalToReview() {
    SampleReceiptEntity existing = entity("R-001", FlowStatus.APPROVAL);
    when(repo.findByTenantIdAndId(TENANT, "R-001")).thenReturn(Optional.of(existing));
    when(receiptService.transitionTo(
            org.mockito.ArgumentMatchers.anyString(),
            org.mockito.ArgumentMatchers.anyString(),
            org.mockito.ArgumentMatchers.any(FlowStatus.class),
            org.mockito.ArgumentMatchers.any(FlowStatus.class),
            org.mockito.ArgumentMatchers.anyString(),
            org.mockito.ArgumentMatchers.any()))
        .thenAnswer(inv -> invocationReturn(inv));
    FlowActionRequest req =
        new FlowActionRequest()
            .ids(List.of("R-001"))
            .action(FlowAction.RETURN)
            .operator("approver");
    var results = service.submitAction(TENANT, req);
    assertTrue(results.get(0).getOk());
    assertEquals(FlowStatus.REVIEW, results.get(0).getFlowStatus());
  }

  @Test
  @Fn({"M03.F05.I03", "M03.F06.I01", "M03.F06.I03", "M03.F07.I03", "M03.F08.I03"})
  void submitAction_missing_reportsFailure() {
    when(repo.findByTenantIdAndId(TENANT, "MISSING")).thenReturn(Optional.empty());
    FlowActionRequest req =
        new FlowActionRequest().ids(List.of("MISSING")).action(FlowAction.SUBMIT).operator("op");
    var results = service.submitAction(TENANT, req);
    assertEquals(1, results.size());
    assertEquals(Boolean.FALSE, results.get(0).getOk());
    assertTrue(results.get(0).getMessage().contains("Receipt not found"));
  }

  private static SampleReceiptEntity entity(String id, FlowStatus stage) {
    SampleReceiptEntity e = new SampleReceiptEntity();
    e.setId(id);
    e.setContractId("C-001");
    e.setCommissionCode("CM-001");
    e.setCommissionDate("2026-08-18");
    e.setCategoryCode("CAT-1");
    e.setReceivedBy("admin");
    e.setSampleSource("client");
    e.setTestCategory("concrete");
    e.setTenantId(TENANT);
    e.setFlowStatus(stage);
    e.setFlowHistory("[]");
    e.setCreatedAt("2026-08-18T10:00:00Z");
    e.setUpdatedAt("2026-08-18T10:00:00Z");
    return e;
  }

  private static io.xr.lab.shared.dto.SampleReceipt invocationReturn(
      org.mockito.invocation.InvocationOnMock inv) {
    return new io.xr.lab.shared.dto.SampleReceipt().id((String) inv.getArgument(1));
  }
}
