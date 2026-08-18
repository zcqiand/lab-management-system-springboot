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
import io.xr.lab.platform.entity.ParamInterfaceEntity;
import io.xr.lab.platform.repository.ParamInterfaceRepository;
import io.xr.lab.shared.dto.CreateParamInterfaceRequest;
import io.xr.lab.shared.dto.ParamInterface;
import io.xr.lab.shared.dto.UpdateParamInterfaceRequest;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/** M06.F08 参数界面 5 子项单测。平台级字典（无 tenant）。 */
class ParamInterfaceServiceTest {

  private ParamInterfaceRepository repo;
  private ParamInterfaceService service;

  @BeforeEach
  void setUp() {
    repo = org.mockito.Mockito.mock(ParamInterfaceRepository.class);
    service = new ParamInterfaceService(repo);
  }

  // I01 list
  @Test
  @Fn({"M06.F08.I01"})
  void list_nullKeyword_returnsAll() {
    when(repo.filter("")).thenReturn(List.of(entity("PI-1"), entity("PI-2")));
    assertEquals(2, service.list(null).size());
  }

  // I02 get
  @Test
  @Fn({"M06.F08.I02"})
  void get_returnsByCode() {
    when(repo.findById("PI-1")).thenReturn(Optional.of(entity("PI-1")));
    ParamInterface out = service.get("PI-1");
    assertEquals("PI-1", out.getCode());
    assertEquals("@/components/forms/Compressive", out.getComponentPath());
  }

  @Test
  @Fn({"M06.F08.I02"})
  void get_missing_throws404() {
    when(repo.findById("X")).thenReturn(Optional.empty());
    assertThrows(NoSuchElementException.class, () -> service.get("X"));
  }

  // I03 create
  @Test
  @Fn({"M06.F08.I03"})
  void create_serializesConfigAndStampsTimestamps() {
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    var req =
        new CreateParamInterfaceRequest("PI-1", "@/components/forms/Compressive")
            .name("抗压界面")
            .putConfigItem("min", 0)
            .putConfigItem("max", 100);
    ParamInterface out = service.create(req);
    ArgumentCaptor<ParamInterfaceEntity> captor =
        ArgumentCaptor.forClass(ParamInterfaceEntity.class);
    verify(repo).save(captor.capture());
    ParamInterfaceEntity saved = captor.getValue();
    assertNotNull(saved.getConfig());
    assertTrue(saved.getConfig().contains("\"min\""));
    assertTrue(saved.getConfig().contains("100"));
    assertEquals(0, saved.getSortOrder());
    assertEquals(saved.getCreatedAt(), saved.getUpdatedAt());
    Map<String, Object> cfg = out.getConfig();
    assertEquals(2, cfg.size());
    assertEquals(100, cfg.get("max"));
  }

  // I04 update
  @Test
  @Fn({"M06.F08.I04"})
  void update_appliesProvidedFieldsOnly() {
    ParamInterfaceEntity existing = entity("PI-1");
    when(repo.findById("PI-1")).thenReturn(Optional.of(existing));
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    ParamInterface out =
        service.update(
            "PI-1", new UpdateParamInterfaceRequest().name("抗压界面v2").putConfigItem("unit", "MPa"));
    assertEquals("抗压界面v2", out.getName());
    assertEquals("MPa", out.getConfig().get("unit"));
    // 未传 componentPath 保留
    assertEquals("@/components/forms/Compressive", out.getComponentPath());
  }

  @Test
  @Fn({"M06.F08.I04"})
  void update_missing_throws404() {
    when(repo.findById("X")).thenReturn(Optional.empty());
    assertThrows(
        NoSuchElementException.class, () -> service.update("X", new UpdateParamInterfaceRequest()));
  }

  // I05 delete
  @Test
  @Fn({"M06.F08.I05"})
  void delete_existing_succeeds() {
    when(repo.existsById("PI-1")).thenReturn(true);
    service.delete("PI-1");
    verify(repo, times(1)).deleteById("PI-1");
  }

  @Test
  @Fn({"M06.F08.I05"})
  void delete_missing_throws404() {
    when(repo.existsById("X")).thenReturn(false);
    assertThrows(NoSuchElementException.class, () -> service.delete("X"));
    verify(repo, never()).deleteById(any());
  }

  private static ParamInterfaceEntity entity(String code) {
    ParamInterfaceEntity e = new ParamInterfaceEntity();
    e.setCode(code);
    e.setName("name-" + code);
    e.setComponentPath("@/components/forms/Compressive");
    e.setSortOrder(0);
    e.setCreatedAt("2026-08-18T10:00:00Z");
    e.setUpdatedAt("2026-08-18T10:00:00Z");
    return e;
  }
}
