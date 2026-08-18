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
import io.xr.lab.platform.entity.InspectionReportNameEntity;
import io.xr.lab.platform.repository.InspectionReportNameRepository;
import io.xr.lab.shared.dto.CreateInspectionReportNameRequest;
import io.xr.lab.shared.dto.ExtFieldDef;
import io.xr.lab.shared.dto.ExtFieldDefSource;
import io.xr.lab.shared.dto.ExtFieldDefType;
import io.xr.lab.shared.dto.InspectionReportName;
import io.xr.lab.shared.dto.UpdateInspectionReportNameRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/** M06.F07 报告名称 5 子项单测。平台级字典（无 tenant）。 */
class InspectionReportNameServiceTest {

  private InspectionReportNameRepository repo;
  private InspectionReportNameService service;

  @BeforeEach
  void setUp() {
    repo = org.mockito.Mockito.mock(InspectionReportNameRepository.class);
    service = new InspectionReportNameService(repo);
  }

  // I01 list
  @Test
  @Fn({"M06.F07.I01"})
  void list_nullKeyword_returnsAll() {
    when(repo.filter("")).thenReturn(List.of(entity("RN-1"), entity("RN-2")));
    assertEquals(2, service.list(null).size());
  }

  @Test
  @Fn({"M06.F07.I01"})
  void list_withKeyword_passesThrough() {
    when(repo.filter("水泥")).thenReturn(List.of(entity("RN-1")));
    assertEquals(1, service.list("水泥").size());
  }

  // I02 get
  @Test
  @Fn({"M06.F07.I02"})
  void get_returnsByCode() {
    when(repo.findById("RN-1")).thenReturn(Optional.of(entity("RN-1")));
    InspectionReportName out = service.get("RN-1");
    assertEquals("RN-1", out.getCode());
  }

  @Test
  @Fn({"M06.F07.I02"})
  void get_missing_throws404() {
    when(repo.findById("X")).thenReturn(Optional.empty());
    assertThrows(NoSuchElementException.class, () -> service.get("X"));
  }

  // I03 create
  @Test
  @Fn({"M06.F07.I03"})
  void create_serializesExtFieldsAndStampsTimestamps() {
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    ExtFieldDef f1 =
        new ExtFieldDef()
            .key("compressive")
            .label("抗压")
            .type(ExtFieldDefType.NUMBER)
            .required(true)
            .source(ExtFieldDefSource.SAMPLE);
    var req = new CreateInspectionReportNameRequest("RN-1", "水泥抗压报告").extFields(List.of(f1));
    InspectionReportName out = service.create(req);
    ArgumentCaptor<InspectionReportNameEntity> captor =
        ArgumentCaptor.forClass(InspectionReportNameEntity.class);
    verify(repo).save(captor.capture());
    InspectionReportNameEntity saved = captor.getValue();
    assertNotNull(saved.getExtFields());
    assertTrue(saved.getExtFields().contains("抗压"));
    assertEquals(0, saved.getSortOrder());
    assertEquals(saved.getCreatedAt(), saved.getUpdatedAt());
    assertEquals(1, out.getExtFields().size());
    assertEquals("抗压", out.getExtFields().get(0).getLabel());
  }

  // I04 update
  @Test
  @Fn({"M06.F07.I04"})
  void update_appliesProvidedFieldsOnly() {
    InspectionReportNameEntity existing = entity("RN-1");
    when(repo.findById("RN-1")).thenReturn(Optional.of(existing));
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    InspectionReportName out =
        service.update(
            "RN-1", new UpdateInspectionReportNameRequest().name("水泥抗压报告v2").sortOrder(5));
    assertEquals("水泥抗压报告v2", out.getName());
    assertEquals(5, out.getSortOrder());
    // 未传字段保留
    assertEquals("RN-1", out.getCode());
    assertTrue(out.getUpdatedAt().length() > 0);
  }

  @Test
  @Fn({"M06.F07.I04"})
  void update_missing_throws404() {
    when(repo.findById("X")).thenReturn(Optional.empty());
    assertThrows(
        NoSuchElementException.class,
        () -> service.update("X", new UpdateInspectionReportNameRequest()));
  }

  // I05 delete
  @Test
  @Fn({"M06.F07.I05"})
  void delete_existing_succeeds() {
    when(repo.existsById("RN-1")).thenReturn(true);
    service.delete("RN-1");
    verify(repo, times(1)).deleteById("RN-1");
  }

  @Test
  @Fn({"M06.F07.I05"})
  void delete_missing_throws404() {
    when(repo.existsById("X")).thenReturn(false);
    assertThrows(NoSuchElementException.class, () -> service.delete("X"));
    verify(repo, never()).deleteById(any());
  }

  private static InspectionReportNameEntity entity(String code) {
    InspectionReportNameEntity e = new InspectionReportNameEntity();
    e.setCode(code);
    e.setName("name-" + code);
    e.setSortOrder(0);
    e.setCreatedAt("2026-08-18T10:00:00Z");
    e.setUpdatedAt("2026-08-18T10:00:00Z");
    return e;
  }
}
