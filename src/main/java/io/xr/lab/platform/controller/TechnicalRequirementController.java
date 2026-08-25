package io.xr.lab.platform.controller;

import io.xr.lab.platform.directory.ConfigUserDirectory;
import io.xr.lab.platform.service.TechnicalRequirementService;
import io.xr.lab.shared.api.TechnicalRequirementsApi;
import io.xr.lab.shared.dto.CreateTechnicalRequirementRequest;
import io.xr.lab.shared.dto.RequirementVerificationStatus;
import io.xr.lab.shared.dto.TechnicalRequirement;
import io.xr.lab.shared.dto.UpdateTechnicalRequirementRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * M06.F06 技术要求 controller（B2）。5 端点。tenant-scoped（V012），tenant 从 {@link
 * InspectionCatalogController#currentTenantIdOrDefault()} 取（同模式）。
 */
@RestController
public class TechnicalRequirementController implements TechnicalRequirementsApi {

  private final TechnicalRequirementService service;
  private final ConfigUserDirectory directory;

  public TechnicalRequirementController(
      TechnicalRequirementService service, ConfigUserDirectory directory) {
    this.service = service;
    this.directory = directory;
  }

  @Override
  public ResponseEntity<List<TechnicalRequirement>> technicalRequirementsListTechnicalRequirements(
      String inspectionObjectCode,
      String inspectionParameterCode,
      String judgmentStandardCode,
      RequirementVerificationStatus verificationStatus) {
    return ResponseEntity.ok(
        service.list(
            currentTenantIdOrDefault(),
            inspectionObjectCode,
            inspectionParameterCode,
            judgmentStandardCode,
            verificationStatus));
  }

  @Override
  public ResponseEntity<TechnicalRequirement> technicalRequirementsGetTechnicalRequirement(
      String inspectionObjectCode, String inspectionParameterCode, String judgmentStandardCode) {
    return ResponseEntity.ok(
        service.get(
            currentTenantIdOrDefault(),
            inspectionObjectCode,
            inspectionParameterCode,
            judgmentStandardCode));
  }

  @Override
  public ResponseEntity<TechnicalRequirement> technicalRequirementsCreateTechnicalRequirement(
      CreateTechnicalRequirementRequest createTechnicalRequirementRequest) {
    return ResponseEntity.ok(
        service.create(createTechnicalRequirementRequest, currentTenantIdOrDefault()));
  }

  @Override
  public ResponseEntity<TechnicalRequirement> technicalRequirementsUpdateTechnicalRequirement(
      String inspectionObjectCode,
      String inspectionParameterCode,
      String judgmentStandardCode,
      UpdateTechnicalRequirementRequest updateTechnicalRequirementRequest) {
    return ResponseEntity.ok(
        service.update(
            currentTenantIdOrDefault(),
            inspectionObjectCode,
            inspectionParameterCode,
            judgmentStandardCode,
            updateTechnicalRequirementRequest));
  }

  @Override
  public ResponseEntity<Void> technicalRequirementsDeleteTechnicalRequirement(
      String inspectionObjectCode, String inspectionParameterCode, String judgmentStandardCode) {
    service.delete(
        currentTenantIdOrDefault(),
        inspectionObjectCode,
        inspectionParameterCode,
        judgmentStandardCode);
    return ResponseEntity.noContent().build();
  }

  private String currentTenantIdOrDefault() {
    java.util.Map<String, Object> claims = InspectionCatalogController.currentClaims();
    Object t = claims.get("tenant_id");
    if (t != null && !t.toString().isEmpty()) {
      return t.toString();
    }
    return directory.defaultTenant().getTenantId();
  }
}
