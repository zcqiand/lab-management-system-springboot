package io.xr.lab.platform.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.xr.lab.platform.service.CalculationMethodService;
import io.xr.lab.shared.api.CalculationMethodsApi;
import io.xr.lab.shared.dto.CalculationMethod;
import io.xr.lab.shared.dto.CreateCalculationMethodRequest;
import io.xr.lab.shared.dto.UpdateCalculationMethodRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** M06.F05 计算方法 controller（B2）。5 端点。平台级字典（无 tenant_id），不读 JWT claims。 */
@RestController
@SuppressFBWarnings(
    value = {"CT_CONSTRUCTOR_THROW", "UUF_UNUSED_FIELD"},
    justification =
        "service 字段在 5 个 @Override 方法里全用上；"
            + "CT_CONSTRUCTOR_THROW 对 final 字段单赋值场景是 SpotBugs 已知误报。")
public class CalculationMethodController implements CalculationMethodsApi {

  private final CalculationMethodService service;

  @SuppressFBWarnings(
      value = "EI_EXPOSE_REP2",
      justification = "Spring DI singleton: 控制器按规范持有 service 的共享 bean 引用。")
  public CalculationMethodController(CalculationMethodService service) {
    this.service = service;
  }

  @Override
  public ResponseEntity<List<CalculationMethod>> calculationMethodsListCalculationMethods(
      String inspectionObjectCode, String inspectionParameterCode) {
    return ResponseEntity.ok(service.list(inspectionObjectCode, inspectionParameterCode));
  }

  @Override
  public ResponseEntity<CalculationMethod> calculationMethodsGetCalculationMethod(
      String inspectionObjectCode, String inspectionParameterCode) {
    return ResponseEntity.ok(service.get(inspectionObjectCode, inspectionParameterCode));
  }

  @Override
  public ResponseEntity<CalculationMethod> calculationMethodsCreateCalculationMethod(
      CreateCalculationMethodRequest createCalculationMethodRequest) {
    return ResponseEntity.ok(service.create(createCalculationMethodRequest));
  }

  @Override
  public ResponseEntity<CalculationMethod> calculationMethodsUpdateCalculationMethod(
      String inspectionObjectCode,
      String inspectionParameterCode,
      UpdateCalculationMethodRequest updateCalculationMethodRequest) {
    return ResponseEntity.ok(
        service.update(
            inspectionObjectCode, inspectionParameterCode, updateCalculationMethodRequest));
  }

  @Override
  public ResponseEntity<Void> calculationMethodsDeleteCalculationMethod(
      String inspectionObjectCode, String inspectionParameterCode) {
    service.delete(inspectionObjectCode, inspectionParameterCode);
    return ResponseEntity.noContent().build();
  }
}
