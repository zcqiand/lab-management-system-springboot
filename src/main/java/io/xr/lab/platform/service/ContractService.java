package io.xr.lab.platform.service;

import io.xr.lab.platform.mapper.ContractMapper;
import io.xr.lab.platform.repository.ContractRepository;
import io.xr.lab.shared.dto.Contract;
import io.xr.lab.shared.dto.ContractStatus;
import io.xr.lab.shared.dto.CreateContractRequest;
import io.xr.lab.shared.dto.UpdateContractRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.stereotype.Service;

/** M02.F01 合同管理。tenant-scoped（V012）。 */
@Service
public class ContractService {

  private final ContractRepository repo;

  public ContractService(ContractRepository repo) {
    this.repo = repo;
  }

  public List<Contract> list(String tenantId, String keyword, ContractStatus status) {
    return repo.filter(tenantId, n(keyword), status).stream().map(ContractMapper::toDto).toList();
  }

  public Contract get(String tenantId, String id) {
    return ContractMapper.toDto(
        repo.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new NoSuchElementException("Contract not found: " + id)));
  }

  public Contract create(String tenantId, CreateContractRequest req) {
    String now = nowIso();
    return ContractMapper.toDto(repo.save(ContractMapper.fromCreate(req, newId(), tenantId, now)));
  }

  public Contract update(String tenantId, String id, UpdateContractRequest req) {
    var entity =
        repo.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new NoSuchElementException("Contract not found: " + id));
    ContractMapper.applyUpdate(entity, req, nowIso());
    return ContractMapper.toDto(repo.save(entity));
  }

  public void delete(String tenantId, String id) {
    var entity =
        repo.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new NoSuchElementException("Contract not found: " + id));
    repo.delete(entity);
  }

  private static String n(String s) {
    return s == null ? "" : s;
  }

  private static String nowIso() {
    return java.time.OffsetDateTime.now(java.time.ZoneOffset.UTC)
        .format(java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }

  private static String newId() {
    return "C-" + UUID.randomUUID().toString();
  }
}
