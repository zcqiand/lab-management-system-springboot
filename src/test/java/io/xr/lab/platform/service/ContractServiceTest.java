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
import io.xr.lab.platform.entity.ContractEntity;
import io.xr.lab.platform.repository.ContractRepository;
import io.xr.lab.shared.dto.ContractStatus;
import io.xr.lab.shared.dto.CreateContractRequest;
import io.xr.lab.shared.dto.UpdateContractRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/** M02.F01 合同管理 5 子项单测。 */
class ContractServiceTest {

  private static final String TENANT = "TENANT-001";

  private ContractRepository repo;
  private ContractService service;

  @BeforeEach
  void setUp() {
    repo = org.mockito.Mockito.mock(ContractRepository.class);
    service = new ContractService(repo);
  }

  // M02.F01.I01 list

  @Test
  @Fn({"M02.F01.I01"})
  void list_returnsMappedContracts() {
    when(repo.filter(TENANT, "", null)).thenReturn(List.of(entity("C-001", "active")));
    var out = service.list(TENANT, "", null);
    assertEquals(1, out.size());
    assertEquals("C-001", out.get(0).getContractCode());
    assertEquals(TENANT, out.get(0).getTenantId());
  }

  // M02.F01.I02 get

  @Test
  @Fn({"M02.F01.I02"})
  void get_existing_returnsDto() {
    when(repo.findByTenantIdAndId(TENANT, "C-001"))
        .thenReturn(Optional.of(entity("C-001", "active")));
    var out = service.get(TENANT, "C-001");
    assertEquals("C-001", out.getContractCode());
  }

  @Test
  @Fn({"M02.F01.I02"})
  void get_missing_throws404() {
    when(repo.findByTenantIdAndId(TENANT, "MISSING")).thenReturn(Optional.empty());
    assertThrows(NoSuchElementException.class, () -> service.get(TENANT, "MISSING"));
  }

  // M02.F01.I03 create

  @Test
  @Fn({"M02.F01.I03"})
  void create_stampsTimestampsAndDefaults() {
    CreateContractRequest req =
        new CreateContractRequest()
            .contractCode("C-NEW")
            .clientUnit("client")
            .projectName("p")
            .constructionUnit("c")
            .witnessUnit("w")
            .witness("witness-person");
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    var out = service.create(TENANT, req);
    ArgumentCaptor<ContractEntity> captor = ArgumentCaptor.forClass(ContractEntity.class);
    verify(repo).save(captor.capture());
    var saved = captor.getValue();
    assertEquals(TENANT, saved.getTenantId());
    assertEquals(ContractStatus.ACTIVE, saved.getStatus());
    assertNotNull(saved.getCreatedAt());
    assertEquals(saved.getCreatedAt(), saved.getUpdatedAt());
    assertTrue(out.getId().startsWith("C-"));
  }

  // M02.F01.I04 update

  @Test
  @Fn({"M02.F01.I04"})
  void update_appliesProvidedFields() {
    ContractEntity existing = entity("C-001", "active");
    when(repo.findByTenantIdAndId(TENANT, "C-001")).thenReturn(Optional.of(existing));
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    var out =
        service.update(
            TENANT,
            "C-001",
            new UpdateContractRequest().projectName("renamed").status(ContractStatus.ARCHIVED));
    assertEquals("renamed", out.getProjectName());
    assertEquals(ContractStatus.ARCHIVED, out.getStatus());
  }

  // M02.F01.I05 delete

  @Test
  @Fn({"M02.F01.I05"})
  void delete_callsRepoRemove() {
    ContractEntity existing = entity("C-001", "active");
    when(repo.findByTenantIdAndId(TENANT, "C-001")).thenReturn(Optional.of(existing));
    service.delete(TENANT, "C-001");
    verify(repo, times(1)).delete(existing);
  }

  @Test
  @Fn({"M02.F01.I05"})
  void delete_missing_throws404() {
    when(repo.findByTenantIdAndId(TENANT, "X")).thenReturn(Optional.empty());
    assertThrows(NoSuchElementException.class, () -> service.delete(TENANT, "X"));
  }

  private static ContractEntity entity(String code, String status) {
    ContractEntity e = new ContractEntity();
    e.setId(code);
    e.setContractCode(code);
    e.setClientUnit("client");
    e.setProjectName("p");
    e.setConstructionUnit("c");
    e.setWitnessUnit("w");
    e.setWitness("witness-person");
    e.setStatus(ContractStatus.ACTIVE);
    e.setTenantId(TENANT);
    e.setCreatedAt("2026-08-18T10:00:00Z");
    e.setUpdatedAt("2026-08-18T10:00:00Z");
    return e;
  }
}
