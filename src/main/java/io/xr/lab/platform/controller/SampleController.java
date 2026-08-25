package io.xr.lab.platform.controller;

import io.xr.lab.platform.directory.ConfigUserDirectory;
import io.xr.lab.platform.service.SampleService;
import io.xr.lab.shared.api.SamplesApi;
import io.xr.lab.shared.dto.CreateSampleRequest;
import io.xr.lab.shared.dto.Sample;
import io.xr.lab.shared.dto.SamplesListSamples200Response;
import io.xr.lab.shared.dto.UpdateSampleRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** M03.F03 样品（B3，5 端点）。tenant-scoped，receipt FK 校验。 */
@RestController
public class SampleController implements SamplesApi {

  private final SampleService service;
  private final ConfigUserDirectory directory;

  public SampleController(SampleService service, ConfigUserDirectory directory) {
    this.service = service;
    this.directory = directory;
  }

  @Override
  public ResponseEntity<SamplesListSamples200Response> samplesListSamples(
      Integer page, Integer pageSize, String receiptId, String keyword) {
    String tenant = InspectionCatalogController.currentTenantIdOrDefaultStatic(directory);
    List<Sample> list = service.list(tenant, receiptId, keyword);
    return ResponseEntity.ok(new SamplesListSamples200Response().items(list));
  }

  @Override
  public ResponseEntity<Sample> samplesGetSample(String id) {
    return ResponseEntity.ok(
        service.get(InspectionCatalogController.currentTenantIdOrDefaultStatic(directory), id));
  }

  @Override
  public ResponseEntity<Sample> samplesCreateSample(CreateSampleRequest createSampleRequest) {
    return ResponseEntity.ok(
        service.create(
            InspectionCatalogController.currentTenantIdOrDefaultStatic(directory),
            createSampleRequest));
  }

  @Override
  public ResponseEntity<Sample> samplesUpdateSample(
      String id, UpdateSampleRequest updateSampleRequest) {
    return ResponseEntity.ok(
        service.update(
            InspectionCatalogController.currentTenantIdOrDefaultStatic(directory),
            id,
            updateSampleRequest));
  }

  @Override
  public ResponseEntity<Void> samplesDeleteSample(String id) {
    service.delete(InspectionCatalogController.currentTenantIdOrDefaultStatic(directory), id);
    return ResponseEntity.noContent().build();
  }
}
