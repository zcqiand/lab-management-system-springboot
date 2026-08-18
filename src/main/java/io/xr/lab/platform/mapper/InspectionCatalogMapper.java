package io.xr.lab.platform.mapper;

import io.xr.lab.platform.entity.InspectionBrandEntity;
import io.xr.lab.platform.entity.InspectionGradeEntity;
import io.xr.lab.platform.entity.InspectionModelEntity;
import io.xr.lab.platform.entity.InspectionSpecEntity;
import io.xr.lab.shared.dto.CreateCatalogEntryRequest;
import io.xr.lab.shared.dto.InspectionBrand;
import io.xr.lab.shared.dto.InspectionGrade;
import io.xr.lab.shared.dto.InspectionModel;
import io.xr.lab.shared.dto.InspectionSpec;
import io.xr.lab.shared.dto.UpdateCatalogEntryRequest;

/**
 * 码表 4 表（M04.F06-F09）的 DTO ↔ Entity 转换。结构一致（Brand/Model/Grade/Spec 同模板），合并在一个文件以减少样板。 Repository
 * 不接触 DTO；Service 持两者。
 */
public final class InspectionCatalogMapper {

  private InspectionCatalogMapper() {}

  // === Brand ===

  public static InspectionBrand toDto(InspectionBrandEntity e) {
    return new InspectionBrand()
        .code(e.getCode())
        .tenantId(e.getTenantId())
        .inspectionObjectCode(e.getInspectionObjectCode())
        .name(e.getName())
        .remark(e.getRemark())
        .sortOrder(e.getSortOrder())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt());
  }

  public static InspectionBrandEntity fromCreate(
      CreateCatalogEntryRequest req, String tenantId, String now) {
    String code = req.getCode();
    String name = req.getName();
    if (code == null || name == null) {
      throw new IllegalArgumentException("code and name are required");
    }
    InspectionBrandEntity e = new InspectionBrandEntity();
    e.setTenantId(tenantId);
    e.setCode(code);
    e.setInspectionObjectCode(req.getInspectionObjectCode());
    e.setName(name);
    e.setRemark(req.getRemark());
    Integer sort = req.getSortOrder();
    e.setSortOrder(sort != null ? sort : 0);
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    return e;
  }

  public static void applyUpdate(
      InspectionBrandEntity e, UpdateCatalogEntryRequest req, String now) {
    if (req.getInspectionObjectCode() != null) {
      e.setInspectionObjectCode(req.getInspectionObjectCode());
    }
    if (req.getName() != null) {
      e.setName(req.getName());
    }
    if (req.getRemark() != null) {
      e.setRemark(req.getRemark());
    }
    if (req.getSortOrder() != null) {
      e.setSortOrder(req.getSortOrder());
    }
    e.setUpdatedAt(now);
  }

  // === Model ===

  public static InspectionModel toDto(InspectionModelEntity e) {
    return new InspectionModel()
        .code(e.getCode())
        .tenantId(e.getTenantId())
        .inspectionObjectCode(e.getInspectionObjectCode())
        .name(e.getName())
        .remark(e.getRemark())
        .sortOrder(e.getSortOrder())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt());
  }

  public static InspectionModelEntity fromCreateModel(
      CreateCatalogEntryRequest req, String tenantId, String now) {
    String code = req.getCode();
    String name = req.getName();
    if (code == null || name == null) {
      throw new IllegalArgumentException("code and name are required");
    }
    InspectionModelEntity e = new InspectionModelEntity();
    e.setTenantId(tenantId);
    e.setCode(code);
    e.setInspectionObjectCode(req.getInspectionObjectCode());
    e.setName(name);
    e.setRemark(req.getRemark());
    Integer sort = req.getSortOrder();
    e.setSortOrder(sort != null ? sort : 0);
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    return e;
  }

  public static void applyUpdateModel(
      InspectionModelEntity e, UpdateCatalogEntryRequest req, String now) {
    if (req.getInspectionObjectCode() != null) {
      e.setInspectionObjectCode(req.getInspectionObjectCode());
    }
    if (req.getName() != null) {
      e.setName(req.getName());
    }
    if (req.getRemark() != null) {
      e.setRemark(req.getRemark());
    }
    if (req.getSortOrder() != null) {
      e.setSortOrder(req.getSortOrder());
    }
    e.setUpdatedAt(now);
  }

  // === Spec ===

  public static InspectionSpec toDto(InspectionSpecEntity e) {
    return new InspectionSpec()
        .code(e.getCode())
        .tenantId(e.getTenantId())
        .inspectionObjectCode(e.getInspectionObjectCode())
        .name(e.getName())
        .remark(e.getRemark())
        .sortOrder(e.getSortOrder())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt());
  }

  public static InspectionSpecEntity fromCreateSpec(
      CreateCatalogEntryRequest req, String tenantId, String now) {
    String code = req.getCode();
    String name = req.getName();
    if (code == null || name == null) {
      throw new IllegalArgumentException("code and name are required");
    }
    InspectionSpecEntity e = new InspectionSpecEntity();
    e.setTenantId(tenantId);
    e.setCode(code);
    e.setInspectionObjectCode(req.getInspectionObjectCode());
    e.setName(name);
    e.setRemark(req.getRemark());
    Integer sort = req.getSortOrder();
    e.setSortOrder(sort != null ? sort : 0);
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    return e;
  }

  public static void applyUpdateSpec(
      InspectionSpecEntity e, UpdateCatalogEntryRequest req, String now) {
    if (req.getInspectionObjectCode() != null) {
      e.setInspectionObjectCode(req.getInspectionObjectCode());
    }
    if (req.getName() != null) {
      e.setName(req.getName());
    }
    if (req.getRemark() != null) {
      e.setRemark(req.getRemark());
    }
    if (req.getSortOrder() != null) {
      e.setSortOrder(req.getSortOrder());
    }
    e.setUpdatedAt(now);
  }

  // === Grade ===

  public static InspectionGrade toDto(InspectionGradeEntity e) {
    return new InspectionGrade()
        .code(e.getCode())
        .tenantId(e.getTenantId())
        .inspectionObjectCode(e.getInspectionObjectCode())
        .name(e.getName())
        .remark(e.getRemark())
        .sortOrder(e.getSortOrder())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt());
  }

  public static InspectionGradeEntity fromCreateGrade(
      CreateCatalogEntryRequest req, String tenantId, String now) {
    String code = req.getCode();
    String name = req.getName();
    if (code == null || name == null) {
      throw new IllegalArgumentException("code and name are required");
    }
    InspectionGradeEntity e = new InspectionGradeEntity();
    e.setTenantId(tenantId);
    e.setCode(code);
    e.setInspectionObjectCode(req.getInspectionObjectCode());
    e.setName(name);
    e.setRemark(req.getRemark());
    Integer sort = req.getSortOrder();
    e.setSortOrder(sort != null ? sort : 0);
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    return e;
  }

  public static void applyUpdateGrade(
      InspectionGradeEntity e, UpdateCatalogEntryRequest req, String now) {
    if (req.getInspectionObjectCode() != null) {
      e.setInspectionObjectCode(req.getInspectionObjectCode());
    }
    if (req.getName() != null) {
      e.setName(req.getName());
    }
    if (req.getRemark() != null) {
      e.setRemark(req.getRemark());
    }
    if (req.getSortOrder() != null) {
      e.setSortOrder(req.getSortOrder());
    }
    e.setUpdatedAt(now);
  }
}
