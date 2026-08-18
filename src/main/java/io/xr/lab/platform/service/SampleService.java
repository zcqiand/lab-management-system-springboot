package io.xr.lab.platform.service;

import io.xr.lab.platform.mapper.SampleMapper;
import io.xr.lab.platform.repository.SampleReceiptRepository;
import io.xr.lab.platform.repository.SampleRepository;
import io.xr.lab.shared.dto.CreateSampleRequest;
import io.xr.lab.shared.dto.Sample;
import io.xr.lab.shared.dto.UpdateSampleRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.stereotype.Service;

/** M03.F02/F03 — 样品。tenant-scoped + receipt FK 校验。 */
@Service
public class SampleService {

  private final SampleRepository repo;
  private final SampleReceiptRepository receiptRepo;

  public SampleService(SampleRepository repo, SampleReceiptRepository receiptRepo) {
    this.repo = repo;
    this.receiptRepo = receiptRepo;
  }

  public List<Sample> list(String tenantId, String receiptId, String keyword) {
    return repo.filter(tenantId, n(receiptId), n(keyword)).stream()
        .map(SampleMapper::toDto)
        .toList();
  }

  public Sample get(String tenantId, String id) {
    return SampleMapper.toDto(
        repo.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new NoSuchElementException("Sample not found: " + id)));
  }

  public Sample create(String tenantId, CreateSampleRequest req) {
    if (!receiptRepo.findByTenantIdAndId(tenantId, req.getReceiptId()).isPresent()) {
      throw new NoSuchElementException("Receipt not found: " + req.getReceiptId());
    }
    String now = nowIso();
    return SampleMapper.toDto(repo.save(SampleMapper.fromCreate(req, newId(), tenantId, now)));
  }

  public Sample update(String tenantId, String id, UpdateSampleRequest req) {
    var entity =
        repo.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new NoSuchElementException("Sample not found: " + id));
    SampleMapper.applyUpdate(entity, req, nowIso());
    return SampleMapper.toDto(repo.save(entity));
  }

  public void delete(String tenantId, String id) {
    repo.delete(
        repo.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new NoSuchElementException("Sample not found: " + id)));
  }

  private static String n(String s) {
    return s == null ? "" : s;
  }

  private static String nowIso() {
    return java.time.OffsetDateTime.now(java.time.ZoneOffset.UTC)
        .format(java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }

  private static String newId() {
    return "S-" + UUID.randomUUID().toString();
  }
}
