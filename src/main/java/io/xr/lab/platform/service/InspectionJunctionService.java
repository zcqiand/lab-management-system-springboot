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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
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

  /** B7 — 按专项 code 过滤专项↔项目 junction。null 返回全量。 */
  public List<SpecialtyObjectLink> listSpecialtyObjectLinks(String inspectionSpecialtyCode) {
    List<InspectionSpecialtyObjectEntity> all = new ArrayList<>(specialtyObjectRepo.findAll());
    if (inspectionSpecialtyCode == null || inspectionSpecialtyCode.isBlank()) {
      return all.stream().map(this::toSpecialtyObjectLink).collect(Collectors.toList());
    }
    return all.stream()
        .filter(e -> inspectionSpecialtyCode.equals(e.getInspectionSpecialtyCode()))
        .map(this::toSpecialtyObjectLink)
        .collect(Collectors.toList());
  }

  private SpecialtyObjectLink toSpecialtyObjectLink(InspectionSpecialtyObjectEntity e) {
    SpecialtyObjectLink dto = new SpecialtyObjectLink();
    dto.setInspectionSpecialtyCode(e.getInspectionSpecialtyCode());
    dto.setInspectionObjectCode(e.getInspectionObjectCode());
    dto.setRemark(e.getRemark());
    return dto;
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

  /** B7 — 项目↔参数 junction list（按 objectCode 与/或 parameterCode 过滤）。 */
  public List<ObjectParameterLink> listObjectParameterLinks(
      String inspectionObjectCode, String inspectionParameterCode) {
    List<InspectionObjectParameterEntity> all = new ArrayList<>(objectParameterRepo.findAll());
    return all.stream()
        .filter(
            e ->
                (inspectionObjectCode == null
                        || inspectionObjectCode.isBlank()
                        || inspectionObjectCode.equals(e.getInspectionObjectCode()))
                    && (inspectionParameterCode == null
                        || inspectionParameterCode.isBlank()
                        || inspectionParameterCode.equals(e.getInspectionParameterCode())))
        .map(this::toObjectParameterLink)
        .collect(Collectors.toList());
  }

  private ObjectParameterLink toObjectParameterLink(InspectionObjectParameterEntity e) {
    ObjectParameterLink dto = new ObjectParameterLink();
    dto.setInspectionObjectCode(e.getInspectionObjectCode());
    dto.setInspectionParameterCode(e.getInspectionParameterCode());
    dto.setQualificationLevel(e.getQualificationLevel());
    dto.setSourcePage(e.getSourcePage());
    dto.setRemark(e.getRemark());
    return dto;
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

  /** B7 — 项目↔标准 junction list（按 objectCode 与/或 role 过滤）。 */
  public List<ObjectStandardLink> listObjectStandardLinks(
      String inspectionObjectCode, InspectionStandardRole role) {
    List<InspectionObjectStandardEntity> all = new ArrayList<>(objectStandardRepo.findAll());
    return all.stream()
        .filter(
            e ->
                (inspectionObjectCode == null
                        || inspectionObjectCode.isBlank()
                        || inspectionObjectCode.equals(e.getInspectionObjectCode()))
                    && (role == null || role.equals(e.getRole())))
        .map(this::toObjectStandardLink)
        .collect(Collectors.toList());
  }

  private ObjectStandardLink toObjectStandardLink(InspectionObjectStandardEntity e) {
    ObjectStandardLink dto = new ObjectStandardLink();
    dto.setInspectionObjectCode(e.getInspectionObjectCode());
    dto.setInspectionStandardCode(e.getInspectionStandardCode());
    dto.setRole(e.getRole());
    dto.setRemark(e.getRemark());
    return dto;
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

  /** B7 — 标准↔参数 junction list（按 standardCode 与/或 parameterCode 过滤）。 */
  public List<StandardParameterLink> listStandardParameterLinks(
      String inspectionStandardCode, String inspectionParameterCode) {
    List<InspectionStandardParameterEntity> all = new ArrayList<>(standardParameterRepo.findAll());
    return all.stream()
        .filter(
            e ->
                (inspectionStandardCode == null
                        || inspectionStandardCode.isBlank()
                        || inspectionStandardCode.equals(e.getInspectionStandardCode()))
                    && (inspectionParameterCode == null
                        || inspectionParameterCode.isBlank()
                        || inspectionParameterCode.equals(e.getInspectionParameterCode())))
        .map(this::toStandardParameterLink)
        .collect(Collectors.toList());
  }

  private StandardParameterLink toStandardParameterLink(InspectionStandardParameterEntity e) {
    StandardParameterLink dto = new StandardParameterLink();
    dto.setInspectionStandardCode(e.getInspectionStandardCode());
    dto.setInspectionParameterCode(e.getInspectionParameterCode());
    return dto;
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

  public List<ObjectReportNameLink> listObjectReportNameLinks(
      String inspectionObjectCode, String reportNameCode) {
    return new ArrayList<>(objectReportNameRepo.findAll())
        .stream()
            .filter(
                e ->
                    (inspectionObjectCode == null || inspectionObjectCode.isBlank())
                        || inspectionObjectCode.equals(e.getInspectionObjectCode()))
            .filter(
                e ->
                    (reportNameCode == null || reportNameCode.isBlank())
                        || reportNameCode.equals(e.getReportNameCode()))
            .map(this::toObjectReportNameLink)
            .collect(Collectors.toList());
  }

  private ObjectReportNameLink toObjectReportNameLink(InspectionObjectReportNameEntity e) {
    ObjectReportNameLink dto = new ObjectReportNameLink();
    dto.setInspectionObjectCode(e.getInspectionObjectCode());
    dto.setReportNameCode(e.getReportNameCode());
    dto.setRemark(e.getRemark());
    return dto;
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

  public List<ReportNameStandardLink> listReportNameStandardLinks(
      String reportNameCode, InspectionStandardRole role) {
    return new ArrayList<>(reportNameStandardRepo.findAll())
        .stream()
            .filter(
                e ->
                    (reportNameCode == null || reportNameCode.isBlank())
                        || reportNameCode.equals(e.getReportNameCode()))
            .filter(e -> role == null || role.equals(e.getRole()))
            .map(this::toReportNameStandardLink)
            .collect(Collectors.toList());
  }

  private ReportNameStandardLink toReportNameStandardLink(InspectionReportNameStandardEntity e) {
    ReportNameStandardLink dto = new ReportNameStandardLink();
    dto.setReportNameCode(e.getReportNameCode());
    dto.setInspectionStandardCode(e.getInspectionStandardCode());
    dto.setRole(e.getRole());
    dto.setRemark(e.getRemark());
    return dto;
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

  public List<ReportNameParameterLink> listReportNameParameterLinks(
      String reportNameCode, String inspectionParameterCode) {
    return new ArrayList<>(reportNameParameterRepo.findAll())
        .stream()
            .filter(
                e ->
                    (reportNameCode == null || reportNameCode.isBlank())
                        || reportNameCode.equals(e.getReportNameCode()))
            .filter(
                e ->
                    (inspectionParameterCode == null || inspectionParameterCode.isBlank())
                        || inspectionParameterCode.equals(e.getInspectionParameterCode()))
            .map(this::toReportNameParameterLink)
            .collect(Collectors.toList());
  }

  private ReportNameParameterLink toReportNameParameterLink(InspectionReportNameParameterEntity e) {
    ReportNameParameterLink dto = new ReportNameParameterLink();
    dto.setReportNameCode(e.getReportNameCode());
    dto.setInspectionParameterCode(e.getInspectionParameterCode());
    dto.setRemark(e.getRemark());
    return dto;
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

  public List<ParamInterfaceLink> listParamInterfaceLinks(
      String inspectionParameterCode, String paramInterfaceCode) {
    return new ArrayList<>(paramInterfaceLinkRepo.findAll())
        .stream()
            .filter(
                e ->
                    (inspectionParameterCode == null || inspectionParameterCode.isBlank())
                        || inspectionParameterCode.equals(e.getInspectionParameterCode()))
            .filter(
                e ->
                    (paramInterfaceCode == null || paramInterfaceCode.isBlank())
                        || paramInterfaceCode.equals(e.getParamInterfaceCode()))
            .map(this::toParamInterfaceLink)
            .collect(Collectors.toList());
  }

  private ParamInterfaceLink toParamInterfaceLink(ParamInterfaceLinkEntity e) {
    ParamInterfaceLink dto = new ParamInterfaceLink();
    dto.setInspectionParameterCode(e.getInspectionParameterCode());
    dto.setParamInterfaceCode(e.getParamInterfaceCode());
    dto.setReportNameCode(e.getReportNameCode());
    dto.setConfig(deserializeConfig(e.getConfig()));
    return dto;
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

  private static Map<String, Object> deserializeConfig(String json) {
    if (json == null || json.isBlank()) {
      return null;
    }
    try {
      return MAPPER.readValue(json, new TypeReference<Map<String, Object>>() {});
    } catch (Exception ex) {
      throw new IllegalStateException("Failed to deserialize param_interface_links.config", ex);
    }
  }
}
