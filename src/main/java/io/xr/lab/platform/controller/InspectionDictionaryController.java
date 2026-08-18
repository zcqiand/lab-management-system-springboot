package io.xr.lab.platform.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.xr.lab.platform.service.InspectionDictionaryService;
import io.xr.lab.shared.api.InspectionDictionaryApi;
import io.xr.lab.shared.dto.CreateInspectionObjectRequest;
import io.xr.lab.shared.dto.CreateInspectionParameterRequest;
import io.xr.lab.shared.dto.CreateInspectionSpecialtyRequest;
import io.xr.lab.shared.dto.CreateInspectionStandardRequest;
import io.xr.lab.shared.dto.InspectionObject;
import io.xr.lab.shared.dto.InspectionParameter;
import io.xr.lab.shared.dto.InspectionParameterSourceType;
import io.xr.lab.shared.dto.InspectionSpecialty;
import io.xr.lab.shared.dto.InspectionStandard;
import io.xr.lab.shared.dto.InspectionStandardStatus;
import io.xr.lab.shared.dto.ObjectParameterLink;
import io.xr.lab.shared.dto.ObjectStandardLink;
import io.xr.lab.shared.dto.SpecialtyObjectLink;
import io.xr.lab.shared.dto.StandardParameterLink;
import io.xr.lab.shared.dto.UpdateInspectionObjectRequest;
import io.xr.lab.shared.dto.UpdateInspectionParameterRequest;
import io.xr.lab.shared.dto.UpdateInspectionSpecialtyRequest;
import io.xr.lab.shared.dto.UpdateInspectionStandardRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * M06 字典 controller（B5）。本批只实现 3 实体的 12 CRUD 端点：specialty/parameter/standard 各 4
 * 个（list/create/update/delete）。检测项目（M06.F02）+ 4 个 junction link/unlink 等下一批（需 Object 主表）。
 */
@RestController
public class InspectionDictionaryController implements InspectionDictionaryApi {

  private final InspectionDictionaryService service;

  @SuppressFBWarnings(
      value = "EI_EXPOSE_REP2",
      justification = "Spring DI singleton: 控制器按规范持有 service 的共享 bean 引用。")
  public InspectionDictionaryController(InspectionDictionaryService service) {
    this.service = service;
  }

  // === M06.F01 Specialty ===

  @Override
  public ResponseEntity<List<InspectionSpecialty>> inspectionDictionaryListSpecialties(
      String keyword) {
    return ResponseEntity.ok(service.listSpecialties(keyword));
  }

  @Override
  public ResponseEntity<InspectionSpecialty> inspectionDictionaryCreateSpecialty(
      CreateInspectionSpecialtyRequest body) {
    return ResponseEntity.ok(service.createSpecialty(body));
  }

  @Override
  public ResponseEntity<InspectionSpecialty> inspectionDictionaryUpdateSpecialty(
      String code, UpdateInspectionSpecialtyRequest body) {
    return ResponseEntity.ok(service.updateSpecialty(code, body));
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryDeleteSpecialty(String code) {
    service.deleteSpecialty(code);
    return ResponseEntity.noContent().build();
  }

  // === M06.F03 Parameter ===

  @Override
  public ResponseEntity<List<InspectionParameter>> inspectionDictionaryListParameters(
      String keyword, InspectionParameterSourceType sourceType) {
    return ResponseEntity.ok(service.listParameters(keyword, sourceType));
  }

  @Override
  public ResponseEntity<InspectionParameter> inspectionDictionaryCreateParameter(
      CreateInspectionParameterRequest body) {
    return ResponseEntity.ok(service.createParameter(body));
  }

  @Override
  public ResponseEntity<InspectionParameter> inspectionDictionaryUpdateParameter(
      String code, UpdateInspectionParameterRequest body) {
    return ResponseEntity.ok(service.updateParameter(code, body));
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryDeleteParameter(String code) {
    service.deleteParameter(code);
    return ResponseEntity.noContent().build();
  }

  // === M06.F04 Standard ===

  @Override
  public ResponseEntity<List<InspectionStandard>> inspectionDictionaryListStandards(
      String keyword, InspectionStandardStatus status) {
    return ResponseEntity.ok(service.listStandards(keyword, status));
  }

  @Override
  public ResponseEntity<InspectionStandard> inspectionDictionaryCreateStandard(
      CreateInspectionStandardRequest body) {
    return ResponseEntity.ok(service.createStandard(body));
  }

  @Override
  public ResponseEntity<InspectionStandard> inspectionDictionaryUpdateStandard(
      String code, UpdateInspectionStandardRequest body) {
    return ResponseEntity.ok(service.updateStandard(code, body));
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryDeleteStandard(String code) {
    service.deleteStandard(code);
    return ResponseEntity.noContent().build();
  }

  // === M06.F02 Object + 4 junction 端点（下一批实现）===

  @Override
  public ResponseEntity<List<InspectionObject>> inspectionDictionaryListObjects(
      String inspectionSpecialtyCode, String keyword) {
    throw new UnsupportedOperationException("M06.F02 objects endpoint deferred to next batch (B6)");
  }

  @Override
  public ResponseEntity<InspectionObject> inspectionDictionaryCreateObject(
      CreateInspectionObjectRequest body) {
    throw new UnsupportedOperationException("M06.F02 objects endpoint deferred to next batch (B6)");
  }

  @Override
  public ResponseEntity<InspectionObject> inspectionDictionaryUpdateObject(
      String code, UpdateInspectionObjectRequest body) {
    throw new UnsupportedOperationException("M06.F02 objects endpoint deferred to next batch (B6)");
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryDeleteObject(String code) {
    throw new UnsupportedOperationException("M06.F02 objects endpoint deferred to next batch (B6)");
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryLinkSpecialtyObject(SpecialtyObjectLink body) {
    throw new UnsupportedOperationException("M06 junction link deferred to B6 (needs Object)");
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryUnlinkSpecialtyObject(SpecialtyObjectLink body) {
    throw new UnsupportedOperationException("M06 junction unlink deferred to B6");
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryLinkObjectParameter(ObjectParameterLink body) {
    throw new UnsupportedOperationException("M06 junction link deferred to B6 (needs Object)");
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryUnlinkObjectParameter(
      io.xr.lab.shared.dto.InspectionDictionaryUnlinkObjectParameterRequest body) {
    throw new UnsupportedOperationException("M06 junction unlink deferred to B6");
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryLinkObjectStandard(ObjectStandardLink body) {
    throw new UnsupportedOperationException("M06 junction link deferred to B6 (needs Object)");
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryUnlinkObjectStandard(
      io.xr.lab.shared.dto.InspectionDictionaryUnlinkObjectStandardRequest body) {
    throw new UnsupportedOperationException("M06 junction unlink deferred to B6");
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryLinkStandardParameter(
      StandardParameterLink body) {
    throw new UnsupportedOperationException("M06 junction link deferred to B6 (needs Object)");
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryUnlinkStandardParameter(
      StandardParameterLink body) {
    throw new UnsupportedOperationException("M06 junction unlink deferred to B6");
  }
}
