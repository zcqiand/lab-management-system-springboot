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
import io.xr.lab.platform.entity.SampleReceiptEntity;
import io.xr.lab.platform.repository.ContractRepository;
import io.xr.lab.platform.repository.SampleReceiptRepository;
import io.xr.lab.shared.dto.AssignTaskRequest;
import io.xr.lab.shared.dto.CreateSampleReceiptRequest;
import io.xr.lab.shared.dto.FlowStatus;
import io.xr.lab.shared.dto.UpdateSampleReceiptRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/** M03.F01 + F02 + F09 7 子项单测（含 assignTask + history 拆出端点）。 */
class SampleReceiptServiceTest {

  private static final String TENANT = "TENANT-001";

  private SampleReceiptRepository repo;
  private ContractRepository contractRepo;
  private SampleReceiptService service;

  @BeforeEach
  void setUp() {
    repo = org.mockito.Mockito.mock(SampleReceiptRepository.class);
    contractRepo = org.mockito.Mockito.mock(ContractRepository.class);
    service = new SampleReceiptService(repo, contractRepo);
  }

  // M03.F01.I01 list

  @Test
  @Fn({"M03.F01.I01"})
  void list_returnsMapped() {
    when(repo.filter(TENANT, "", null, "")).thenReturn(List.of(entity("R-001")));
    assertEquals(1, service.list(TENANT, null, null, null).size());
  }

  // M03.F01.I02 get

  @Test
  @Fn({"M03.F01.I02"})
  void get_returnsDto() {
    when(repo.findByTenantIdAndId(TENANT, "R-001")).thenReturn(Optional.of(entity("R-001")));
    var out = service.get(TENANT, "R-001");
    assertEquals("R-001", out.getId());
  }

  // M03.F01.I03 create

  @Test
  @Fn({"M03.F01.I03"})
  void create_initializesFlowReceiving() {
    CreateSampleReceiptRequest req =
        new CreateSampleReceiptRequest()
            .contractId("C-001")
            .commissionCode("CM-001")
            .commissionDate("2026-08-18")
            .categoryCode("CAT-1")
            .receivedBy("admin")
            .sampleSource("client")
            .testCategory("concrete");
    when(contractRepo.findByTenantIdAndId(TENANT, "C-001"))
        .thenReturn(Optional.of(contractStub("C-001")));
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    var out = service.create(TENANT, req);
    ArgumentCaptor<SampleReceiptEntity> captor = ArgumentCaptor.forClass(SampleReceiptEntity.class);
    verify(repo).save(captor.capture());
    var saved = captor.getValue();
    assertEquals(FlowStatus.RECEIVING, saved.getFlowStatus());
    assertEquals("[]", saved.getFlowHistory());
    assertEquals(TENANT, saved.getTenantId());
    assertNotNull(out.getCreatedAt());
  }

  @Test
  @Fn({"M03.F01.I03"})
  void create_missingContract_throws404() {
    CreateSampleReceiptRequest req =
        new CreateSampleReceiptRequest()
            .contractId("MISSING")
            .commissionCode("CM-001")
            .commissionDate("2026-08-18")
            .categoryCode("CAT-1")
            .receivedBy("admin")
            .sampleSource("client")
            .testCategory("concrete");
    when(contractRepo.findByTenantIdAndId(TENANT, "MISSING")).thenReturn(Optional.empty());
    assertThrows(NoSuchElementException.class, () -> service.create(TENANT, req));
  }

  // M03.F01.I04 update

  @Test
  @Fn({"M03.F01.I04"})
  void update_appliesPartialFields() {
    SampleReceiptEntity existing = entity("R-001");
    when(repo.findByTenantIdAndId(TENANT, "R-001")).thenReturn(Optional.of(existing));
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    var out = service.update(TENANT, "R-001", new UpdateSampleReceiptRequest().remark("edit"));
    assertEquals("edit", out.getRemark());
    assertEquals(FlowStatus.RECEIVING, out.getFlowStatus());
  }

  // M03.F01.I05 delete

  @Test
  @Fn({"M03.F01.I05"})
  void delete_callsRepoRemove() {
    SampleReceiptEntity existing = entity("R-001");
    when(repo.findByTenantIdAndId(TENANT, "R-001")).thenReturn(Optional.of(existing));
    service.delete(TENANT, "R-001");
    verify(repo, times(1)).delete(existing);
  }

