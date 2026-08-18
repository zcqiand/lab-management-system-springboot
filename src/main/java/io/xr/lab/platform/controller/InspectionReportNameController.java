package io.xr.lab.platform.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.xr.lab.platform.service.InspectionReportNameService;
import io.xr.lab.shared.api.ReportNamesApi;
import io.xr.lab.shared.dto.CreateInspectionReportNameRequest;
import io.xr.lab.shared.dto.InspectionReportName;
import io.xr.lab.shared.dto.ObjectReportNameLink;
import io.xr.lab.shared.dto.ReportNameParameterLink;
import io.xr.lab.shared.dto.ReportNameStandardLink;
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

  @SuppressFBWarnings(
      value = "EI_EXPOSE_REP2",
      justification = "Spring DI singleton: 控制器按规范持有 service 的共享 bean 引用。")
  public InspectionReportNameController(InspectionReportNameService service) {
    this.service = service;
  }

  @Override
  public ResponseEntity<List<InspectionReportName>> reportNamesListReportNames(String keyword) {
    return ResponseEntity.ok(service.list(keyword));
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

  // === junction link/unlink（M06 — 下一批）===

  @Override
  public ResponseEntity<Void> reportNamesLinkObjectReportName(ObjectReportNameLink body) {
    throw new UnsupportedOperationException(
        "ReportName link/object endpoint deferred to next batch (needs Object entity)");
  }

  @Override
  public ResponseEntity<Void> reportNamesUnlinkObjectReportName(
      io.xr.lab.shared.dto.ReportNamesUnlinkObjectReportNameRequest body) {
    throw new UnsupportedOperationException("ReportName unlink/object deferred to next batch");
  }

  @Override
  public ResponseEntity<Void> reportNamesLinkReportNameParameter(ReportNameParameterLink body) {
    throw new UnsupportedOperationException(
        "ReportName link/parameter endpoint deferred to next batch");
  }

  @Override
  public ResponseEntity<Void> reportNamesUnlinkReportNameParameter(
      io.xr.lab.shared.dto.ReportNamesUnlinkReportNameParameterRequest body) {
    throw new UnsupportedOperationException("ReportName unlink/parameter deferred to next batch");
  }

  @Override
  public ResponseEntity<Void> reportNamesLinkReportNameStandard(ReportNameStandardLink body) {
    throw new UnsupportedOperationException(
        "ReportName link/standard endpoint deferred to next batch");
  }

  @Override
  public ResponseEntity<Void> reportNamesUnlinkReportNameStandard(
      io.xr.lab.shared.dto.ReportNamesUnlinkReportNameStandardRequest body) {
    throw new UnsupportedOperationException("ReportName unlink/standard deferred to next batch");
  }
}
