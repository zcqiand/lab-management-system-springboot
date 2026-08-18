package io.xr.lab.platform.service;

import io.xr.lab.platform.entity.TestRecordEntity;
import io.xr.lab.platform.mapper.TestRecordMapper;
import io.xr.lab.platform.repository.TestRecordRepository;
import io.xr.lab.shared.dto.CreateTestRecordRequest;
import io.xr.lab.shared.dto.TestRecord;
import io.xr.lab.shared.dto.UpdateTestRecordRequest;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.stereotype.Service;

/** M03.F03 检测记录数据录入。tenant-scoped CRUD + 人工改判 setVerdict。 */
@Service
public class TestRecordService {

  private final TestRecordRepository repo;

  public TestRecordService(TestRecordRepository repo) {
    this.repo = repo;
  }

  public List<TestRecord> list(String tenantId, String sampleId) {
    return repo.filter(n(tenantId), n(sampleId)).stream().map(TestRecordMapper::toDto).toList();
  }

  public TestRecord get(String tenantId, String id) {
    return TestRecordMapper.toDto(
        repo.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new NoSuchElementException("TestRecord not found: " + id)));
  }

  public TestRecord create(String tenantId, CreateTestRecordRequest req) {
    String now = nowIso();
    return TestRecordMapper.toDto(
        repo.save(TestRecordMapper.fromCreate(req, newId(), tenantId, now)));
  }

  public TestRecord update(String tenantId, String id, UpdateTestRecordRequest req) {
    TestRecordEntity entity =
        repo.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new NoSuchElementException("TestRecord not found: " + id));
    TestRecordMapper.applyUpdate(entity, req, nowIso());
    return TestRecordMapper.toDto(repo.save(entity));
  }

  public void delete(String tenantId, String id) {
    TestRecordEntity entity =
        repo.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new NoSuchElementException("TestRecord not found: " + id));
    repo.delete(entity);
  }

  /** 人工改判 verdict（M03.F05/F06 报告流程可能触发）。 */
  public TestRecord setVerdict(String tenantId, String id, String verdict) {
    TestRecordEntity entity =
        repo.findByTenantIdAndId(tenantId, id)
            .orElseThrow(() -> new NoSuchElementException("TestRecord not found: " + id));
    entity.setVerdict(verdict);
    entity.setUpdatedAt(nowIso());
    return TestRecordMapper.toDto(repo.save(entity));
  }

  private static String newId() {
    return "TR-" + UUID.randomUUID().toString();
  }

  private static String nowIso() {
    return java.time.OffsetDateTime.now(java.time.ZoneOffset.UTC)
        .format(java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }

  private static String n(String s) {
    return s == null ? "" : s;
  }
}
