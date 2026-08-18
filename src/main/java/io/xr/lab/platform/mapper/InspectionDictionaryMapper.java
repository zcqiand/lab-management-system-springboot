package io.xr.lab.platform.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.xr.lab.platform.entity.InspectionObjectEntity;
import io.xr.lab.platform.entity.InspectionParameterEntity;
import io.xr.lab.platform.entity.InspectionSpecialtyEntity;
import io.xr.lab.platform.entity.InspectionStandardEntity;
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
import io.xr.lab.shared.dto.UpdateInspectionObjectRequest;
import io.xr.lab.shared.dto.UpdateInspectionParameterRequest;
import io.xr.lab.shared.dto.UpdateInspectionSpecialtyRequest;
import io.xr.lab.shared.dto.UpdateInspectionStandardRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * M06 字典 3 实体（specialty/parameter/standard）DTO ↔ Entity。结构差异较大不复用，统一在一个文件按实体切分。
 *
 * <p>parameter.aliases 是 {@code List<String>} 走 jsonb：Entity 端用 {@code String} 装 Jackson 序列化值， 写库走
 * {@code @JdbcTypeCode(SqlTypes.JSON)}；DTO 端直接 List<String>，mapper 用共享 ObjectMapper 转换。
 */
public final class InspectionDictionaryMapper {

  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final TypeReference<List<String>> STRING_LIST = new TypeReference<>() {};

  private InspectionDictionaryMapper() {}

  // === Specialty (M06.F01) ===

