package io.xr.lab.platform.controller;

import io.xr.lab.platform.service.InspectionDictionaryService;
import io.xr.lab.platform.service.InspectionJunctionService;
import io.xr.lab.shared.api.InspectionDictionaryApi;
import io.xr.lab.shared.dto.CreateInspectionObjectRequest;
import io.xr.lab.shared.dto.CreateInspectionParameterRequest;
import io.xr.lab.shared.dto.CreateInspectionSpecialtyRequest;
import io.xr.lab.shared.dto.CreateInspectionStandardRequest;
import io.xr.lab.shared.dto.InspectionDictionaryListObjectParameterLinks200Response;
import io.xr.lab.shared.dto.InspectionDictionaryListObjectStandardLinks200Response;
import io.xr.lab.shared.dto.InspectionDictionaryListObjects200Response;
import io.xr.lab.shared.dto.InspectionDictionaryListParameters200Response;
import io.xr.lab.shared.dto.InspectionDictionaryListSpecialties200Response;
import io.xr.lab.shared.dto.InspectionDictionaryListSpecialtyObjectLinks200Response;
import io.xr.lab.shared.dto.InspectionDictionaryListStandardParameterLinks200Response;
import io.xr.lab.shared.dto.InspectionDictionaryListStandards200Response;
import io.xr.lab.shared.dto.InspectionObject;
import io.xr.lab.shared.dto.InspectionParameter;
import io.xr.lab.shared.dto.InspectionParameterSourceType;
import io.xr.lab.shared.dto.InspectionSpecialty;
import io.xr.lab.shared.dto.InspectionStandard;
import io.xr.lab.shared.dto.InspectionStandardRole;
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
 * M06 字典 controller（B5/B6/B7）。本批实现 4 实体的 12 CRUD 端点 + 4 junction link/unlink + 4 junction list（B7 补
 * GET 列表，与 React/Vue 镜像客户端对齐）。
 *
 * <p>所有 list GET 端点返 OpenAPI `Page<T>` 包裹（items / page / pageSize / total）。 page / pageSize 当前不被
 * service 用于分页过滤（数据量小），但按 TypeSpec 真源 `Page<T>` 接受并回写，便于后续切真分页时只改 service 不改 controller。
 */
@RestController
public class InspectionDictionaryController implements InspectionDictionaryApi {

  private final InspectionDictionaryService service;
  private final InspectionJunctionService junctionService;

  public InspectionDictionaryController(
      InspectionDictionaryService service, InspectionJunctionService junctionService) {
    this.service = service;
    this.junctionService = junctionService;
  }

  // === M06.F01 Specialty ===

