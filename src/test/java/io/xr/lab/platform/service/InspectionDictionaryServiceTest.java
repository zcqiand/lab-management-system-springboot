package io.xr.lab.platform.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.xr.harness.junit.Fn;
import io.xr.lab.platform.entity.InspectionParameterEntity;
import io.xr.lab.platform.entity.InspectionSpecialtyEntity;
import io.xr.lab.platform.entity.InspectionStandardEntity;
import io.xr.lab.platform.repository.InspectionParameterRepository;
import io.xr.lab.platform.repository.InspectionSpecialtyRepository;
import io.xr.lab.platform.repository.InspectionStandardRepository;
import io.xr.lab.shared.dto.CreateInspectionParameterRequest;
import io.xr.lab.shared.dto.CreateInspectionSpecialtyRequest;
import io.xr.lab.shared.dto.CreateInspectionStandardRequest;
import io.xr.lab.shared.dto.InspectionParameter;
import io.xr.lab.shared.dto.InspectionParameterSourceType;
import io.xr.lab.shared.dto.InspectionSpecialty;
import io.xr.lab.shared.dto.InspectionStandard;
import io.xr.lab.shared.dto.InspectionStandardStatus;
import io.xr.lab.shared.dto.UpdateInspectionParameterRequest;
import io.xr.lab.shared.dto.UpdateInspectionSpecialtyRequest;
import io.xr.lab.shared.dto.UpdateInspectionStandardRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * M06 字典 3 实体 12 子项单测：specialty/parameter/standard 各 4 个（list/create/update/delete）。 平台级字典（无 tenant
 * 注入）。镜像 lab-msw handlers-extra.ts 3 实体处理语义。
 */
class InspectionDictionaryServiceTest {

  private InspectionSpecialtyRepository specialtyRepo;
  private InspectionParameterRepository parameterRepo;
  private InspectionStandardRepository standardRepo;
  private InspectionDictionaryService service;

  @BeforeEach
  void setUp() {
    specialtyRepo = org.mockito.Mockito.mock(InspectionSpecialtyRepository.class);
    parameterRepo = org.mockito.Mockito.mock(InspectionParameterRepository.class);
    standardRepo = org.mockito.Mockito.mock(InspectionStandardRepository.class);
    service = new InspectionDictionaryService(specialtyRepo, parameterRepo, standardRepo);
  }

  // ============== M06.F01 Specialty ==============

  // I01 list
  @Test
  @Fn({"M06.F01.I01"})
  void listSpecialties_nullKeyword_returnsAll() {
    when(specialtyRepo.filter("")).thenReturn(List.of(specialty("S-1"), specialty("S-2")));
    List<InspectionSpecialty> out = service.listSpecialties(null);
    assertEquals(2, out.size());
  }

  @Test
  @Fn({"M06.F01.I01"})
  void listSpecialties_withKeyword_passesThrough() {
    when(specialtyRepo.filter("concrete")).thenReturn(List.of(specialty("S-1")));
    assertEquals(1, service.listSpecialties("concrete").size());
  }

