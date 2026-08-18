package io.xr.lab.platform.service;

import io.xr.lab.platform.entity.InspectionBrandEntity;
import io.xr.lab.platform.entity.InspectionGradeEntity;
import io.xr.lab.platform.entity.InspectionModelEntity;
import io.xr.lab.platform.entity.InspectionSpecEntity;
import io.xr.lab.platform.mapper.InspectionCatalogMapper;
import io.xr.lab.platform.repository.InspectionBrandRepository;
import io.xr.lab.platform.repository.InspectionGradeRepository;
import io.xr.lab.platform.repository.InspectionModelRepository;
import io.xr.lab.platform.repository.InspectionSpecRepository;
import io.xr.lab.shared.dto.CreateCatalogEntryRequest;
import io.xr.lab.shared.dto.InspectionBrand;
import io.xr.lab.shared.dto.InspectionGrade;
import io.xr.lab.shared.dto.InspectionModel;
import io.xr.lab.shared.dto.InspectionSpec;
import io.xr.lab.shared.dto.UpdateCatalogEntryRequest;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

/**
 * M04.F06/07/08/09 码表服务。4 表结构一致（list 按 tenant_id + 检测专项 + 关键字过滤），合并在一个 service。
 *
 * <p>tenant_id 从 controller 注入（B1 AuthController#currentClaims 模式），dev fallback 到 {@link
 * io.xr.lab.platform.directory.ConfigUserDirectory} 默认租户 TENANT-001。
 */
@Service
public class CatalogService {

  private final InspectionBrandRepository brandRepo;
  private final InspectionModelRepository modelRepo;
  private final InspectionSpecRepository specRepo;
  private final InspectionGradeRepository gradeRepo;

  public CatalogService(
      InspectionBrandRepository brandRepo,
      InspectionModelRepository modelRepo,
      InspectionSpecRepository specRepo,
      InspectionGradeRepository gradeRepo) {
    this.brandRepo = brandRepo;
    this.modelRepo = modelRepo;
    this.specRepo = specRepo;
    this.gradeRepo = gradeRepo;
  }

  // === M04.F06 型号码表 ===

  public List<InspectionModel> listModels(
      String tenantId, String inspectionObjectCode, String keyword) {
    return modelRepo.filter(tenantId, n(inspectionObjectCode), n(keyword)).stream()
        .map(InspectionCatalogMapper::toDto)
        .toList();
  }

  public InspectionModel createModel(String tenantId, CreateCatalogEntryRequest req) {
    String now = nowIso();
    InspectionModelEntity entity = InspectionCatalogMapper.fromCreateModel(req, tenantId, now);
    return InspectionCatalogMapper.toDto(modelRepo.save(entity));
  }

  public InspectionModel updateModel(String tenantId, String code, UpdateCatalogEntryRequest req) {
    InspectionModelEntity entity =
        modelRepo
            .findByTenantIdAndCode(tenantId, code)
            .orElseThrow(() -> new NoSuchElementException("Model not found: " + code));
    InspectionCatalogMapper.applyUpdateModel(entity, req, nowIso());
    return InspectionCatalogMapper.toDto(modelRepo.save(entity));
  }

  public void deleteModel(String tenantId, String code) {
    InspectionModelEntity entity =
        modelRepo
            .findByTenantIdAndCode(tenantId, code)
            .orElseThrow(() -> new NoSuchElementException("Model not found: " + code));
    modelRepo.delete(entity);
  }

  // === M04.F07 规格码表 ===

  public List<InspectionSpec> listSpecs(
      String tenantId, String inspectionObjectCode, String keyword) {
    return specRepo.filter(tenantId, n(inspectionObjectCode), n(keyword)).stream()
        .map(InspectionCatalogMapper::toDto)
        .toList();
  }

  public InspectionSpec createSpec(String tenantId, CreateCatalogEntryRequest req) {
    String now = nowIso();
    InspectionSpecEntity entity = InspectionCatalogMapper.fromCreateSpec(req, tenantId, now);
    return InspectionCatalogMapper.toDto(specRepo.save(entity));
  }

