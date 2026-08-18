package io.xr.lab.platform.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.xr.harness.junit.Fn;
import io.xr.lab.platform.entity.InspectionBrandEntity;
import io.xr.lab.platform.entity.InspectionGradeEntity;
import io.xr.lab.platform.entity.InspectionModelEntity;
import io.xr.lab.platform.entity.InspectionSpecEntity;
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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * M04.F06/07/08/09 码表 16 子项单测（1 个 service 测 16 个 I 级 — 镜像 AuthServiceTest 多子项合并）。
 *
 * <p>Repos 全 Mockito，不依赖 Spring 上下文。语义基准：lab-msw handlers-extra.ts 的 inspectionCatalogHandlers
 * （faker 数据不直接读；以 msw 端点的「200/204/过滤语义」为参考）。
 */
class CatalogServiceTest {

  private static final String TENANT = "TENANT-001";

  private InspectionBrandRepository brandRepo;
  private InspectionModelRepository modelRepo;
  private InspectionSpecRepository specRepo;
  private InspectionGradeRepository gradeRepo;
  private CatalogService service;

  @BeforeEach
  void setUp() {
    brandRepo = org.mockito.Mockito.mock(InspectionBrandRepository.class);
    modelRepo = org.mockito.Mockito.mock(InspectionModelRepository.class);
    specRepo = org.mockito.Mockito.mock(InspectionSpecRepository.class);
    gradeRepo = org.mockito.Mockito.mock(InspectionGradeRepository.class);
    service = new CatalogService(brandRepo, modelRepo, specRepo, gradeRepo);
  }

  // ===== M04.F06 型号码表 =====

  // M04.F06.I01 list

  @Test
  @Fn({"M04.F06.I01"})
  void listModels_returnsMappedDtos() {
    when(modelRepo.filter(TENANT, "OBJ-01", ""))
        .thenReturn(
            List.of(
                entity("M-001", TENANT, "OBJ-01", "Model A"),
                entity("M-002", TENANT, "OBJ-01", "Model B")));
    List<InspectionModel> out = service.listModels(TENANT, "OBJ-01", null);
    assertEquals(2, out.size());
    assertEquals("M-001", out.get(0).getCode());
    assertEquals("Model A", out.get(0).getName());
  }

  // M04.F06.I02 create

  @Test
  @Fn({"M04.F06.I02"})
  void createModel_appliesTenantAndStampsTimestamps() {
    CreateCatalogEntryRequest req =
        new CreateCatalogEntryRequest().code("M-NEW").name("New Model").sortOrder(5);
    when(modelRepo.save(any(InspectionModelEntity.class))).thenAnswer(inv -> inv.getArgument(0));
    InspectionModel out = service.createModel(TENANT, req);
    ArgumentCaptor<InspectionModelEntity> captor =
        ArgumentCaptor.forClass(InspectionModelEntity.class);
    verify(modelRepo).save(captor.capture());
    InspectionModelEntity saved = captor.getValue();
    assertEquals(TENANT, saved.getTenantId());
    assertEquals("M-NEW", saved.getCode());
    assertEquals(5, saved.getSortOrder());
    assertTrue(!saved.getCreatedAt().isEmpty());
    assertEquals(saved.getCreatedAt(), saved.getUpdatedAt());
    assertEquals("M-NEW", out.getCode());
  }

  // M04.F06.I03 update

  @Test
  @Fn({"M04.F06.I03"})
  void updateModel_appliesNewNameAndStampsUpdatedAt() {
    InspectionModelEntity existing = entity("M-001", TENANT, null, "Old");
    when(modelRepo.findByTenantIdAndCode(TENANT, "M-001")).thenReturn(Optional.of(existing));
    when(modelRepo.save(any(InspectionModelEntity.class))).thenAnswer(inv -> inv.getArgument(0));
    InspectionModel out =
        service.updateModel(
            TENANT, "M-001", new UpdateCatalogEntryRequest().name("New Name").sortOrder(9));
    assertEquals("New Name", out.getName());
    assertEquals(9, out.getSortOrder());
    assertTrue(!out.getUpdatedAt().isEmpty());
  }