  // I02 create
  @Test
  @Fn({"M06.F01.I02"})
  void createSpecialty_appliesDefaultsAndStampsTimestamps() {
    when(specialtyRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    var req = new CreateInspectionSpecialtyRequest("S-1", "OFFICIAL-001", "混凝土专项");
    InspectionSpecialty out = service.createSpecialty(req);
    ArgumentCaptor<InspectionSpecialtyEntity> captor =
        ArgumentCaptor.forClass(InspectionSpecialtyEntity.class);
    verify(specialtyRepo).save(captor.capture());
    InspectionSpecialtyEntity saved = captor.getValue();
    assertEquals(Boolean.TRUE, saved.getIsOfficial());
    assertEquals(Boolean.TRUE, saved.getEnabled());
    assertEquals(0, saved.getSortOrder());
    assertEquals(saved.getCreatedAt(), saved.getUpdatedAt());
    assertEquals("S-1", out.getCode());
  }

  // I03 update
  @Test
  @Fn({"M06.F01.I03"})
  void updateSpecialty_appliesProvidedFieldsOnly() {
    InspectionSpecialtyEntity existing = specialty("S-1");
    when(specialtyRepo.findById("S-1")).thenReturn(Optional.of(existing));
    when(specialtyRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    InspectionSpecialty out =
        service.updateSpecialty(
            "S-1", new UpdateInspectionSpecialtyRequest().name("混凝土专项v2").enabled(Boolean.FALSE));
    assertEquals("混凝土专项v2", out.getName());
    assertFalse(out.getEnabled());
    assertTrue(out.getUpdatedAt().length() > 0);
    // 未传字段保留
    assertEquals("OFFICIAL-S-1", out.getOfficialNo());
    assertEquals(0, out.getSortOrder());
  }

  @Test
  @Fn({"M06.F01.I03"})
  void updateSpecialty_missing_throws404() {
    when(specialtyRepo.findById("X")).thenReturn(Optional.empty());
    assertThrows(
        NoSuchElementException.class,
        () -> service.updateSpecialty("X", new UpdateInspectionSpecialtyRequest()));
  }

  // I04 delete
  @Test
  @Fn({"M06.F01.I04"})
  void deleteSpecialty_existing_succeeds() {
    when(specialtyRepo.existsById("S-1")).thenReturn(true);
    service.deleteSpecialty("S-1");
    verify(specialtyRepo, times(1)).deleteById("S-1");
  }

  @Test
  @Fn({"M06.F01.I04"})
  void deleteSpecialty_missing_throws404() {
    when(specialtyRepo.existsById("X")).thenReturn(false);
    assertThrows(NoSuchElementException.class, () -> service.deleteSpecialty("X"));
    verify(specialtyRepo, never()).deleteById(any());
  }

  // ============== M06.F03 Parameter ==============

  // I01 list
  @Test
  @Fn({"M06.F03.I01"})
  void listParameters_filtersBySourceType() {
    when(parameterRepo.filter("", InspectionParameterSourceType.OFFICIAL))
        .thenReturn(List.of(parameter("P-1")));
    List<InspectionParameter> out =
        service.listParameters(null, InspectionParameterSourceType.OFFICIAL);
    assertEquals(1, out.size());
    assertEquals(InspectionParameterSourceType.OFFICIAL, out.get(0).getSourceType());
  }

  @Test
  @Fn({"M06.F03.I01"})
  void listParameters_nullSourceType_passesNull() {
    when(parameterRepo.filter("", null)).thenReturn(List.of(parameter("P-1")));
    assertEquals(1, service.listParameters(null, null).size());
  }

  // I02 create
  @Test
  @Fn({"M06.F03.I02"})
  void createParameter_fallsBackRawNameAndCanonicalFromName() {
    when(parameterRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    var req = new CreateInspectionParameterRequest("P-1", "抗压强度", "raw-抗压", "canonical-抗压");
    InspectionParameter out = service.createParameter(req);
    ArgumentCaptor<InspectionParameterEntity> captor =
        ArgumentCaptor.forClass(InspectionParameterEntity.class);
    verify(parameterRepo).save(captor.capture());
    InspectionParameterEntity saved = captor.getValue();
    // rawName/canonicalName 显式提供
    assertEquals("raw-抗压", saved.getRawName());
    assertEquals("canonical-抗压", saved.getCanonicalName());
    assertEquals(InspectionParameterSourceType.OFFICIAL, saved.getSourceType());
    assertEquals(0, saved.getSortOrder());
    // aliases 默认 [] 序列化
    assertEquals("[]", saved.getAliases());
    assertEquals("P-1", out.getCode());
  }

  // I03 update
  @Test
  @Fn({"M06.F03.I03"})
  void updateParameter_replacesAliasesWhenProvided() {
    InspectionParameterEntity existing = parameter("P-1");
    when(parameterRepo.findById("P-1")).thenReturn(Optional.of(existing));
    when(parameterRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    var req = new UpdateInspectionParameterRequest().aliases(List.of("抗压", "compressive"));
    InspectionParameter out = service.updateParameter("P-1", req);
    assertEquals(2, out.getAliases().size());
    assertTrue(out.getAliases().contains("抗压"));
  }

  @Test
  @Fn({"M06.F03.I03"})
  void updateParameter_missing_throws404() {
    when(parameterRepo.findById("X")).thenReturn(Optional.empty());
    assertThrows(
        NoSuchElementException.class,
        () -> service.updateParameter("X", new UpdateInspectionParameterRequest()));
  }

  // I04 delete
  @Test
  @Fn({"M06.F03.I04"})
  void deleteParameter_existing_succeeds() {
    when(parameterRepo.existsById("P-1")).thenReturn(true);
    service.deleteParameter("P-1");
    verify(parameterRepo, times(1)).deleteById("P-1");
  }

  @Test
  @Fn({"M06.F03.I04"})
  void deleteParameter_missing_throws404() {
    when(parameterRepo.existsById("X")).thenReturn(false);
    assertThrows(NoSuchElementException.class, () -> service.deleteParameter("X"));
  }

  // ============== M06.F04 Standard ==============

  // I01 list
  @Test
  @Fn({"M06.F04.I01"})
  void listStandards_filtersByStatus() {
    when(standardRepo.filter("", InspectionStandardStatus.ACTIVE))
        .thenReturn(List.of(standard("GB/T")));
    List<InspectionStandard> out = service.listStandards(null, InspectionStandardStatus.ACTIVE);
    assertEquals(1, out.size());
    assertEquals(InspectionStandardStatus.ACTIVE, out.get(0).getStatus());
  }

  // I02 create
  @Test
  @Fn({"M06.F04.I02"})
  void createStandard_appliesStatusDefault() {
    when(standardRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    var req = new CreateInspectionStandardRequest("GB/T-50082", "普通混凝土长期性能试验方法标准");
    InspectionStandard out = service.createStandard(req);
    ArgumentCaptor<InspectionStandardEntity> captor =
        ArgumentCaptor.forClass(InspectionStandardEntity.class);
    verify(standardRepo).save(captor.capture());
    InspectionStandardEntity saved = captor.getValue();
    assertEquals(InspectionStandardStatus.ACTIVE, saved.getStatus());
    assertEquals("GB/T-50082", out.getCode());
  }

  // I03 update
  @Test
  @Fn({"M06.F04.I03"})
  void updateStandard_supersedeStatus() {
    InspectionStandardEntity existing = standard("GB/T");
    when(standardRepo.findById("GB/T")).thenReturn(Optional.of(existing));
    when(standardRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    InspectionStandard out =
        service.updateStandard(
            "GB/T",
            new UpdateInspectionStandardRequest().status(InspectionStandardStatus.SUPERSEDED));
    assertEquals(InspectionStandardStatus.SUPERSEDED, out.getStatus());
  }

  @Test
  @Fn({"M06.F04.I03"})
  void updateStandard_missing_throws404() {
    when(standardRepo.findById("X")).thenReturn(Optional.empty());
    assertThrows(
        NoSuchElementException.class,
        () -> service.updateStandard("X", new UpdateInspectionStandardRequest()));
  }

  // I04 delete
  @Test
  @Fn({"M06.F04.I04"})
  void deleteStandard_existing_succeeds() {
    when(standardRepo.existsById("GB/T")).thenReturn(true);
    service.deleteStandard("GB/T");
    verify(standardRepo, times(1)).deleteById("GB/T");
  }

  @Test
  @Fn({"M06.F04.I04"})
  void deleteStandard_missing_throws404() {
    when(standardRepo.existsById("X")).thenReturn(false);
    assertThrows(NoSuchElementException.class, () -> service.deleteStandard("X"));
  }

  // ============== helpers ==============

  private static InspectionSpecialtyEntity specialty(String code) {
    InspectionSpecialtyEntity e = new InspectionSpecialtyEntity();
    e.setCode(code);
    e.setOfficialNo("OFFICIAL-" + code);
    e.setName("专项-" + code);
    e.setIsOfficial(true);
    e.setEnabled(true);
    e.setSortOrder(0);
    e.setCreatedAt("2026-08-18T10:00:00Z");
    e.setUpdatedAt("2026-08-18T10:00:00Z");
    return e;
  }

  private static InspectionParameterEntity parameter(String code) {
    InspectionParameterEntity e = new InspectionParameterEntity();
    e.setCode(code);
    e.setName("参数-" + code);
    e.setRawName("raw-" + code);
    e.setCanonicalName("canonical-" + code);
    e.setAliases("[]");
    e.setSourceType(InspectionParameterSourceType.OFFICIAL);
    e.setSortOrder(0);
    e.setCreatedAt("2026-08-18T10:00:00Z");
    e.setUpdatedAt("2026-08-18T10:00:00Z");
    return e;
  }

  private static InspectionStandardEntity standard(String code) {
    InspectionStandardEntity e = new InspectionStandardEntity();
    e.setCode(code);
    e.setName("标准-" + code);
    e.setStatus(InspectionStandardStatus.ACTIVE);
    e.setSortOrder(0);
    e.setCreatedAt("2026-08-18T10:00:00Z");
    e.setUpdatedAt("2026-08-18T10:00:00Z");
    return e;
  }
}