  public InspectionSpec updateSpec(String tenantId, String code, UpdateCatalogEntryRequest req) {
    InspectionSpecEntity entity =
        specRepo
            .findByTenantIdAndCode(tenantId, code)
            .orElseThrow(() -> new NoSuchElementException("Spec not found: " + code));
    InspectionCatalogMapper.applyUpdateSpec(entity, req, nowIso());
    return InspectionCatalogMapper.toDto(specRepo.save(entity));
  }

  public void deleteSpec(String tenantId, String code) {
    InspectionSpecEntity entity =
        specRepo
            .findByTenantIdAndCode(tenantId, code)
            .orElseThrow(() -> new NoSuchElementException("Spec not found: " + code));
    specRepo.delete(entity);
  }

  // === M04.F08 等级码表 ===

  public List<InspectionGrade> listGrades(
      String tenantId, String inspectionObjectCode, String keyword) {
    return gradeRepo.filter(tenantId, n(inspectionObjectCode), n(keyword)).stream()
        .map(InspectionCatalogMapper::toDto)
        .toList();
  }

  public InspectionGrade createGrade(String tenantId, CreateCatalogEntryRequest req) {
    String now = nowIso();
    InspectionGradeEntity entity = InspectionCatalogMapper.fromCreateGrade(req, tenantId, now);
    return InspectionCatalogMapper.toDto(gradeRepo.save(entity));
  }

  public InspectionGrade updateGrade(String tenantId, String code, UpdateCatalogEntryRequest req) {
    InspectionGradeEntity entity =
        gradeRepo
            .findByTenantIdAndCode(tenantId, code)
            .orElseThrow(() -> new NoSuchElementException("Grade not found: " + code));
    InspectionCatalogMapper.applyUpdateGrade(entity, req, nowIso());
    return InspectionCatalogMapper.toDto(gradeRepo.save(entity));
  }

  public void deleteGrade(String tenantId, String code) {
    InspectionGradeEntity entity =
        gradeRepo
            .findByTenantIdAndCode(tenantId, code)
            .orElseThrow(() -> new NoSuchElementException("Grade not found: " + code));
    gradeRepo.delete(entity);
  }

  // === M04.F09 牌号码表 ===

  public List<InspectionBrand> listBrands(
      String tenantId, String inspectionObjectCode, String keyword) {
    return brandRepo.filter(tenantId, n(inspectionObjectCode), n(keyword)).stream()
        .map(InspectionCatalogMapper::toDto)
        .toList();
  }

  public InspectionBrand createBrand(String tenantId, CreateCatalogEntryRequest req) {
    String now = nowIso();
    InspectionBrandEntity entity = InspectionCatalogMapper.fromCreate(req, tenantId, now);
    return InspectionCatalogMapper.toDto(brandRepo.save(entity));
  }

  public InspectionBrand updateBrand(String tenantId, String code, UpdateCatalogEntryRequest req) {
    InspectionBrandEntity entity =
        brandRepo
            .findByTenantIdAndCode(tenantId, code)
            .orElseThrow(() -> new NoSuchElementException("Brand not found: " + code));
    InspectionCatalogMapper.applyUpdate(entity, req, nowIso());
    return InspectionCatalogMapper.toDto(brandRepo.save(entity));
  }

  public void deleteBrand(String tenantId, String code) {
    InspectionBrandEntity entity =
        brandRepo
            .findByTenantIdAndCode(tenantId, code)
            .orElseThrow(() -> new NoSuchElementException("Brand not found: " + code));
    brandRepo.delete(entity);
  }

  private static String nowIso() {
    return java.time.OffsetDateTime.now(java.time.ZoneOffset.UTC)
        .format(java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }

  /**
   * Convert null filter param to empty string so JPQL parameter type is bound as text (not bytea).
   */
  private static String n(String s) {
    return s == null ? "" : s;
  }
}
