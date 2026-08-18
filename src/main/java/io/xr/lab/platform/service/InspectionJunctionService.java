package io.xr.lab.platform.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.xr.lab.platform.entity.InspectionObjectParameterEntity;
import io.xr.lab.platform.entity.InspectionObjectReportNameEntity;
import io.xr.lab.platform.entity.InspectionObjectStandardEntity;
import io.xr.lab.platform.entity.InspectionReportNameParameterEntity;
import io.xr.lab.platform.entity.InspectionReportNameStandardEntity;
import io.xr.lab.platform.entity.InspectionSpecialtyObjectEntity;
import io.xr.lab.platform.entity.InspectionStandardParameterEntity;
import io.xr.lab.platform.entity.ParamInterfaceLinkEntity;
import io.xr.lab.platform.entity.enums.ObjectParameterKey;
import io.xr.lab.platform.entity.enums.ObjectReportNameKey;
import io.xr.lab.platform.entity.enums.ObjectStandardKey;
import io.xr.lab.platform.entity.enums.ParamInterfaceLinkKey;
import io.xr.lab.platform.entity.enums.ReportNameParameterKey;
import io.xr.lab.platform.entity.enums.ReportNameStandardKey;
import io.xr.lab.platform.entity.enums.SpecialtyObjectKey;
import io.xr.lab.platform.entity.enums.StandardParameterKey;
import io.xr.lab.platform.repository.InspectionObjectParameterRepository;
import io.xr.lab.platform.repository.InspectionObjectReportNameRepository;
import io.xr.lab.platform.repository.InspectionObjectStandardRepository;
import io.xr.lab.platform.repository.InspectionReportNameParameterRepository;
import io.xr.lab.platform.repository.InspectionReportNameStandardRepository;
import io.xr.lab.platform.repository.InspectionSpecialtyObjectRepository;
import io.xr.lab.platform.repository.InspectionStandardParameterRepository;
import io.xr.lab.platform.repository.ParamInterfaceLinkRepository;
import io.xr.lab.shared.dto.InspectionStandardRole;
import io.xr.lab.shared.dto.ObjectParameterLink;
import io.xr.lab.shared.dto.ObjectReportNameLink;
import io.xr.lab.shared.dto.ObjectStandardLink;
import io.xr.lab.shared.dto.ParamInterfaceLink;
import io.xr.lab.shared.dto.QualificationLevel;
import io.xr.lab.shared.dto.ReportNameParameterLink;
import io.xr.lab.shared.dto.ReportNameStandardLink;
import io.xr.lab.shared.dto.SpecialtyObjectLink;
import io.xr.lab.shared.dto.StandardParameterLink;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

/**
 * M06 检测能力字典 7 个 junction 表的 link/unlink 操作（B6）。
 *
 * <p>每个 junction 表 1 link + 1 unlink 端点 = 14 端点。其中 param_interface_links config 是 jsonb
 * 走 @JdbcTypeCode(SqlTypes.JSON)，写库序列化 String。
 *
 * <p>link 全部走 save()（upsert 语义：同 PK 重复时覆盖），unlink 用 existsById + deleteById 兜底 404。
 */
@Service
public class InspectionJunctionService {

  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final TypeReference<Map<String, Object>> CONFIG_MAP = new TypeReference<>() {};

  private final InspectionSpecialtyObjectRepository specialtyObjectRepo;
  private final InspectionObjectParameterRepository objectParameterRepo;
  private final InspectionObjectStandardRepository objectStandardRepo;
  private final InspectionStandardParameterRepository standardParameterRepo;
  private final InspectionObjectReportNameRepository objectReportNameRepo;
  private final InspectionReportNameStandardRepository reportNameStandardRepo;
  private final InspectionReportNameParameterRepository reportNameParameterRepo;
  private final ParamInterfaceLinkRepository paramInterfaceLinkRepo;

  public InspectionJunctionService(
      InspectionSpecialtyObjectRepository specialtyObjectRepo,
      InspectionObjectParameterRepository objectParameterRepo,
      InspectionObjectStandardRepository objectStandardRepo,
      InspectionStandardParameterRepository standardParameterRepo,
      InspectionObjectReportNameRepository objectReportNameRepo,
      InspectionReportNameStandardRepository reportNameStandardRepo,
      InspectionReportNameParameterRepository reportNameParameterRepo,
      ParamInterfaceLinkRepository paramInterfaceLinkRepo) {
    this.specialtyObjectRepo = specialtyObjectRepo;
    this.objectParameterRepo = objectParameterRepo;
    this.objectStandardRepo = objectStandardRepo;
    this.standardParameterRepo = standardParameterRepo;
    this.objectReportNameRepo = objectReportNameRepo;
    this.reportNameStandardRepo = reportNameStandardRepo;
    this.reportNameParameterRepo = reportNameParameterRepo;
    this.paramInterfaceLinkRepo = paramInterfaceLinkRepo;
  }

