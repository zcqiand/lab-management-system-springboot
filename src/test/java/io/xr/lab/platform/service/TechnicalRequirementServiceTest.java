package io.xr.lab.platform.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.xr.harness.junit.Fn;
import io.xr.lab.platform.entity.TechnicalRequirementEntity;
import io.xr.lab.platform.entity.TechnicalRequirementKey;
import io.xr.lab.platform.repository.TechnicalRequirementRepository;
import io.xr.lab.shared.dto.CreateTechnicalRequirementRequest;
import io.xr.lab.shared.dto.RequirementComparison;
import io.xr.lab.shared.dto.RequirementJudgmentMode;
import io.xr.lab.shared.dto.RequirementValueType;
import io.xr.lab.shared.dto.RequirementVerificationStatus;
import io.xr.lab.shared.dto.TechnicalRequirement;
import io.xr.lab.shared.dto.UpdateTechnicalRequirementRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/** M06.F06 技术要求 5 子项。tenant-scoped；4 过滤参数。镜像 msw technicalRequirementsHandlers 语义。 */
class TechnicalRequirementServiceTest {

  private static final String TENANT = "TENANT-001";

  private TechnicalRequirementRepository repo;
  private TechnicalRequirementService service;

  @BeforeEach
  void setUp() {
    repo = org.mockito.Mockito.mock(TechnicalRequirementRepository.class);
    service = new TechnicalRequirementService(repo);
  }

  // M06.F06.I01 list

  @Test
  @Fn({"M06.F06.I01"})
  void list_passesFiltersThroughAndMaps() {
    TechnicalRequirementEntity row = entity(TENANT, "OBJ-1", "IP-1", "GB-1");
    row.setVerificationStatus(RequirementVerificationStatus.VERIFIED);
    when(repo.filter(TENANT, "OBJ-1", "IP-1", null, RequirementVerificationStatus.VERIFIED))
        .thenReturn(List.of(row));
    List<TechnicalRequirement> out =
        service.list(TENANT, "OBJ-1", "IP-1", null, RequirementVerificationStatus.VERIFIED);
    assertEquals(1, out.size());
    assertEquals(RequirementVerificationStatus.VERIFIED, out.get(0).getVerificationStatus());
  }

  @Test
  @Fn({"M06.F06.I01"})
  void list_noFilters_returnsAllForTenant() {
    when(repo.filter(TENANT, null, null, null, null))
        .thenReturn(
            List.of(
                entity(TENANT, "OBJ-1", "IP-1", "GB-1"), entity(TENANT, "OBJ-2", "IP-1", "GB-2")));
    assertEquals(2, service.list(TENANT, null, null, null, null).size());
  }

  // M06.F06.I02 get

  @Test
  @Fn({"M06.F06.I02"})
  void get_returnsByCompositeKey() {
    TechnicalRequirementEntity e = entity(TENANT, "OBJ-1", "IP-1", "GB-1");
    when(repo.findById(new TechnicalRequirementKey(TENANT, "OBJ-1", "IP-1", "GB-1")))
        .thenReturn(Optional.of(e));
    TechnicalRequirement out = service.get(TENANT, "OBJ-1", "IP-1", "GB-1");
    assertEquals("GB-1", out.getJudgmentStandardCode());
  }

  @Test
  @Fn({"M06.F06.I02"})
  void get_missing_throws404() {
    when(repo.findById(any())).thenReturn(Optional.empty());
    assertThrows(NoSuchElementException.class, () -> service.get(TENANT, "X", "Y", "Z"));
  }

  // M06.F06.I03 create

  @Test
  @Fn({"M06.F06.I03"})
  void create_appliesTenantAndStampsTimes() {
    CreateTechnicalRequirementRequest req =
        new CreateTechnicalRequirementRequest("OBJ-1", "IP-1", "GB-1");
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    TechnicalRequirement out = service.create(req, TENANT);
    ArgumentCaptor<TechnicalRequirementEntity> captor =
        ArgumentCaptor.forClass(TechnicalRequirementEntity.class);
    verify(repo).save(captor.capture());
    TechnicalRequirementEntity saved = captor.getValue();
    assertEquals(TENANT, saved.getTenantId());
    assertEquals(RequirementValueType.NUMERIC, saved.getValueType()); // default
    assertEquals(RequirementComparison.u, saved.getComparison()); // default '≥'
    assertEquals(RequirementJudgmentMode.MANUAL, saved.getJudgmentMode()); // default
    assertEquals(RequirementVerificationStatus.DRAFT, saved.getVerificationStatus()); // default
    assertEquals(saved.getCreatedAt(), saved.getUpdatedAt());
    assertEquals("OBJ-1", out.getInspectionObjectCode());
  }

  // M06.F06.I04 update

  @Test
  @Fn({"M06.F06.I04"})
  void update_onlyProvidedFieldsChange() {
    TechnicalRequirementEntity existing = entity(TENANT, "OBJ-1", "IP-1", "GB-1");
    when(repo.findById(new TechnicalRequirementKey(TENANT, "OBJ-1", "IP-1", "GB-1")))
        .thenReturn(Optional.of(existing));
    when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));
    TechnicalRequirement out =
        service.update(
            TENANT,
            "OBJ-1",
            "IP-1",
            "GB-1",
            new UpdateTechnicalRequirementRequest()
                .verificationStatus(RequirementVerificationStatus.VERIFIED)
                .remark("audited 2026-08"));
    assertEquals(RequirementVerificationStatus.VERIFIED, out.getVerificationStatus());
    assertEquals("audited 2026-08", out.getRemark());
    // 未传的 judgmentMode 维持原样
    assertEquals(RequirementJudgmentMode.MANUAL, out.getJudgmentMode());
  }

  // M06.F06.I05 delete

  @Test
  @Fn({"M06.F06.I05"})
  void delete_existing_removesRow() {
    TechnicalRequirementKey key = new TechnicalRequirementKey(TENANT, "OBJ-1", "IP-1", "GB-1");
    when(repo.existsById(key)).thenReturn(true);
    service.delete(TENANT, "OBJ-1", "IP-1", "GB-1");
    verify(repo, times(1)).deleteById(key);
  }

  @Test
  @Fn({"M06.F06.I05"})
  void delete_missing_throws404() {
    when(repo.existsById(any())).thenReturn(false);
    assertThrows(NoSuchElementException.class, () -> service.delete(TENANT, "X", "Y", "Z"));
    verify(repo, never()).deleteById(any());
  }

  private static TechnicalRequirementEntity entity(
      String tenant, String obj, String param, String std) {
    TechnicalRequirementEntity e = new TechnicalRequirementEntity();
    e.setTenantId(tenant);
    e.setInspectionObjectCode(obj);
    e.setInspectionParameterCode(param);
    e.setJudgmentStandardCode(std);
    e.setValueType(RequirementValueType.NUMERIC);
    e.setComparison(RequirementComparison.u);
    e.setJudgmentMode(RequirementJudgmentMode.MANUAL);
    e.setVerificationStatus(RequirementVerificationStatus.DRAFT);
    e.setSortOrder(0);
    e.setCreatedAt("2026-08-18T10:00:00Z");
    e.setUpdatedAt("2026-08-18T10:00:00Z");
    return e;
  }
}
