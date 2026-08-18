package io.xr.lab.platform.service;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.xr.lab.platform.entity.SampleReceiptEntity;
import io.xr.lab.platform.repository.SampleReceiptRepository;
import io.xr.lab.shared.dto.FlowAction;
import io.xr.lab.shared.dto.FlowActionRequest;
import io.xr.lab.shared.dto.FlowActionResult;
import io.xr.lab.shared.dto.FlowStatus;
import io.xr.lab.shared.dto.SampleReceipt;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/** 报告流程状态机（M03.F05-F08，7 阶段）。 */
@Service
public class ReportFlowService {

  private final SampleReceiptService receiptService;
  private final SampleReceiptRepository repo;

  @SuppressFBWarnings(
      value = "EI_EXPOSE_REP2",
      justification = "Spring DI singleton: service 持有 Spring 注入的服务 / 仓储 bean 引用，是规范模式。")
  public ReportFlowService(SampleReceiptService receiptService, SampleReceiptRepository repo) {
    this.receiptService = receiptService;
    this.repo = repo;
  }

  /** SUBMIT（推进）阶段映射。 */
  private static final Map<FlowStatus, FlowStatus> SUBMIT_NEXT = initNext();

  /** RETURN（退回上一阶段）映射。 */
  private static final Map<FlowStatus, FlowStatus> RETURN_PREV = initPrev();

  private static Map<FlowStatus, FlowStatus> initNext() {
    EnumMap<FlowStatus, FlowStatus> m = new EnumMap<>(FlowStatus.class);
    m.put(FlowStatus.RECEIVING, FlowStatus.TASK_ASSIGNMENT);
    m.put(FlowStatus.TASK_ASSIGNMENT, FlowStatus.DATA_ENTRY);
    m.put(FlowStatus.DATA_ENTRY, FlowStatus.REVIEW);
    m.put(FlowStatus.REVIEW, FlowStatus.APPROVAL);
    m.put(FlowStatus.APPROVAL, FlowStatus.ISSUANCE);
    m.put(FlowStatus.ISSUANCE, FlowStatus.ARCHIVED);
    return java.util.Collections.unmodifiableMap(m);
  }

  private static Map<FlowStatus, FlowStatus> initPrev() {
    EnumMap<FlowStatus, FlowStatus> m = new EnumMap<>(FlowStatus.class);
    m.put(FlowStatus.TASK_ASSIGNMENT, FlowStatus.RECEIVING);
    m.put(FlowStatus.DATA_ENTRY, FlowStatus.TASK_ASSIGNMENT);
    m.put(FlowStatus.REVIEW, FlowStatus.DATA_ENTRY);
    m.put(FlowStatus.APPROVAL, FlowStatus.REVIEW);
    m.put(FlowStatus.ISSUANCE, FlowStatus.APPROVAL);
    m.put(FlowStatus.ARCHIVED, FlowStatus.ISSUANCE);
    return java.util.Collections.unmodifiableMap(m);
  }

  /** M03.F05 审核/审批/发放队列。 */
  public List<SampleReceipt> flowQueue(String tenantId, FlowStatus stage) {
    return receiptService.flowQueue(tenantId, stage, 50);
  }

  /** M03.F06 阶段推进。 action: SUBMIT（向前）/ RETURN（退回）/ WITHDRAW（撤回 RECEIVING）。 */
  public List<FlowActionResult> submitAction(String tenantId, FlowActionRequest req) {
    List<FlowActionResult> results = new ArrayList<>();
    for (String id : req.getIds()) {
      try {
        SampleReceiptEntity entity =
            repo.findByTenantIdAndId(tenantId, id)
                .orElseThrow(
                    () -> new java.util.NoSuchElementException("Receipt not found: " + id));
        FlowAction action = req.getAction();
        FlowStatus current = entity.getFlowStatus();
        FlowStatus target;
        String operator = req.getOperator();
        String reason = req.getReason();
        switch (action) {
          case SUBMIT:
            target = SUBMIT_NEXT.get(current);
            break;
          case RETURN:
            target = RETURN_PREV.get(current);
            break;
          case WITHDRAW:
            target = (current == FlowStatus.RECEIVING) ? FlowStatus.RECEIVING : null;
            break;
          default:
            target = null;
        }
        if (target == null) {
          results.add(err(id, "Invalid transition from " + current));
          continue;
        }
        receiptService.transitionTo(tenantId, id, current, target, operator, reason);
        results.add(ok(id, target));
      } catch (Exception e) {
        results.add(err(id, e.getMessage()));
      }
    }
    return results;
  }

  private static FlowActionResult ok(String id, FlowStatus to) {
    return new FlowActionResult().id(id).ok(true).flowStatus(to);
  }

  private static FlowActionResult err(String id, String msg) {
    return new FlowActionResult().id(id).ok(false).message(msg);
  }
}
