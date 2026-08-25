package io.xr.lab.platform.controller;

import io.xr.lab.platform.directory.ConfigUserDirectory;
import io.xr.lab.platform.service.TestRecordService;
import io.xr.lab.shared.api.TestRecordsApi;
import io.xr.lab.shared.dto.CreateTestRecordRequest;
import io.xr.lab.shared.dto.TestRecord;
import io.xr.lab.shared.dto.TestRecordsListTestRecords200Response;
import io.xr.lab.shared.dto.TestRecordsSetVerdictRequest;
import io.xr.lab.shared.dto.UpdateTestRecordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** M03.F03 检测记录 controller（B9.3）。6 端点。tenant 收口走 JWT claim。 */
@RestController
public class TestRecordController implements TestRecordsApi {

  private final TestRecordService service;
  private final ConfigUserDirectory directory;

  public TestRecordController(TestRecordService service, ConfigUserDirectory directory) {
    this.service = service;
    this.directory = directory;
  }

  @Override
  public ResponseEntity<TestRecord> testRecordsCreateTestRecord(CreateTestRecordRequest body) {
    return ResponseEntity.ok(
        service.create(
            InspectionCatalogController.currentTenantIdOrDefaultStatic(directory), body));
  }

  @Override
  public ResponseEntity<Void> testRecordsDeleteTestRecord(String id) {
    service.delete(InspectionCatalogController.currentTenantIdOrDefaultStatic(directory), id);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<TestRecord> testRecordsGetTestRecord(String id) {
    return ResponseEntity.ok(
        service.get(InspectionCatalogController.currentTenantIdOrDefaultStatic(directory), id));
  }

  @Override
  public ResponseEntity<TestRecordsListTestRecords200Response> testRecordsListTestRecords(
      Integer page, Integer pageSize, String sampleId, String parameterCode) {
    // @entry M03.F03.I01
    String tenantId = InspectionCatalogController.currentTenantIdOrDefaultStatic(directory);
    var items = service.list(tenantId, sampleId);
    var body = new TestRecordsListTestRecords200Response();
    body.setItems(items);
    body.setPage(page != null ? page : Integer.valueOf(1));
    body.setPageSize(pageSize != null ? pageSize : Integer.valueOf(items.size()));
    body.setTotal((long) items.size());
    return ResponseEntity.ok(body);
  }

  @Override
  public ResponseEntity<TestRecord> testRecordsSetVerdict(
      String id, TestRecordsSetVerdictRequest body) {
    return ResponseEntity.ok(
        service.setVerdict(
            InspectionCatalogController.currentTenantIdOrDefaultStatic(directory),
            id,
            body == null ? null : body.getVerdict()));
  }

  @Override
  public ResponseEntity<TestRecord> testRecordsUpdateTestRecord(
      String id, UpdateTestRecordRequest body) {
    return ResponseEntity.ok(
        service.update(
            InspectionCatalogController.currentTenantIdOrDefaultStatic(directory), id, body));
  }
}
