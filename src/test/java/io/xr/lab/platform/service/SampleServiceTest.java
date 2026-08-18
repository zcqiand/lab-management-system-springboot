package io.xr.lab.platform.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.xr.harness.junit.Fn;
import io.xr.lab.platform.entity.SampleEntity;
import io.xr.lab.platform.entity.SampleReceiptEntity;
import io.xr.lab.platform.repository.SampleReceiptRepository;
import io.xr.lab.platform.repository.SampleRepository;
import io.xr.lab.shared.dto.CreateSampleRequest;
import io.xr.lab.shared.dto.UpdateSampleRequest;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** M03.F03 样品 5 子项单测。 */
class SampleServiceTest {

  private static final String TENANT = "TENANT-001";

  private SampleRepository repo;
  private SampleReceiptRepository receiptRepo;
  private SampleService service;

  @BeforeEach
  void setUp() {
    repo = org.mockito.Mockito.mock(SampleRepository.class);
    receiptRepo = org.mockito.Mockito.mock(SampleReceiptRepository.class);
    service = new SampleService(repo, receiptRepo);
  }

  // M03.F03.I01 list

  @Test
  @Fn({"M03.F03.I01"})
  void list_returnsMappedSamples() {
    when(repo.filter(TENANT, "", "")).thenReturn(List.of(entity("S-001")));
    assertEquals(1, service.list(TENANT, null, null).size());
  }

  // M03.F03.I02 get

  @Test
  @Fn({"M03.F03.I02"})
  void get_returnsDto() {
    when(repo.findByTenantIdAndId(TENANT, "S-001")).thenReturn(Optional.of(entity("S-001")));
    var out = service.get(TENANT, "S-001");
    assertEquals("S-001", out.getId());
  }

  @Test
  @Fn({"M03.F03.I02"})
  void get_missing_throws404() {
    when(repo.findByTenantIdAndId(TENANT, "MISSING")).thenReturn(Optional.empty());
    assertThrows(NoSuchElementException.class, () -> service.get(TENANT, "MISSING"));
  }

  // M03.F03.I03 create

  @Test
  @Fn({"M03.F03.I03"})
  void create_initializesIdAndExt() {
    CreateSampleRequest req =
        new CreateSampleRequest()
            .receiptId("R-001")
            .sampleCode("S-001")
            .sampleName("First sample")
            .ext(new HashMap<>());
    SampleReceiptEntity receipt = new SampleReceiptEntity();
    receipt.setId("R-001");
    when(receiptRepo.findByTenantIdAndId(TENANT, "R-001")).thenReturn(Optional.of(receipt));
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    var out = service.create(TENANT, req);
    assertNotNull(out.getId(), "id should be generated");
    assertTrue(out.getId().startsWith("S-"));
    assertTrue(out.getExt() != null);
  }

  @Test
  @Fn({"M03.F03.I03"})
  void create_missingReceipt_throws404() {
    CreateSampleRequest req =
        new CreateSampleRequest().receiptId("MISSING").sampleCode("S-001").ext(new HashMap<>());
    when(receiptRepo.findByTenantIdAndId(TENANT, "MISSING")).thenReturn(Optional.empty());
    assertThrows(NoSuchElementException.class, () -> service.create(TENANT, req));
  }

  // M03.F03.I04 update

  @Test
  @Fn({"M03.F03.I04"})
  void update_appliesPartialFields() {
    SampleEntity existing = entity("S-001");
    when(repo.findByTenantIdAndId(TENANT, "S-001")).thenReturn(Optional.of(existing));
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    var out = service.update(TENANT, "S-001", new UpdateSampleRequest().sampleName("renamed"));
    assertEquals("renamed", out.getSampleName());
  }

  // M03.F03.I05 delete

  @Test
  @Fn({"M03.F03.I05"})
  void delete_callsRepoRemove() {
    SampleEntity existing = entity("S-001");
    when(repo.findByTenantIdAndId(TENANT, "S-001")).thenReturn(Optional.of(existing));
    service.delete(TENANT, "S-001");
    verify(repo, times(1)).delete(existing);
  }

  private static SampleEntity entity(String id) {
    SampleEntity e = new SampleEntity();
    e.setId(id);
    e.setReceiptId("R-001");
    e.setSampleCode(id);
    e.setSampleName("name");
    e.setTenantId(TENANT);
    e.setExt(new HashMap<>());
    e.setCreatedAt("2026-08-18T10:00:00Z");
    e.setUpdatedAt("2026-08-18T10:00:00Z");
    return e;
  }
}