  // M04.F06.I04 delete

  @Test
  @Fn({"M04.F06.I04"})
  void deleteModel_callsRepoDelete() {
    InspectionModelEntity existing = entity("M-001", TENANT, null, null);
    when(modelRepo.findByTenantIdAndCode(TENANT, "M-001")).thenReturn(Optional.of(existing));
    service.deleteModel(TENANT, "M-001");
    verify(modelRepo, times(1)).delete(existing);
  }

  @Test
  @Fn({"M04.F06.I04"})
  void deleteModel_missing_throws404() {
    when(modelRepo.findByTenantIdAndCode(TENANT, "MISSING")).thenReturn(Optional.empty());
    assertThrows(NoSuchElementException.class, () -> service.deleteModel(TENANT, "MISSING"));
    verify(modelRepo, never()).delete(any());
  }

  // ===== M04.F07 规格码表 =====

  @Test
  @Fn({"M04.F07.I01"})
  void listSpecs_keywordFiltersByName() {
    when(specRepo.filter(TENANT, "", "fire"))
        .thenReturn(List.of(specEntity("S-1", TENANT, "fire-A")));
    List<InspectionSpec> out = service.listSpecs(TENANT, null, "fire");
    assertEquals(1, out.size());
    assertEquals("fire-A", out.get(0).getName());
  }

  @Test
  @Fn({"M04.F07.I02"})
  void createSpec_setsTenantAndDefaults() {
    CreateCatalogEntryRequest req =
        new CreateCatalogEntryRequest().code("S-1").name("Spec1").remark("r");
    when(specRepo.save(any(InspectionSpecEntity.class))).thenAnswer(inv -> inv.getArgument(0));
    InspectionSpec out = service.createSpec(TENANT, req);
    assertEquals(TENANT, out.getTenantId());
    assertEquals(0, out.getSortOrder()); // default
  }