  // === M06.F01/F02 specialty ↔ object ===

  public void linkSpecialtyObject(SpecialtyObjectLink body) {
    requireNonBlank(body.getInspectionSpecialtyCode(), body.getInspectionObjectCode());
    String now = nowIso();
    InspectionSpecialtyObjectEntity e = new InspectionSpecialtyObjectEntity();
    e.setInspectionSpecialtyCode(body.getInspectionSpecialtyCode());
    e.setInspectionObjectCode(body.getInspectionObjectCode());
    e.setRemark(body.getRemark());
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    specialtyObjectRepo.save(e);
  }

  public void unlinkSpecialtyObject(SpecialtyObjectLink body) {
    SpecialtyObjectKey key =
        new SpecialtyObjectKey(body.getInspectionSpecialtyCode(), body.getInspectionObjectCode());
    if (!specialtyObjectRepo.existsById(key)) {
      throw new NoSuchElementException(
          "SpecialtyObject link not found: "
              + key.getInspectionSpecialtyCode()
              + "/"
              + key.getInspectionObjectCode());
    }
    specialtyObjectRepo.deleteById(key);
  }

  // === M06.F02/F03 object ↔ parameter ===

  public void linkObjectParameter(ObjectParameterLink body) {
    requireNonBlank(body.getInspectionObjectCode(), body.getInspectionParameterCode());
    String now = nowIso();
    InspectionObjectParameterEntity e = new InspectionObjectParameterEntity();
    e.setInspectionObjectCode(body.getInspectionObjectCode());
    e.setInspectionParameterCode(body.getInspectionParameterCode());
    QualificationLevel ql = body.getQualificationLevel();
    e.setQualificationLevel(ql != null ? ql : QualificationLevel.QUALIFIED);
    e.setSourcePage(body.getSourcePage());
    e.setRemark(body.getRemark());
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    objectParameterRepo.save(e);
  }

  public void unlinkObjectParameter(String inspectionObjectCode, String inspectionParameterCode) {
    ObjectParameterKey key = new ObjectParameterKey(inspectionObjectCode, inspectionParameterCode);
    if (!objectParameterRepo.existsById(key)) {
      throw new NoSuchElementException(
          "ObjectParameter link not found: "
              + inspectionObjectCode
              + "/"
              + inspectionParameterCode);
    }
    objectParameterRepo.deleteById(key);
  }

  // === M06.F02/F04 object ↔ standard (role) ===

  public void linkObjectStandard(ObjectStandardLink body) {
    requireNonBlank(
        body.getInspectionObjectCode(), body.getInspectionStandardCode(), body.getRole());
    String now = nowIso();
    InspectionObjectStandardEntity e = new InspectionObjectStandardEntity();
    e.setInspectionObjectCode(body.getInspectionObjectCode());
    e.setInspectionStandardCode(body.getInspectionStandardCode());
    e.setRole(body.getRole());
    e.setRemark(body.getRemark());
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    objectStandardRepo.save(e);
  }

  public void unlinkObjectStandard(
      String inspectionObjectCode, String inspectionStandardCode, InspectionStandardRole role) {
    ObjectStandardKey key =
        new ObjectStandardKey(inspectionObjectCode, inspectionStandardCode, role);
    if (!objectStandardRepo.existsById(key)) {
      throw new NoSuchElementException(
          "ObjectStandard link not found: "
              + inspectionObjectCode
              + "/"
              + inspectionStandardCode
              + "/"
              + role);
    }
    objectStandardRepo.deleteById(key);
  }

  // === M06.F03/F04 standard ↔ parameter ===

  public void linkStandardParameter(StandardParameterLink body) {
    requireNonBlank(body.getInspectionStandardCode(), body.getInspectionParameterCode());
    String now = nowIso();
    InspectionStandardParameterEntity e = new InspectionStandardParameterEntity();
    e.setInspectionStandardCode(body.getInspectionStandardCode());
    e.setInspectionParameterCode(body.getInspectionParameterCode());
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    standardParameterRepo.save(e);
  }

  public void unlinkStandardParameter(StandardParameterLink body) {
    StandardParameterKey key =
        new StandardParameterKey(
            body.getInspectionStandardCode(), body.getInspectionParameterCode());
    if (!standardParameterRepo.existsById(key)) {
      throw new NoSuchElementException(
          "StandardParameter link not found: "
              + key.getInspectionStandardCode()
              + "/"
              + key.getInspectionParameterCode());
    }
    standardParameterRepo.deleteById(key);
  }

  // === M06.F02/F07 object ↔ report-name ===

