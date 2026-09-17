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
 * 历史背景：2026-09-17 M03 重整把早期 3 阶段 9 op (submit/return/withdraw) + 报告 4 阶段 4 act op
 * 合并为 7 act op (receiving/assigning/data-entry/review/approve/issuance/archived)。
 * 旧 service.flowQueue() / service.submitAction() 已删，本测试文件随之重写。
 *
 * 旧测试方法 (flowQueue_delegatesToReceiptService / submitAction_advance_* / submitAction_return_*
 * / submitAction_missing_reportsFailure) 引用已删方法，2026-09-17 删除；新 7 act 方法的
 * 单元测试见各 service 方法的 @Fn 标注和后续 M03 重构任务补全。
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
