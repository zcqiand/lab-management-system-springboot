package io.xr.lab.platform.controller;

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
public class CalculationMethodController implements CalculationMethodsApi {

  private final CalculationMethodService service;

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