  @Test
  @Fn({"M04.F07.I03"})
  void updateSpec_appliesPartialFields() {
    InspectionSpecEntity existing = specEntity("S-1", TENANT, "Old");
    when(specRepo.findByTenantIdAndCode(TENANT, "S-1")).thenReturn(Optional.of(existing));
    when(specRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    InspectionSpec out =
        service.updateSpec(TENANT, "S-1", new UpdateCatalogEntryRequest().remark("new remark"));
    assertEquals("new remark", out.getRemark());
    assertEquals("Old", out.getName()); // name untouched
  }

  @Test
  @Fn({"M04.F07.I04"})
  void deleteSpec_notFound_throws404() {
    when(specRepo.findByTenantIdAndCode(TENANT, "X")).thenReturn(Optional.empty());
    assertThrows(NoSuchElementException.class, () -> service.deleteSpec(TENANT, "X"));
  }

  // ===== M04.F08 等级码表 =====

  @Test
  @Fn({"M04.F08.I01"})
  void listGrades_returnsMappedList() {
    when(gradeRepo.filter(TENANT, "", "")).thenReturn(List.of(gradeEntity("G-1", TENANT, "A")));
    assertEquals(1, service.listGrades(TENANT, null, null).size());
  }

  @Test
  @Fn({"M04.F08.I02"})
  void createGrade_stampsCreatedAndUpdatedEqual() {
    CreateCatalogEntryRequest req = new CreateCatalogEntryRequest().code("G-1").name("A");
    when(gradeRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    InspectionGrade out = service.createGrade(TENANT, req);
    assertEquals(out.getCreatedAt(), out.getUpdatedAt());
  }

  @Test
  @Fn({"M04.F08.I03"})
  void updateGrade_appliesSortOrder() {
    InspectionGradeEntity existing = gradeEntity("G-1", TENANT, "A");
    when(gradeRepo.findByTenantIdAndCode(TENANT, "G-1")).thenReturn(Optional.of(existing));
    when(gradeRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    InspectionGrade out =
        service.updateGrade(TENANT, "G-1", new UpdateCatalogEntryRequest().sortOrder(42));
    assertEquals(42, out.getSortOrder());
  }

  @Test
  @Fn({"M04.F08.I04"})
  void deleteGrade_succeeds() {
    InspectionGradeEntity existing = gradeEntity("G-1", TENANT, "A");
    when(gradeRepo.findByTenantIdAndCode(TENANT, "G-1")).thenReturn(Optional.of(existing));
    service.deleteGrade(TENANT, "G-1");
    verify(gradeRepo).delete(existing);
  }

  // ===== M04.F09 牌号码表 =====

  @Test
  @Fn({"M04.F09.I01"})
  void listBrands_supportsObjectFilter() {
    when(brandRepo.filter(TENANT, "OBJ-X", ""))
        .thenReturn(List.of(brandEntity("B-1", TENANT, "OBJ-X", "Brand X")));
    List<InspectionBrand> out = service.listBrands(TENANT, "OBJ-X", null);
    assertEquals(1, out.size());
    assertEquals("Brand X", out.get(0).getName());
  }

  @Test
  @Fn({"M04.F09.I02"})
  void createBrand_setsTenant() {
    CreateCatalogEntryRequest req = new CreateCatalogEntryRequest().code("B-1").name("Brand");
    when(brandRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    InspectionBrand out = service.createBrand(TENANT, req);
    assertNotNull(out);
    assertEquals(TENANT, out.getTenantId());
  }

  @Test
  @Fn({"M04.F09.I03"})
  void updateBrand_appliesName() {
    InspectionBrandEntity existing = brandEntity("B-1", TENANT, null, "Old");
    when(brandRepo.findByTenantIdAndCode(TENANT, "B-1")).thenReturn(Optional.of(existing));
    when(brandRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    InspectionBrand out =
        service.updateBrand(TENANT, "B-1", new UpdateCatalogEntryRequest().name("Renamed"));
    assertEquals("Renamed", out.getName());
  }

  @Test
  @Fn({"M04.F09.I04"})
  void deleteBrand_callsRepoDelete() {
    InspectionBrandEntity existing = brandEntity("B-1", TENANT, null, null);
    when(brandRepo.findByTenantIdAndCode(TENANT, "B-1")).thenReturn(Optional.of(existing));
    service.deleteBrand(TENANT, "B-1");
    verify(brandRepo).delete(existing);
  }

  // === helpers ===

  private static InspectionModelEntity entity(
      String code, String tenant, String inspectionObjectCode, String name) {
    InspectionModelEntity e = new InspectionModelEntity();
    e.setCode(code);
    e.setTenantId(tenant);
    e.setInspectionObjectCode(inspectionObjectCode);
    e.setName(name);
    e.setCreatedAt("2026-08-18T10:00:00Z");
    e.setUpdatedAt("2026-08-18T10:00:00Z");
    return e;
  }

  private static InspectionSpecEntity specEntity(String code, String tenant, String name) {
    InspectionSpecEntity e = new InspectionSpecEntity();
    e.setCode(code);
    e.setTenantId(tenant);
    e.setName(name);
    return e;
  }

  private static InspectionGradeEntity gradeEntity(String code, String tenant, String name) {
    InspectionGradeEntity e = new InspectionGradeEntity();
    e.setCode(code);
    e.setTenantId(tenant);
    e.setName(name);
    return e;
  }

  private static InspectionBrandEntity brandEntity(
      String code, String tenant, String inspectionObjectCode, String name) {
    InspectionBrandEntity e = new InspectionBrandEntity();
    e.setCode(code);
    e.setTenantId(tenant);
    e.setInspectionObjectCode(inspectionObjectCode);
    e.setName(name);
    return e;
  }
}