  public void linkObjectReportName(ObjectReportNameLink body) {
    requireNonBlank(body.getInspectionObjectCode(), body.getReportNameCode());
    String now = nowIso();
    InspectionObjectReportNameEntity e = new InspectionObjectReportNameEntity();
    e.setInspectionObjectCode(body.getInspectionObjectCode());
    e.setReportNameCode(body.getReportNameCode());
    e.setRemark(body.getRemark());
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    objectReportNameRepo.save(e);
  }

  public void unlinkObjectReportName(String inspectionObjectCode, String reportNameCode) {
    ObjectReportNameKey key = new ObjectReportNameKey(inspectionObjectCode, reportNameCode);
    if (!objectReportNameRepo.existsById(key)) {
      throw new NoSuchElementException(
          "ObjectReportName link not found: " + inspectionObjectCode + "/" + reportNameCode);
    }
    objectReportNameRepo.deleteById(key);
  }

  // === M06.F07/F04 report-name ↔ standard (role) ===

  public void linkReportNameStandard(ReportNameStandardLink body) {
    requireNonBlank(body.getReportNameCode(), body.getInspectionStandardCode(), body.getRole());
    String now = nowIso();
    InspectionReportNameStandardEntity e = new InspectionReportNameStandardEntity();
    e.setReportNameCode(body.getReportNameCode());
    e.setInspectionStandardCode(body.getInspectionStandardCode());
    e.setRole(body.getRole());
    e.setRemark(body.getRemark());
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    reportNameStandardRepo.save(e);
  }

  public void unlinkReportNameStandard(
      String reportNameCode, String inspectionStandardCode, InspectionStandardRole role) {
    ReportNameStandardKey key =
        new ReportNameStandardKey(reportNameCode, inspectionStandardCode, role);
    if (!reportNameStandardRepo.existsById(key)) {
      throw new NoSuchElementException(
          "ReportNameStandard link not found: "
              + reportNameCode
              + "/"
              + inspectionStandardCode
              + "/"
              + role);
    }
    reportNameStandardRepo.deleteById(key);
  }

  // === M06.F07/F03 report-name ↔ parameter ===

  public void linkReportNameParameter(ReportNameParameterLink body) {
    requireNonBlank(body.getReportNameCode(), body.getInspectionParameterCode());
    String now = nowIso();
    InspectionReportNameParameterEntity e = new InspectionReportNameParameterEntity();
    e.setReportNameCode(body.getReportNameCode());
    e.setInspectionParameterCode(body.getInspectionParameterCode());
    e.setRemark(body.getRemark());
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    reportNameParameterRepo.save(e);
  }

  public void unlinkReportNameParameter(String reportNameCode, String inspectionParameterCode) {
    ReportNameParameterKey key =
        new ReportNameParameterKey(reportNameCode, inspectionParameterCode);
    if (!reportNameParameterRepo.existsById(key)) {
      throw new NoSuchElementException(
          "ReportNameParameter link not found: " + reportNameCode + "/" + inspectionParameterCode);
    }
    reportNameParameterRepo.deleteById(key);
  }

  // === M06.F08 parameter ↔ interface (config jsonb) ===

  public void linkParamInterface(ParamInterfaceLink body) {
    requireNonBlank(body.getInspectionParameterCode(), body.getParamInterfaceCode());
    String now = nowIso();
    ParamInterfaceLinkEntity e = new ParamInterfaceLinkEntity();
    e.setInspectionParameterCode(body.getInspectionParameterCode());
    e.setParamInterfaceCode(body.getParamInterfaceCode());
    e.setReportNameCode(body.getReportNameCode());
    e.setConfig(serializeConfig(body.getConfig()));
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    paramInterfaceLinkRepo.save(e);
  }

  public void unlinkParamInterface(String inspectionParameterCode, String paramInterfaceCode) {
    ParamInterfaceLinkKey key =
        new ParamInterfaceLinkKey(inspectionParameterCode, paramInterfaceCode);
    if (!paramInterfaceLinkRepo.existsById(key)) {
      throw new NoSuchElementException(
          "ParamInterface link not found: " + inspectionParameterCode + "/" + paramInterfaceCode);
    }
    paramInterfaceLinkRepo.deleteById(key);
  }

  // === helpers ===

  private static void requireNonBlank(Object... fields) {
    for (Object f : fields) {
      if (f == null) {
        throw new IllegalArgumentException("junction link fields must be non-null");
      }
      if (f instanceof String s && s.isBlank()) {
        throw new IllegalArgumentException("junction link fields must be non-blank");
      }
    }
  }

  private static String nowIso() {
    return java.time.OffsetDateTime.now(java.time.ZoneOffset.UTC)
        .format(java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }

  private static String serializeConfig(Map<String, Object> config) {
    if (config == null) {
      return null;
    }
    try {
      return MAPPER.writeValueAsString(config);
    } catch (Exception ex) {
      throw new IllegalStateException("Failed to serialize param_interface_links.config", ex);
    }
  }
}