  public static InspectionSpecialty toDto(InspectionSpecialtyEntity e) {
    return new InspectionSpecialty()
        .code(e.getCode())
        .officialNo(e.getOfficialNo())
        .name(e.getName())
        .isOfficial(e.getIsOfficial())
        .enabled(e.getEnabled())
        .sortOrder(e.getSortOrder())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt());
  }

  public static InspectionSpecialtyEntity fromCreateSpecialty(
      CreateInspectionSpecialtyRequest req, String now) {
    if (req.getCode() == null || req.getOfficialNo() == null || req.getName() == null) {
      throw new IllegalArgumentException("code, officialNo and name are required");
    }
    InspectionSpecialtyEntity e = new InspectionSpecialtyEntity();
    e.setCode(req.getCode());
    e.setOfficialNo(req.getOfficialNo());
    e.setName(req.getName());
    Boolean isOfficial = req.getIsOfficial();
    e.setIsOfficial(isOfficial != null ? isOfficial : Boolean.TRUE);
    Boolean enabled = req.getEnabled();
    e.setEnabled(enabled != null ? enabled : Boolean.TRUE);
    Integer sort = req.getSortOrder();
    e.setSortOrder(sort != null ? sort : 0);
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    return e;
  }

  public static void applyUpdateSpecialty(
      InspectionSpecialtyEntity e, UpdateInspectionSpecialtyRequest req, String now) {
    if (req.getOfficialNo() != null) {
      e.setOfficialNo(req.getOfficialNo());
    }
    if (req.getName() != null) {
      e.setName(req.getName());
    }
    if (req.getIsOfficial() != null) {
      e.setIsOfficial(req.getIsOfficial());
    }
    if (req.getEnabled() != null) {
      e.setEnabled(req.getEnabled());
    }
    if (req.getSortOrder() != null) {
      e.setSortOrder(req.getSortOrder());
    }
    e.setUpdatedAt(now);
  }

  // === Object (M06.F02) ===

  public static InspectionObject toDto(InspectionObjectEntity e) {
    return new InspectionObject()
        .code(e.getCode())
        .inspectionSpecialtyCode(e.getInspectionSpecialtyCode())
        .sourceProjectNo(e.getSourceProjectNo())
        .sourceProjectName(e.getSourceProjectName())
        .name(e.getName())
        .isOptionalForQualification(e.getIsOptionalForQualification())
        .isOfficial(e.getIsOfficial())
        .enabled(e.getEnabled())
        .sortOrder(e.getSortOrder())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt());
  }

  public static InspectionObjectEntity fromCreateObject(
      CreateInspectionObjectRequest req, String now) {
    if (req.getCode() == null
        || req.getInspectionSpecialtyCode() == null
        || req.getSourceProjectNo() == null
        || req.getSourceProjectName() == null
        || req.getName() == null) {
      throw new IllegalArgumentException(
          "code, inspectionSpecialtyCode, sourceProjectNo, sourceProjectName and name are required");
    }
    InspectionObjectEntity e = new InspectionObjectEntity();
    e.setCode(req.getCode());
    e.setInspectionSpecialtyCode(req.getInspectionSpecialtyCode());
    e.setSourceProjectNo(req.getSourceProjectNo());
    e.setSourceProjectName(req.getSourceProjectName());
    e.setName(req.getName());
    Boolean opt = req.getIsOptionalForQualification();
    e.setIsOptionalForQualification(opt != null ? opt : Boolean.FALSE);
    Boolean off = req.getIsOfficial();
    e.setIsOfficial(off != null ? off : Boolean.TRUE);
    Boolean en = req.getEnabled();
    e.setEnabled(en != null ? en : Boolean.TRUE);
    Integer sort = req.getSortOrder();
    e.setSortOrder(sort != null ? sort : 0);
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    return e;
  }

  public static void applyUpdateObject(
      InspectionObjectEntity e, UpdateInspectionObjectRequest req, String now) {
    if (req.getInspectionSpecialtyCode() != null) {
      e.setInspectionSpecialtyCode(req.getInspectionSpecialtyCode());
    }
    if (req.getSourceProjectNo() != null) {
      e.setSourceProjectNo(req.getSourceProjectNo());
    }
    if (req.getSourceProjectName() != null) {
      e.setSourceProjectName(req.getSourceProjectName());
    }
    if (req.getName() != null) {
      e.setName(req.getName());
    }
    if (req.getIsOptionalForQualification() != null) {
      e.setIsOptionalForQualification(req.getIsOptionalForQualification());
    }
    if (req.getIsOfficial() != null) {
      e.setIsOfficial(req.getIsOfficial());
    }
    if (req.getEnabled() != null) {
      e.setEnabled(req.getEnabled());
    }
    if (req.getSortOrder() != null) {
      e.setSortOrder(req.getSortOrder());
    }
    e.setUpdatedAt(now);
  }

  // === Parameter (M06.F03) ===

  public static InspectionParameter toDto(InspectionParameterEntity e) {
    return new InspectionParameter()
        .code(e.getCode())
        .name(e.getName())
        .rawName(e.getRawName())
        .canonicalName(e.getCanonicalName())
        .methodText(e.getMethodText())
        .aliases(deserializeAliases(e.getAliases()))
        .unit(e.getUnit())
        .sourceType(e.getSourceType())
        .sortOrder(e.getSortOrder())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt());
  }

  public static InspectionParameterEntity fromCreateParameter(
      CreateInspectionParameterRequest req, String now) {
    if (req.getCode() == null || req.getName() == null) {
      throw new IllegalArgumentException("code and name are required");
    }
    InspectionParameterEntity e = new InspectionParameterEntity();
    e.setCode(req.getCode());
    e.setName(req.getName());
    // rawName/canonicalName 默认 = name（前端不传时回退）
    e.setRawName(req.getRawName() != null ? req.getRawName() : req.getName());
    e.setCanonicalName(req.getCanonicalName() != null ? req.getCanonicalName() : req.getName());
    e.setMethodText(req.getMethodText());
    List<String> aliases = req.getAliases();
    e.setAliases(serializeAliases(aliases == null ? List.of() : aliases));
    e.setUnit(req.getUnit());
    InspectionParameterSourceType src = req.getSourceType();
    e.setSourceType(src != null ? src : InspectionParameterSourceType.OFFICIAL);
    Integer sort = req.getSortOrder();
    e.setSortOrder(sort != null ? sort : 0);
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    return e;
  }

  public static void applyUpdateParameter(
      InspectionParameterEntity e, UpdateInspectionParameterRequest req, String now) {
    if (req.getName() != null) {
      e.setName(req.getName());
    }
    if (req.getRawName() != null) {
      e.setRawName(req.getRawName());
    }
    if (req.getCanonicalName() != null) {
      e.setCanonicalName(req.getCanonicalName());
    }
    if (req.getMethodText() != null) {
      e.setMethodText(req.getMethodText());
    }
    if (req.getAliases() != null) {
      e.setAliases(serializeAliases(req.getAliases()));
    }
    if (req.getUnit() != null) {
      e.setUnit(req.getUnit());
    }
    if (req.getSourceType() != null) {
      e.setSourceType(req.getSourceType());
    }
    if (req.getSortOrder() != null) {
      e.setSortOrder(req.getSortOrder());
    }
    e.setUpdatedAt(now);
  }

  // === Standard (M06.F04) ===

  public static InspectionStandard toDto(InspectionStandardEntity e) {
    return new InspectionStandard()
        .code(e.getCode())
        .name(e.getName())
        .version(e.getVersion())
        .status(e.getStatus())
        .sourceDocumentId(e.getSourceDocumentId())
        .sourceHash(e.getSourceHash())
        .sortOrder(e.getSortOrder())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt());
  }

  public static InspectionStandardEntity fromCreateStandard(
      CreateInspectionStandardRequest req, String now) {
    if (req.getCode() == null || req.getName() == null) {
      throw new IllegalArgumentException("code and name are required");
    }
    InspectionStandardEntity e = new InspectionStandardEntity();
    e.setCode(req.getCode());
    e.setName(req.getName());
    e.setVersion(req.getVersion());
    InspectionStandardStatus status = req.getStatus();
    e.setStatus(status != null ? status : InspectionStandardStatus.ACTIVE);
    e.setSourceDocumentId(req.getSourceDocumentId());
    e.setSourceHash(req.getSourceHash());
    Integer sort = req.getSortOrder();
    e.setSortOrder(sort != null ? sort : 0);
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    return e;
  }

  public static void applyUpdateStandard(
      InspectionStandardEntity e, UpdateInspectionStandardRequest req, String now) {
    if (req.getName() != null) {
      e.setName(req.getName());
    }
    if (req.getVersion() != null) {
      e.setVersion(req.getVersion());
    }
    if (req.getStatus() != null) {
      e.setStatus(req.getStatus());
    }
    if (req.getSourceDocumentId() != null) {
      e.setSourceDocumentId(req.getSourceDocumentId());
    }
    if (req.getSourceHash() != null) {
      e.setSourceHash(req.getSourceHash());
    }
    if (req.getSortOrder() != null) {
      e.setSortOrder(req.getSortOrder());
    }
    e.setUpdatedAt(now);
  }

  // === aliases jsonb 序列化辅助 ===

  private static String serializeAliases(List<String> aliases) {
    try {
      return MAPPER.writeValueAsString(aliases);
    } catch (Exception ex) {
      throw new IllegalStateException("Failed to serialize parameter.aliases", ex);
    }
  }

  private static List<String> deserializeAliases(String json) {
    if (json == null || json.isBlank()) {
      return new ArrayList<>();
    }
    try {
      return MAPPER.readValue(json, STRING_LIST);
    } catch (Exception ex) {
      throw new IllegalStateException("Failed to deserialize parameter.aliases: " + json, ex);
    }
  }
}
