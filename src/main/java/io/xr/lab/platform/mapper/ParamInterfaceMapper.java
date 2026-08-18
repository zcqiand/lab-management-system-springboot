package io.xr.lab.platform.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.xr.lab.platform.entity.ParamInterfaceEntity;
import io.xr.lab.shared.dto.CreateParamInterfaceRequest;
import io.xr.lab.shared.dto.ParamInterface;
import io.xr.lab.shared.dto.UpdateParamInterfaceRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数界面（M06.F08）DTO ↔ Entity。config 是 {@code Map<String,Object>} 走 jsonb：Entity 端 {@code String} 装
 * Jackson 序列化值，DTO 端直接 Map<String,Object>，mapper 用共享 ObjectMapper 转换。
 */
public final class ParamInterfaceMapper {

  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final TypeReference<Map<String, Object>> CONFIG_MAP = new TypeReference<>() {};

  private ParamInterfaceMapper() {}

  public static ParamInterface toDto(ParamInterfaceEntity e) {
    return new ParamInterface()
        .code(e.getCode())
        .name(e.getName())
        .componentPath(e.getComponentPath())
        .description(e.getDescription())
        .isOfficial(e.getIsOfficial())
        .sortOrder(e.getSortOrder())
        .config(deserialize(e.getConfig()))
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt());
  }

  public static ParamInterfaceEntity fromCreate(CreateParamInterfaceRequest req, String now) {
    if (req.getCode() == null || req.getComponentPath() == null) {
      throw new IllegalArgumentException("code and componentPath are required");
    }
    ParamInterfaceEntity e = new ParamInterfaceEntity();
    e.setCode(req.getCode());
    e.setName(req.getName());
    e.setComponentPath(req.getComponentPath());
    e.setDescription(req.getDescription());
    e.setIsOfficial(req.getIsOfficial());
    Integer sort = req.getSortOrder();
    e.setSortOrder(sort != null ? sort : 0);
    e.setConfig(serialize(req.getConfig()));
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    return e;
  }

  public static void applyUpdate(
      ParamInterfaceEntity e, UpdateParamInterfaceRequest req, String now) {
    if (req.getName() != null) {
      e.setName(req.getName());
    }
    if (req.getComponentPath() != null) {
      e.setComponentPath(req.getComponentPath());
    }
    if (req.getDescription() != null) {
      e.setDescription(req.getDescription());
    }
    if (req.getIsOfficial() != null) {
      e.setIsOfficial(req.getIsOfficial());
    }
    if (req.getSortOrder() != null) {
      e.setSortOrder(req.getSortOrder());
    }
    if (req.getConfig() != null) {
      e.setConfig(serialize(req.getConfig()));
    }
    e.setUpdatedAt(now);
  }

  private static String serialize(Map<String, Object> config) {
    if (config == null) {
      return null;
    }
    try {
      return MAPPER.writeValueAsString(config);
    } catch (Exception ex) {
      throw new IllegalStateException("Failed to serialize param_interface.config", ex);
    }
  }

  private static Map<String, Object> deserialize(String json) {
    if (json == null || json.isBlank()) {
      return new HashMap<>();
    }
    try {
      return MAPPER.readValue(json, CONFIG_MAP);
    } catch (Exception ex) {
      throw new IllegalStateException("Failed to deserialize param_interface.config: " + json, ex);
    }
  }
}