  // M03.F01.I06 history

  @Test
  @Fn({"M03.F01.I06"})
  void history_returnsParsedEntries() {
    SampleReceiptEntity existing = entity("R-001");
    String validJson =
        "[{\"action\":\"submit\",\"from\":\"receiving\",\"to\":\"task_assignment\","
            + "\"operator\":\"admin\",\"at\":\"2026-08-18T10:00:00Z\",\"reason\":\"\"}]";
    existing.setFlowHistory(validJson);
    when(repo.findByTenantIdAndId(TENANT, "R-001")).thenReturn(Optional.of(existing));
    var history = service.history(TENANT, "R-001");
    assertNotNull(history);
    assertEquals(1, history.size(), "should parse at least one entry, raw=" + validJson);
  }

  // M03.F02.I01 task

  @Test
  @Fn({"M03.F02.I01"})
  void assignTask_receiving_advancesToTaskAssignmentAndWritesHistory() {
    SampleReceiptEntity existing = entity("R-001");
    existing.setFlowStatus(FlowStatus.RECEIVING);
    when(repo.findByTenantIdAndId(TENANT, "R-001")).thenReturn(Optional.of(existing));
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    var out =
        service.assignTask(
            TENANT,
            "R-001",
            new AssignTaskRequest()
                .assigneeId("U-1")
                .assigneeName("lab-tech")
                .plannedTestDate("2026-08-20"));
    org.mockito.ArgumentCaptor<SampleReceiptEntity> capt =
        org.mockito.ArgumentCaptor.forClass(SampleReceiptEntity.class);
    verify(repo).save(capt.capture());
    var saved = capt.getValue();
    assertEquals(FlowStatus.TASK_ASSIGNMENT, out.getFlowStatus());
    assertEquals("U-1", out.getAssigneeId());
    assertTrue(out.getFlowHistory().size() >= 1, "DTO history should have an entry");
    assertTrue(
        saved.getFlowHistory().contains("task_assignment"),
        "raw history JSON should contain stage label");
  }

  @Test
  @Fn({"M03.F02.I01"})
  void assignTask_alreadyAssigned_doesNotAdvanceStage() {
    SampleReceiptEntity existing = entity("R-001");
    existing.setFlowStatus(FlowStatus.TASK_ASSIGNMENT);
    when(repo.findByTenantIdAndId(TENANT, "R-001")).thenReturn(Optional.of(existing));
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    var out = service.assignTask(TENANT, "R-001", new AssignTaskRequest().assigneeId("U-2"));
    assertEquals(FlowStatus.TASK_ASSIGNMENT, out.getFlowStatus());
  }

  private static io.xr.lab.platform.entity.ContractEntity contractStub(String id) {
    io.xr.lab.platform.entity.ContractEntity c = new io.xr.lab.platform.entity.ContractEntity();
    c.setId(id);
    c.setContractCode(id);
    c.setClientUnit("client");
    c.setProjectName("p");
    c.setConstructionUnit("c");
    c.setWitnessUnit("w");
    c.setWitness("witness-person");
    c.setStatus(io.xr.lab.shared.dto.ContractStatus.ACTIVE);
    c.setTenantId(TENANT);
    c.setCreatedAt("2026-08-18T10:00:00Z");
    c.setUpdatedAt("2026-08-18T10:00:00Z");
    return c;
  }

  private static SampleReceiptEntity entity(String id) {
    SampleReceiptEntity e = new SampleReceiptEntity();
    e.setId(id);
    e.setContractId("C-001");
    e.setCommissionCode("CM-001");
    e.setCommissionDate("2026-08-18");
    e.setCategoryCode("CAT-1");
    e.setReceivedBy("admin");
    e.setSampleSource("client");
    e.setTestCategory("concrete");
    e.setFlowStatus(FlowStatus.RECEIVING);
    e.setFlowHistory("[]");
    e.setTenantId(TENANT);
    e.setCreatedAt("2026-08-18T10:00:00Z");
    e.setUpdatedAt("2026-08-18T10:00:00Z");
    return e;
  }
}
