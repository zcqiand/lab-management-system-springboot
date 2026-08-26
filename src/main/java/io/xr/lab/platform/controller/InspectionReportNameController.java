package io.xr.lab.platform.controller;

import io.xr.lab.platform.service.InspectionJunctionService;
import io.xr.lab.platform.service.InspectionReportNameService;
import io.xr.lab.shared.api.ReportNamesApi;
import io.xr.lab.shared.dto.CreateInspectionReportNameRequest;
import io.xr.lab.shared.dto.InspectionReportName;
import io.xr.lab.shared.dto.ObjectReportNameLink;
import io.xr.lab.shared.dto.ReportNameParameterLink;
import io.xr.lab.shared.dto.ReportNameStandardLink;
import io.xr.lab.shared.dto.ReportNamesListReportNames200Response;
import io.xr.lab.shared.dto.UpdateInspectionReportNameRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * M06.F07 报告名称 controller（B5）。本批只实现 5 CRUD 端点（list/get/create/update/delete）。
 *
 * <p>3 junction link/unlink 端点（object/parameter/standard）等下一批：
 *
 * <ul>
 *   <li>links/object 涉及 M06.F02 objects 实体
 *   <li>links/parameter、links/standard 可在 B6 一并实现
 * </ul>
 */
@RestController
public class InspectionReportNameController implements ReportNamesApi {

  private final InspectionReportNameService service;
  private final InspectionJunctionService junctionService;

  public InspectionReportNameController(
      InspectionReportNameService service, InspectionJunctionService junctionService) {
    this.service = service;
    this.junctionService = junctionService;
  }

  @Override
  public ResponseEntity<ReportNamesListReportNames200Response> reportNamesListReportNames(
      Integer page, Integer pageSize, String keyword) {
    List<InspectionReportName> list = service.list(keyword);
    int effectivePage = page == null ? 1 : page;
    int effectivePageSize = pageSize == null ? list.size() : pageSize;
    return ResponseEntity.ok(
        new ReportNamesListReportNames200Response()
            .items(list)
            .page(effectivePage)
            .pageSize(effectivePageSize)
            .total((long) list.size()));
  }

  @Override
  public ResponseEntity<InspectionReportName> reportNamesGetReportName(String code) {
    return ResponseEntity.ok(service.get(code));
  }

  @Override
  public ResponseEntity<InspectionReportName> reportNamesCreateReportName(
      CreateInspectionReportNameRequest body) {
    return ResponseEntity.ok(service.create(body));
  }

  @Override
  public ResponseEntity<InspectionReportName> reportNamesUpdateReportName(
      String code, UpdateInspectionReportNameRequest body) {
    return ResponseEntity.ok(service.update(code, body));
  }

  @Override
  public ResponseEntity<Void> reportNamesDeleteReportName(String code) {
    service.delete(code);
    return ResponseEntity.noContent().build();
  }

  // === junction link/unlink（B6 落地）===

  @Override
  public ResponseEntity<Void> reportNamesLinkObjectReportName(ObjectReportNameLink body) {
    junctionService.linkObjectReportName(body);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> reportNamesUnlinkObjectReportName(
      io.xr.lab.shared.dto.ReportNamesUnlinkObjectReportNameRequest body) {
    junctionService.unlinkObjectReportName(
        body.getInspectionObjectCode(), body.getReportNameCode());
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> reportNamesLinkReportNameParameter(ReportNameParameterLink body) {
    junctionService.linkReportNameParameter(body);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> reportNamesUnlinkReportNameParameter(
      io.xr.lab.shared.dto.ReportNamesUnlinkReportNameParameterRequest body) {
    junctionService.unlinkReportNameParameter(
        body.getReportNameCode(), body.getInspectionParameterCode());
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> reportNamesLinkReportNameStandard(ReportNameStandardLink body) {
    junctionService.linkReportNameStandard(body);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> reportNamesUnlinkReportNameStandard(
      io.xr.lab.shared.dto.ReportNamesUnlinkReportNameStandardRequest body) {
    junctionService.unlinkReportNameStandard(
        body.getReportNameCode(), body.getInspectionStandardCode(), body.getRole());
    return ResponseEntity.noContent().build();
  }

  // === junction GET（Page<T>，shared 契约补齐）===

  @Override
  public ResponseEntity<io.xr.lab.shared.dto.ReportNamesListObjectReportNameLinks200Response>
      reportNamesListObjectReportNameLinks(String inspectionObjectCode, String reportNameCode) {
    List<ObjectReportNameLink> list =
        junctionService.listObjectReportNameLinks(inspectionObjectCode, reportNameCode);
    return ResponseEntity.ok(
        new io.xr.lab.shared.dto.ReportNamesListObjectReportNameLinks200Response()
            .items(list)
            .page(1)
            .pageSize(list.size())
            .total((long) list.size()));
  }

  @Override
  public ResponseEntity<io.xr.lab.shared.dto.ReportNamesListReportNameStandardLinks200Response>
      reportNamesListReportNameStandardLinks(
          String reportNameCode, io.xr.lab.shared.dto.InspectionStandardRole role) {
    List<ReportNameStandardLink> list =
        junctionService.listReportNameStandardLinks(reportNameCode, role);
    return ResponseEntity.ok(
        new io.xr.lab.shared.dto.ReportNamesListReportNameStandardLinks200Response()
            .items(list)
            .page(1)
            .pageSize(list.size())
            .total((long) list.size()));
  }

  @Override
  public ResponseEntity<io.xr.lab.shared.dto.ReportNamesListReportNameParameterLinks200Response>
      reportNamesListReportNameParameterLinks(
          String reportNameCode, String inspectionParameterCode) {
    List<ReportNameParameterLink> list =
        junctionService.listReportNameParameterLinks(reportNameCode, inspectionParameterCode);
    return ResponseEntity.ok(
        new io.xr.lab.shared.dto.ReportNamesListReportNameParameterLinks200Response()
            .items(list)
            .page(1)
            .pageSize(list.size())
            .total((long) list.size()));
  }
}
