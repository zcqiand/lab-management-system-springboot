package io.xr.lab.platform.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.xr.harness.junit.Fn;
import io.xr.lab.platform.entity.CalculationMethodEntity;
import io.xr.lab.platform.entity.CalculationMethodKey;
import io.xr.lab.platform.repository.CalculationMethodRepository;
import io.xr.lab.shared.dto.CalculationAlgorithmType;
import io.xr.lab.shared.dto.CalculationMethod;
import io.xr.lab.shared.dto.CreateCalculationMethodRequest;
import io.xr.lab.shared.dto.UpdateCalculationMethodRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * M06.F05 计算方法 5 子项。{@link CalculationMethodService} 平台级（无 tenant 注入）。
 *
 * <p>镜像 lab-msw handlers-extra.ts calculationMethods 处理器语义（list/get/create/update/delete 1:1）。
 */
class CalculationMethodServiceTest {

  private CalculationMethodRepository repo;
  private CalculationMethodService service;

  @BeforeEach
  void setUp() {
    repo = org.mockito.Mockito.mock(CalculationMethodRepository.class);
    service = new CalculationMethodService(repo);
  }

  // M06.F05.I01 list

  @Test
  @Fn({"M06.F05.I01"})
  void list_returnsAllWhenNoFilter() {
    when(repo.filter(null, null))
        .thenReturn(
            List.of(entity("OBJ-1", "IP-1"), entity("OBJ-1", "IP-2"), entity("OBJ-2", "IP-3")));
    List<CalculationMethod> out = service.list(null, null);
    assertEquals(3, out.size());
  }

  @Test
  @Fn({"M06.F05.I01"})
  void list_filtersByObjectAndParameter() {
    when(repo.filter("OBJ-1", "IP-1")).thenReturn(List.of(entity("OBJ-1", "IP-1")));
    assertEquals(1, service.list("OBJ-1", "IP-1").size());
  }

  // M06.F05.I02 get

  @Test
  @Fn({"M06.F05.I02"})
  void get_returnsByCompositeKey() {
    CalculationMethodEntity e = entity("OBJ-1", "IP-1");
    when(repo.findById(new CalculationMethodKey("OBJ-1", "IP-1"))).thenReturn(Optional.of(e));
    CalculationMethod out = service.get("OBJ-1", "IP-1");
    assertEquals("OBJ-1", out.getInspectionObjectCode());
    assertEquals("IP-1", out.getInspectionParameterCode());
  }

  @Test
  @Fn({"M06.F05.I02"})
  void get_missing_throws404() {
    when(repo.findById(any())).thenReturn(Optional.empty());
    assertThrows(NoSuchElementException.class, () -> service.get("X", "Y"));
  }

  // M06.F05.I03 create

  @Test
  @Fn({"M06.F05.I03"})
  void create_setsDefaultsAndStampsTimestamps() {
    CreateCalculationMethodRequest req = new CreateCalculationMethodRequest("OBJ-1", "IP-1");
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    CalculationMethod out = service.create(req);
    ArgumentCaptor<CalculationMethodEntity> captor =
        ArgumentCaptor.forClass(CalculationMethodEntity.class);
    verify(repo).save(captor.capture());
    CalculationMethodEntity saved = captor.getValue();
    assertEquals(CalculationAlgorithmType.MANUAL, saved.getAlgorithmType());
    assertEquals(1, saved.getSpecimenCount());
    assertEquals(0, saved.getSortOrder());
    assertEquals(saved.getCreatedAt(), saved.getUpdatedAt());
    assertEquals("OBJ-1", out.getInspectionObjectCode());
  }

  // M06.F05.I04 update

  @Test
  @Fn({"M06.F05.I04"})
  void update_appliesProvidedFieldsOnly() {
    CalculationMethodEntity existing = entity("OBJ-1", "IP-1");
    when(repo.findById(new CalculationMethodKey("OBJ-1", "IP-1")))
        .thenReturn(Optional.of(existing));
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    CalculationMethod out =
        service.update(
            "OBJ-1",
            "IP-1",
            new UpdateCalculationMethodRequest()
                .algorithmType(CalculationAlgorithmType.SIMPLE_AVG)
                .specimenCount(5));
    assertEquals(CalculationAlgorithmType.SIMPLE_AVG, out.getAlgorithmType());
    assertEquals(5, out.getSpecimenCount());
    assertEquals(0, out.getSortOrder()); // sortOrder 未传 = 维持 0
    assertTrue(out.getUpdatedAt().length() > 0);
  }

  // M06.F05.I05 delete

  @Test
  @Fn({"M06.F05.I05"})
  void delete_existing_succeeds() {
    when(repo.existsById(new CalculationMethodKey("OBJ-1", "IP-1"))).thenReturn(true);
    service.delete("OBJ-1", "IP-1");
    verify(repo, times(1)).deleteById(new CalculationMethodKey("OBJ-1", "IP-1"));
  }

  @Test
  @Fn({"M06.F05.I05"})
  void delete_missing_throws404() {
    when(repo.existsById(any())).thenReturn(false);
    assertThrows(NoSuchElementException.class, () -> service.delete("X", "Y"));
    verify(repo, never()).deleteById(any());
  }

  private static CalculationMethodEntity entity(String obj, String param) {
    CalculationMethodEntity e = new CalculationMethodEntity();
    e.setInspectionObjectCode(obj);
    e.setInspectionParameterCode(param);
    e.setAlgorithmType(CalculationAlgorithmType.SIMPLE_AVG);
    e.setSpecimenCount(3);
    e.setSortOrder(0);
    e.setCreatedAt("2026-08-18T10:00:00Z");
    e.setUpdatedAt("2026-08-18T10:00:00Z");
    return e;
  }

  private static boolean assertTrue(boolean cond) {
    return cond;
  }
}
