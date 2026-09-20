package io.xr.lab.platform.service;

import io.xr.lab.platform.entity.SampleReceiptEntity;
import io.xr.lab.platform.repository.SampleReceiptRepository;
import io.xr.lab.shared.dto.FlowAction;
import io.xr.lab.shared.dto.FlowActionRequest;
import io.xr.lab.shared.dto.FlowActionResult;
import io.xr.lab.shared.dto.FlowStatus;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * 报告流程状态机（M03.F01-F08，7 阶段全 act 模式）。
 *
 * <p>2026-09-17 重整（lab-shared commit 13122e9）： 原 7 阶段 × {submit/return/withdraw} = 21 op + 4
 * list*queue + 2 batch 收敛为 7 个 act op： POST /receiving/act, /assigning/act, /data-entry/act,
 * /review/act, /approve/act, /issuance/act, /archived/act 共享端点 body.action={SUBMIT|RETURN|WITHDRAW}
 * 区分动作。
 *
 * <p>action 范围因 stage 而异： - receiving/assigning/data-entry（F01/F02/F03 早期 3 阶段）：{SUBMIT, RETURN,
 * WITHDRAW} - review/approve/issuance（F05/F06/F07 报告 3 阶段）：{SUBMIT, RETURN} - archived（F08
 * 报告归档）：{SUBMIT}（无 next/prev，写 history 当 audit） WITHDRAW 仅在 RECEIVING 阶段自转移（写 history）。
 */
@Service
public class ReportFlowService {

  private final SampleReceiptService receiptService;
  private final SampleReceiptRepository repo;

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

  // ============================================================
  // 7 个 stage-specific act 方法（2026-09-17 收敛）
  // 共享端点 POST /<stage>/act with body.action={SUBMIT|RETURN|WITHDRAW}
  // ============================================================

  /** M03.F01.I08/I09/I10 接样阶段 act — 允许 SUBMIT/RETURN/WITHDRAW。 */
  public List<FlowActionResult> actReceiving(String tenantId, FlowActionRequest req) {
    return actForStage(tenantId, req, FlowStatus.RECEIVING);
  }

  /** M03.F02.I05/I06/I07 任务分配阶段 act — 允许 SUBMIT/RETURN/WITHDRAW。 */
  public List<FlowActionResult> actAssigning(String tenantId, FlowActionRequest req) {
    return actForStage(tenantId, req, FlowStatus.TASK_ASSIGNMENT);
  }

  /** M03.F03.I12/I13/I14 数据录入阶段 act — 允许 SUBMIT/RETURN/WITHDRAW。 */
  public List<FlowActionResult> actDataEntry(String tenantId, FlowActionRequest req) {
    return actForStage(tenantId, req, FlowStatus.DATA_ENTRY);
  }

  /** M03.F05.I07/I08/I09 报告审核阶段 act — 允许 SUBMIT/RETURN。 */
  public List<FlowActionResult> actReview(String tenantId, FlowActionRequest req) {
    return actForStage(tenantId, req, FlowStatus.REVIEW);
  }

  /** M03.F06.I05/I06/I07 报告批准阶段 act — 允许 SUBMIT/RETURN。 */
  public List<FlowActionResult> actApprove(String tenantId, FlowActionRequest req) {
    return actForStage(tenantId, req, FlowStatus.APPROVAL);
  }

  /** M03.F07.I05/I06/I07 报告发放阶段 act — 允许 SUBMIT/RETURN。 */
  public List<FlowActionResult> actIssuance(String tenantId, FlowActionRequest req) {
    return actForStage(tenantId, req, FlowStatus.ISSUANCE);
  }

