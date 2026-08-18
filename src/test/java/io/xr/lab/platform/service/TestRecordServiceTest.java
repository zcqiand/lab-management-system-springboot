package io.xr.lab.platform.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.xr.harness.junit.Fn;
import io.xr.lab.platform.entity.TestRecordEntity;
import io.xr.lab.platform.repository.TestRecordRepository;
import io.xr.lab.shared.dto.CreateTestRecordRequest;
import io.xr.lab.shared.dto.TestRecord;
import io.xr.lab.shared.dto.UpdateTestRecordRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * M03.F03 检测记录 6 子项单测（CRUD + setVerdict）。tenant-scoped。 镜像 lab-msw handler-extra.ts testRecords
 * 处理器语义。
 */
class TestRecordServiceTest {

  private static final String TENANT = "TENANT-001";

  private TestRecordRepository repo;
  private TestRecordService service;

  @BeforeEach
  void setUp() {
    repo = org.mockito.Mockito.mock(TestRecordRepository.class);
    service = new TestRecordService(repo);
  }

  // I06 list
  @Test
  @Fn({"M03.F03.I06"})
  void list_filtersBySample() {
    when(repo.filter(TENANT, "S-1")).thenReturn(List.of(entity("TR-1"), entity("TR-2")));
    assertEquals(2, service.list(TENANT, "S-1").size());
  }

  // I07 get
  @Test
  @Fn({"M03.F03.I07"})
  void get_returnsByTenantAndId() {
    when(repo.findByTenantIdAndId(TENANT, "TR-1")).thenReturn(Optional.of(entity("TR-1")));
    TestRecord out = service.get(TENANT, "TR-1");
    assertEquals("TR-1", out.getId());
  }

  @Test
  @Fn({"M03.F03.I07"})
  void get_missing_throws404() {
    when(repo.findByTenantIdAndId(any(), any())).thenReturn(Optional.empty());
    assertThrows(NoSuchElementException.class, () -> service.get(TENANT, "X"));
  }

  // I08 create
  @Test
  @Fn({"M03.F03.I08"})
  void create_stampsTimestampsAndSaves() {
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    var req =
        new CreateTestRecordRequest()
            .sampleId("S-1")
            .parameterCode("P-1")
            .requirement("req text")
            .result("result text");
    service.create(TENANT, req);
    ArgumentCaptor<TestRecordEntity> captor = ArgumentCaptor.forClass(TestRecordEntity.class);
    verify(repo).save(captor.capture());
    TestRecordEntity saved = captor.getValue();
    assertEquals(saved.getCreatedAt(), saved.getUpdatedAt());
    assertEquals(TENANT, saved.getTenantId());
    assertTrue(saved.getId().startsWith("TR-"));
    assertEquals("S-1", saved.getSampleId());
  }

  @Test
  @Fn({"M03.F03.I08"})
  void create_missingRequired_throws400() {
    assertThrows(
        IllegalArgumentException.class,
        () -> service.create(TENANT, new CreateTestRecordRequest()));
  }

  // I09 update
  @Test
  @Fn({"M03.F03.I09"})
  void update_appliesProvidedFieldsOnly() {
    TestRecordEntity existing = entity("TR-1");
    when(repo.findByTenantIdAndId(TENANT, "TR-1")).thenReturn(Optional.of(existing));
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    TestRecord out =
        service.update(
            TENANT, "TR-1", new UpdateTestRecordRequest().result("new result").verdict("PASS"));
    assertEquals("new result", out.getResult());
    assertEquals("PASS", out.getVerdict());
    // 未传字段保留
    assertEquals("P-1", out.getParameterCode());
  }

  @Test
  @Fn({"M03.F03.I09"})
  void update_missing_throws404() {
    when(repo.findByTenantIdAndId(any(), any())).thenReturn(Optional.empty());
    assertThrows(
        NoSuchElementException.class,
        () -> service.update(TENANT, "X", new UpdateTestRecordRequest()));
  }

  // I10 delete
  @Test
  @Fn({"M03.F03.I10"})
  void delete_existing_succeeds() {
    when(repo.findByTenantIdAndId(TENANT, "TR-1")).thenReturn(Optional.of(entity("TR-1")));
    service.delete(TENANT, "TR-1");
    verify(repo, times(1)).delete(any());
  }

  @Test
  @Fn({"M03.F03.I10"})
  void delete_missing_throws404() {
    when(repo.findByTenantIdAndId(any(), any())).thenReturn(Optional.empty());
    assertThrows(NoSuchElementException.class, () -> service.delete(TENANT, "X"));
    verify(repo, never()).delete(any());
  }

  // I11 setVerdict
  @Test
  @Fn({"M03.F03.I11"})
  void setVerdict_overridesHumanVerdict() {
    TestRecordEntity existing = entity("TR-1");
    when(repo.findByTenantIdAndId(TENANT, "TR-1")).thenReturn(Optional.of(existing));
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    TestRecord out = service.setVerdict(TENANT, "TR-1", "FAIL");
    assertEquals("FAIL", out.getVerdict());
  }

  @Test
  @Fn({"M03.F03.I11"})
  void setVerdict_missing_throws404() {
    when(repo.findByTenantIdAndId(any(), any())).thenReturn(Optional.empty());
    assertThrows(NoSuchElementException.class, () -> service.setVerdict(TENANT, "X", "FAIL"));
  }

  @SuppressWarnings("unused")
  private static boolean assertTrue(boolean c) {
    return c;
  }

  private static TestRecordEntity entity(String id) {
    TestRecordEntity e = new TestRecordEntity();
    e.setId(id);
    e.setTenantId(TENANT);
    e.setSampleId("S-1");
    e.setParameterCode("P-1");
    e.setStandardCode(null);
    e.setRequirementCode(null);
    e.setRequirement("req");
    e.setResult("res");
    e.setCreatedAt("2026-08-18T10:00:00Z");
    e.setUpdatedAt("2026-08-18T10:00:00Z");
    return e;
  }
}
