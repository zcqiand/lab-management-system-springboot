package io.xr.lab.platform.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.xr.lab.platform.entity.InspectionReportNameEntity;
import io.xr.lab.shared.dto.CreateInspectionReportNameRequest;
import io.xr.lab.shared.dto.ExtFieldDef;
import io.xr.lab.shared.dto.InspectionReportName;
import io.xr.lab.shared.dto.UpdateInspectionReportNameRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 报告名称（M06.F07）DTO ↔ Entity。ext_fields 是 {@code List<ExtFieldDef>} 走 jsonb：Entity 端 {@code String}
 * 装 Jackson 序列化值，DTO 端直接 List<ExtFieldDef>，mapper 用共享 ObjectMapper 转换。
 */
public final class InspectionReportNameMapper {

  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final TypeReference<List<ExtFieldDef>> EXT_FIELD_LIST = new TypeReference<>() {};

  private InspectionReportNameMapper() {}

  public static InspectionReportName toDto(InspectionReportNameEntity e) {
    return new InspectionReportName()
        .code(e.getCode())
        .name(e.getName())
        .fullName(e.getFullName())
        .templatePath(e.getTemplatePath())
        .summaryName(e.getSummaryName())
        .extFields(deserialize(e.getExtFields()))
        .description(e.getDescription())
        .sortOrder(e.getSortOrder())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt());
  }

  public static InspectionReportNameEntity fromCreate(
      CreateInspectionReportNameRequest req, String now) {
    if (req.getCode() == null || req.getName() == null) {
      throw new IllegalArgumentException("code and name are required");
    }
    InspectionReportNameEntity e = new InspectionReportNameEntity();
    e.setCode(req.getCode());
    e.setName(req.getName());
    e.setFullName(req.getFullName());
    e.setTemplatePath(req.getTemplatePath());
    e.setSummaryName(req.getSummaryName());
    e.setExtFields(serialize(req.getExtFields()));
    e.setDescription(req.getDescription());
    Integer sort = req.getSortOrder();
    e.setSortOrder(sort != null ? sort : 0);
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    return e;
  }

  public static void applyUpdate(
      InspectionReportNameEntity e, UpdateInspectionReportNameRequest req, String now) {
    if (req.getName() != null) {
      e.setName(req.getName());
    }
    if (req.getFullName() != null) {
      e.setFullName(req.getFullName());
    }
    if (req.getTemplatePath() != null) {
      e.setTemplatePath(req.getTemplatePath());
    }
    if (req.getSummaryName() != null) {
      e.setSummaryName(req.getSummaryName());
    }
    if (req.getExtFields() != null) {
      e.setExtFields(serialize(req.getExtFields()));
    }
    if (req.getDescription() != null) {
      e.setDescription(req.getDescription());
    }
    if (req.getSortOrder() != null) {
      e.setSortOrder(req.getSortOrder());
    }
    e.setUpdatedAt(now);
  }

  private static String serialize(List<ExtFieldDef> extFields) {
    if (extFields == null) {
      return null;
    }
    try {
      return MAPPER.writeValueAsString(extFields);
    } catch (Exception ex) {
      throw new IllegalStateException("Failed to serialize ext_fields", ex);
    }
  }

  private static List<ExtFieldDef> deserialize(String json) {
    if (json == null || json.isBlank()) {
      return new ArrayList<>();
    }
    try {
      return MAPPER.readValue(json, EXT_FIELD_LIST);
    } catch (Exception ex) {
      throw new IllegalStateException("Failed to deserialize ext_fields: " + json, ex);
    }
  }
}
