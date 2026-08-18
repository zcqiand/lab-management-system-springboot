package io.xr.lab.platform.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.xr.lab.platform.service.CalculationRuleService;
import io.xr.lab.shared.api.CalculationRulesApi;
import io.xr.lab.shared.dto.CalculationRule;
import io.xr.lab.shared.dto.CreateCalculationRuleRequest;
import io.xr.lab.shared.dto.UpdateCalculationRuleRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** M06.F05 计算规则 controller（B2）。5 端点。平台级字典（无 tenant_id），不读 JWT claims。 */
@RestController
public class CalculationRuleController implements CalculationRulesApi {

  private final CalculationRuleService service;

  @SuppressFBWarnings(
      value = "EI_EXPOSE_REP2",
      justification = "Spring DI singleton: 控制器按规范持有 service 的共享 bean 引用。")
  public CalculationRuleController(CalculationRuleService service) {
    this.service = service;
  }

  @Override
  public ResponseEntity<List<CalculationRule>> calculationRulesListCalculationRules(
      String inspectionObjectCode, String inspectionParameterCode) {
    return ResponseEntity.ok(service.list(inspectionObjectCode, inspectionParameterCode));
  }

  @Override
  public ResponseEntity<CalculationRule> calculationRulesGetCalculationRule(
      String inspectionObjectCode, String inspectionParameterCode) {
    return ResponseEntity.ok(service.get(inspectionObjectCode, inspectionParameterCode));
  }

  @Override
  public ResponseEntity<CalculationRule> calculationRulesCreateCalculationRule(
      CreateCalculationRuleRequest createCalculationRuleRequest) {
    return ResponseEntity.ok(service.create(createCalculationRuleRequest));
  }

  @Override
  public ResponseEntity<CalculationRule> calculationRulesUpdateCalculationRule(
      String inspectionObjectCode,
      String inspectionParameterCode,
      UpdateCalculationRuleRequest updateCalculationRuleRequest) {
    return ResponseEntity.ok(
        service.update(
            inspectionObjectCode, inspectionParameterCode, updateCalculationRuleRequest));
  }

  @Override
  public ResponseEntity<Void> calculationRulesDeleteCalculationRule(
      String inspectionObjectCode, String inspectionParameterCode) {
    service.delete(inspectionObjectCode, inspectionParameterCode);
    return ResponseEntity.noContent().build();
  }
}