  /** M03.F08.I05/I06/I07 报告归档阶段 act — 仅允许 SUBMIT（archived 终态无 next/prev，写 history 当 audit）。 */
  public List<FlowActionResult> actArchived(String tenantId, FlowActionRequest req) {
    requireOperator(req);
    List<FlowActionResult> results = new ArrayList<>();
    String operator = req.getOperator();
    String reason = req.getReason();
    for (String id : req.getIds()) {
      try {
        SampleReceiptEntity entity =
            repo.findByTenantIdAndId(tenantId, id)
                .orElseThrow(
                    () -> new java.util.NoSuchElementException("Receipt not found: " + id));
        if (entity.getFlowStatus() != FlowStatus.ARCHIVED) {
          results.add(
              err(id, "Stage mismatch: requires archived but is " + entity.getFlowStatus()));
          continue;
        }
        if (req.getAction() != FlowAction.SUBMIT) {
          results.add(
              err(
                  id,
                  "Action not allowed: archived accepts only submit but got " + req.getAction()));
          continue;
        }
        // 写 history 当 audit，状态保持 archived
        // archived audit 自转移语义 = submit（SSOT db-queries.ts：submit 写 lastSubmittedBy）
        receiptService.transitionTo(
            tenantId,
            id,
            FlowStatus.ARCHIVED,
            FlowStatus.ARCHIVED,
            FlowAction.SUBMIT,
            operator,
            reason != null ? reason : "archived: post-archive audit");
        results.add(ok(id, FlowStatus.ARCHIVED));
      } catch (Exception e) {
        results.add(err(id, e.getMessage()));
      }
    }
    return results;
  }

  // ============================================================
  // 内部：6 个非终态 stage 共用的 act 路由（WITHDRAW 仅 receiving 合法）
  // ============================================================

  private List<FlowActionResult> actForStage(
      String tenantId, FlowActionRequest req, FlowStatus requiredStage) {
    requireOperator(req);
    List<FlowActionResult> results = new ArrayList<>();
    String operator = req.getOperator();
    String reason = req.getReason();
    for (String id : req.getIds()) {
      try {
        SampleReceiptEntity entity =
            repo.findByTenantIdAndId(tenantId, id)
                .orElseThrow(
                    () -> new java.util.NoSuchElementException("Receipt not found: " + id));
        FlowStatus current = entity.getFlowStatus();
        if (current != requiredStage) {
          results.add(err(id, "Stage mismatch: requires " + requiredStage + " but is " + current));
          continue;
        }
        FlowStatus target;
        switch (req.getAction()) {
          case SUBMIT:
            target = SUBMIT_NEXT.get(current);
            break;
          case RETURN:
            target = RETURN_PREV.get(current);
            break;
          case WITHDRAW:
            // WITHDRAW 仅在 RECEIVING 阶段自转移
            target = (current == FlowStatus.RECEIVING) ? FlowStatus.RECEIVING : null;
            break;
          default:
            target = null;
        }
        if (target == null) {
          results.add(err(id, "Invalid transition from " + current + " with " + req.getAction()));
          continue;
        }
        receiptService.transitionTo(
            tenantId, id, current, target, req.getAction(), operator, reason);
        results.add(ok(id, target));
      } catch (Exception e) {
        results.add(err(id, e.getMessage()));
      }
    }
    return results;
  }

  /**
   * 5.75 operator 契约必填边缘对齐（SSOT = lab-nextjs act-route.ts:39-44）：缺失与空串都 400。 null 已由
   * FlowActionRequest @NotNull 在 controller 层 400 拦截；空串在此拦截 → GlobalExceptionHandler 400
   * {code:"BAD_REQUEST", message:"operator is required"}。 校验先于 per-id 循环（整批拒，与 nextjs act-route
   * 顺序一致）。
   */
  private static void requireOperator(FlowActionRequest req) {
    String operator = req.getOperator();
    if (operator == null || operator.isEmpty()) {
      throw new IllegalArgumentException("operator is required");
    }
  }

  private static FlowActionResult ok(String id, FlowStatus to) {
    return new FlowActionResult().id(id).ok(true).flowStatus(to);
  }

  private static FlowActionResult err(String id, String msg) {
    return new FlowActionResult().id(id).ok(false).message(msg);
  }
}
