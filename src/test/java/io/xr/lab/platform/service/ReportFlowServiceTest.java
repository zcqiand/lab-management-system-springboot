package io.xr.lab.platform.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

/**
 * M03 7 阶段全 act 模式单元测试。
 *
 * <p>历史背景：2026-09-17 M03 重整把早期 3 阶段 9 op (submit/return/withdraw) + 报告 4 阶段 4 act op 合并为 7 act op
 * (receiving/assigning/data-entry/review/approve/issuance/archived)。 旧 service.flowQueue() /
 * service.submitAction() 已删，本测试文件随之重写。
 *
 * <p>旧测试方法 (flowQueue_delegatesToReceiptService / submitAction_advance_* / submitAction_return_* /
 * submitAction_missing_reportsFailure) 引用已删方法，2026-09-17 删除；新 7 act 方法的 单元测试见各 service 方法的 @Fn
 * 标注和后续 M03 重构任务补全。
 */
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

  // M03.F01.I08 receiving 阶段提交（receiving → task_assignment）
  // 7 act 模式 smoke：actReceiving(SUBMIT) 应调 receiptService.transitionTo 并返 ok=true
  @Test
  @Fn({"M03.F01.I08"})
  void actReceiving_submit_delegatesToReceiptService() {
    SampleReceiptEntity existing = entity("R-001", FlowStatus.RECEIVING);
    when(repo.findByTenantIdAndId(TENANT, "R-001")).thenReturn(Optional.of(existing));
    when(receiptService.transitionTo(
            org.mockito.ArgumentMatchers.anyString(),
            org.mockito.ArgumentMatchers.anyString(),
            org.mockito.ArgumentMatchers.any(FlowStatus.class),
            org.mockito.ArgumentMatchers.any(FlowStatus.class),
            org.mockito.ArgumentMatchers.any(FlowAction.class),
            org.mockito.ArgumentMatchers.anyString(),
            org.mockito.ArgumentMatchers.any()))
        .thenAnswer(inv -> invocationReturn(inv));
    FlowActionRequest req =
        new FlowActionRequest()
            .ids(List.of("R-001"))
            .action(FlowAction.SUBMIT)
            .operator("receiver");
    List<FlowActionResult> results = service.actReceiving(TENANT, req);
    assertEquals(1, results.size());
    assertTrue(results.get(0).getOk());
  }

  // 5.69 last_submitted_by 写/清对齐（SSOT = lab-nextjs db-queries.ts:271-276）：
  //   submit → 写当前操作人；withdraw → 清空（null）；return → 保留原值。
  // 身份取 FlowActionRequest.operator（前端登录态 user.id ?? user.username，
  // nextjs act-route.ts:39-46 同款必填校验），禁字面量兜底（ADR-0019）。
  @Test
  @Fn({"M03.F01.I08"})
  void actReceiving_submit_writesLastSubmittedByFromOperator() {
    SampleReceiptEntity existing = entity("R-001", FlowStatus.RECEIVING);
    when(repo.findByTenantIdAndId(TENANT, "R-001")).thenReturn(Optional.of(existing));
    when(receiptService.transitionTo(
            org.mockito.ArgumentMatchers.anyString(),
            org.mockito.ArgumentMatchers.anyString(),
            org.mockito.ArgumentMatchers.any(FlowStatus.class),
            org.mockito.ArgumentMatchers.any(FlowStatus.class),
            org.mockito.ArgumentMatchers.any(FlowAction.class),
            org.mockito.ArgumentMatchers.anyString(),
            org.mockito.ArgumentMatchers.any()))
        .thenAnswer(inv -> invocationReturn(inv));
    FlowActionRequest req =
        new FlowActionRequest()
            .ids(List.of("R-001"))
            .action(FlowAction.SUBMIT)
            .operator("user-uuid-001");
    service.actReceiving(TENANT, req);

    org.mockito.Mockito.verify(receiptService)
        .transitionTo(
            org.mockito.ArgumentMatchers.eq(TENANT),
            org.mockito.ArgumentMatchers.eq("R-001"),
            org.mockito.ArgumentMatchers.eq(FlowStatus.RECEIVING),
            org.mockito.ArgumentMatchers.eq(FlowStatus.TASK_ASSIGNMENT),
            org.mockito.ArgumentMatchers.eq(FlowAction.SUBMIT),
            org.mockito.ArgumentMatchers.eq("user-uuid-001"),
            org.mockito.ArgumentMatchers.isNull());
  }

  @Test
  @Fn({"M03.F01.I08"})
  void actReceiving_withdraw_passesActionThroughForClearing() {
    SampleReceiptEntity existing = entity("R-001", FlowStatus.RECEIVING);
    when(repo.findByTenantIdAndId(TENANT, "R-001")).thenReturn(Optional.of(existing));
    when(receiptService.transitionTo(
            org.mockito.ArgumentMatchers.anyString(),
            org.mockito.ArgumentMatchers.anyString(),
            org.mockito.ArgumentMatchers.any(FlowStatus.class),
            org.mockito.ArgumentMatchers.any(FlowStatus.class),
            org.mockito.ArgumentMatchers.any(FlowAction.class),
            org.mockito.ArgumentMatchers.anyString(),
            org.mockito.ArgumentMatchers.any()))
        .thenAnswer(inv -> invocationReturn(inv));
    FlowActionRequest req =
        new FlowActionRequest()
            .ids(List.of("R-001"))
            .action(FlowAction.WITHDRAW)
            .operator("user-uuid-001");
    service.actReceiving(TENANT, req);

    org.mockito.Mockito.verify(receiptService)
        .transitionTo(
            org.mockito.ArgumentMatchers.eq(TENANT),
            org.mockito.ArgumentMatchers.eq("R-001"),
            org.mockito.ArgumentMatchers.eq(FlowStatus.RECEIVING),
            org.mockito.ArgumentMatchers.eq(FlowStatus.RECEIVING),
            org.mockito.ArgumentMatchers.eq(FlowAction.WITHDRAW),
            org.mockito.ArgumentMatchers.eq("user-uuid-001"),
            org.mockito.ArgumentMatchers.isNull());
  }

  // 5.75 operator 契约必填边缘对齐（SSOT = lab-nextjs act-route.ts:39-44）：
  // 缺失与空串都 400 —— service 层对空串抛 IllegalArgumentException
  // （GlobalExceptionHandler → 400 {code:"BAD_REQUEST", message:"operator is required"}）；
  // null 已由 FlowActionRequest @NotNull 在 controller 层 400 拦截，此处防御兜底。
  @Test
  @Fn({"M03.F01.I08"})
  void actReceiving_blankOperator_throwsIllegalArgument() {
    SampleReceiptEntity existing = entity("R-001", FlowStatus.RECEIVING);
    when(repo.findByTenantIdAndId(TENANT, "R-001")).thenReturn(Optional.of(existing));
    FlowActionRequest req =
        new FlowActionRequest().ids(List.of("R-001")).action(FlowAction.SUBMIT).operator("");
    IllegalArgumentException ex =
        org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class, () -> service.actReceiving(TENANT, req));
    assertEquals("operator is required", ex.getMessage());
  }

  @Test
  @Fn({"M03.F08.I05"})
  void actArchived_blankOperator_throwsIllegalArgument() {
    SampleReceiptEntity existing = entity("R-001", FlowStatus.ARCHIVED);
    when(repo.findByTenantIdAndId(TENANT, "R-001")).thenReturn(Optional.of(existing));
    FlowActionRequest req =
        new FlowActionRequest().ids(List.of("R-001")).action(FlowAction.SUBMIT).operator("");
    IllegalArgumentException ex =
        org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class, () -> service.actArchived(TENANT, req));
    assertEquals("operator is required", ex.getMessage());
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
