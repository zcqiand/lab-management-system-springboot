package io.xr.lab.platform.service;

import io.xr.lab.platform.entity.InspectionReportNameEntity;
import io.xr.lab.platform.mapper.InspectionReportNameMapper;
import io.xr.lab.platform.repository.InspectionReportNameRepository;
import io.xr.lab.shared.dto.CreateInspectionReportNameRequest;
import io.xr.lab.shared.dto.InspectionReportName;
import io.xr.lab.shared.dto.UpdateInspectionReportNameRequest;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

/** M06.F07 报告名称（5 子项 CRUD）。平台级字典（无 tenant_id，per V012 备注）。 */
@Service
public class InspectionReportNameService {

  private final InspectionReportNameRepository repo;

  public InspectionReportNameService(InspectionReportNameRepository repo) {
    this.repo = repo;
  }

  public List<InspectionReportName> list(String keyword) {
    return repo.filter(n(keyword)).stream().map(InspectionReportNameMapper::toDto).toList();
  }

  public InspectionReportName get(String code) {
    return InspectionReportNameMapper.toDto(
        repo.findById(code)
            .orElseThrow(() -> new NoSuchElementException("ReportName not found: " + code)));
  }

  public InspectionReportName create(CreateInspectionReportNameRequest req) {
    String now = nowIso();
    return InspectionReportNameMapper.toDto(
        repo.save(InspectionReportNameMapper.fromCreate(req, now)));
  }

  public InspectionReportName update(String code, UpdateInspectionReportNameRequest req) {
    InspectionReportNameEntity entity =
        repo.findById(code)
            .orElseThrow(() -> new NoSuchElementException("ReportName not found: " + code));
    InspectionReportNameMapper.applyUpdate(entity, req, nowIso());
    return InspectionReportNameMapper.toDto(repo.save(entity));
  }

  public void delete(String code) {
    if (!repo.existsById(code)) {
      throw new NoSuchElementException("ReportName not found: " + code);
    }
    repo.deleteById(code);
  }

  private static String nowIso() {
    return java.time.OffsetDateTime.now(java.time.ZoneOffset.UTC)
        .format(java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }

  private static String n(String s) {
    return s == null ? "" : s;
  }
}