  @Override
  public ResponseEntity<InspectionDictionaryListSpecialties200Response>
      inspectionDictionaryListSpecialties(Integer page, Integer pageSize, String keyword) {
    List<InspectionSpecialty> list = service.listSpecialties(keyword);
    int effectivePage = page == null ? 1 : page;
    int effectivePageSize = pageSize == null ? list.size() : pageSize;
    return ResponseEntity.ok(
        new InspectionDictionaryListSpecialties200Response()
            .items(list)
            .page(effectivePage)
            .pageSize(effectivePageSize)
            .total((long) list.size()));
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
  public ResponseEntity<InspectionDictionaryListParameters200Response>
      inspectionDictionaryListParameters(
          Integer page,
          Integer pageSize,
          String keyword,
          InspectionParameterSourceType sourceType) {
    List<InspectionParameter> list = service.listParameters(keyword, sourceType);
    int effectivePage = page == null ? 1 : page;
    int effectivePageSize = pageSize == null ? list.size() : pageSize;
    return ResponseEntity.ok(
        new InspectionDictionaryListParameters200Response()
            .items(list)
            .page(effectivePage)
            .pageSize(effectivePageSize)
            .total((long) list.size()));
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
  public ResponseEntity<InspectionDictionaryListStandards200Response>
      inspectionDictionaryListStandards(
          Integer page, Integer pageSize, String keyword, InspectionStandardStatus status) {
    List<InspectionStandard> list = service.listStandards(keyword, status);
    int effectivePage = page == null ? 1 : page;
    int effectivePageSize = pageSize == null ? list.size() : pageSize;
    return ResponseEntity.ok(
        new InspectionDictionaryListStandards200Response()
            .items(list)
            .page(effectivePage)
            .pageSize(effectivePageSize)
            .total((long) list.size()));
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

  // === M06.F02 Object + 4 junction 端点（B6 落地）===

  @Override
  public ResponseEntity<InspectionDictionaryListObjects200Response> inspectionDictionaryListObjects(
      Integer page, Integer pageSize, String inspectionSpecialtyCode, String keyword) {
    List<InspectionObject> list = service.listObjects(inspectionSpecialtyCode, keyword);
    int effectivePage = page == null ? 1 : page;
    int effectivePageSize = pageSize == null ? list.size() : pageSize;
    return ResponseEntity.ok(
        new InspectionDictionaryListObjects200Response()
            .items(list)
            .page(effectivePage)
            .pageSize(effectivePageSize)
            .total((long) list.size()));
  }

  @Override
  public ResponseEntity<InspectionObject> inspectionDictionaryCreateObject(
      CreateInspectionObjectRequest body) {
    return ResponseEntity.ok(service.createObject(body));
  }

  @Override
  public ResponseEntity<InspectionObject> inspectionDictionaryUpdateObject(
      String code, UpdateInspectionObjectRequest body) {
    return ResponseEntity.ok(service.updateObject(code, body));
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryDeleteObject(String code) {
    service.deleteObject(code);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryLinkSpecialtyObject(SpecialtyObjectLink body) {
    junctionService.linkSpecialtyObject(body);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryUnlinkSpecialtyObject(SpecialtyObjectLink body) {
    junctionService.unlinkSpecialtyObject(body);
    return ResponseEntity.noContent().build();
  }

  // === M06 junction list (B7 补 GET 列表，与 React/Vue 对齐) ===

  @Override
  public ResponseEntity<InspectionDictionaryListSpecialtyObjectLinks200Response>
      inspectionDictionaryListSpecialtyObjectLinks(String inspectionSpecialtyCode) {
    List<SpecialtyObjectLink> list =
        junctionService.listSpecialtyObjectLinks(inspectionSpecialtyCode);
    return ResponseEntity.ok(
        new InspectionDictionaryListSpecialtyObjectLinks200Response()
            .items(list)
            .page(1)
            .pageSize(list.size())
            .total((long) list.size()));
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryLinkObjectParameter(ObjectParameterLink body) {
    junctionService.linkObjectParameter(body);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryUnlinkObjectParameter(
      io.xr.lab.shared.dto.InspectionDictionaryUnlinkObjectParameterRequest body) {
    junctionService.unlinkObjectParameter(
        body.getInspectionObjectCode(), body.getInspectionParameterCode());
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<InspectionDictionaryListObjectParameterLinks200Response>
      inspectionDictionaryListObjectParameterLinks(
          String inspectionObjectCode, String inspectionParameterCode) {
    List<ObjectParameterLink> list =
        junctionService.listObjectParameterLinks(inspectionObjectCode, inspectionParameterCode);
    return ResponseEntity.ok(
        new InspectionDictionaryListObjectParameterLinks200Response()
            .items(list)
            .page(1)
            .pageSize(list.size())
            .total((long) list.size()));
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryLinkObjectStandard(ObjectStandardLink body) {
    junctionService.linkObjectStandard(body);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryUnlinkObjectStandard(
      io.xr.lab.shared.dto.InspectionDictionaryUnlinkObjectStandardRequest body) {
    junctionService.unlinkObjectStandard(
        body.getInspectionObjectCode(), body.getInspectionStandardCode(), body.getRole());
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<InspectionDictionaryListObjectStandardLinks200Response>
      inspectionDictionaryListObjectStandardLinks(
          String inspectionObjectCode, InspectionStandardRole role) {
    List<ObjectStandardLink> list =
        junctionService.listObjectStandardLinks(inspectionObjectCode, role);
    return ResponseEntity.ok(
        new InspectionDictionaryListObjectStandardLinks200Response()
            .items(list)
            .page(1)
            .pageSize(list.size())
            .total((long) list.size()));
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryLinkStandardParameter(
      StandardParameterLink body) {
    junctionService.linkStandardParameter(body);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> inspectionDictionaryUnlinkStandardParameter(
      StandardParameterLink body) {
    junctionService.unlinkStandardParameter(body);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<InspectionDictionaryListStandardParameterLinks200Response>
      inspectionDictionaryListStandardParameterLinks(
          String inspectionStandardCode, String inspectionParameterCode) {
    List<StandardParameterLink> list =
        junctionService.listStandardParameterLinks(inspectionStandardCode, inspectionParameterCode);
    return ResponseEntity.ok(
        new InspectionDictionaryListStandardParameterLinks200Response()
            .items(list)
            .page(1)
            .pageSize(list.size())
            .total((long) list.size()));
  }
}
