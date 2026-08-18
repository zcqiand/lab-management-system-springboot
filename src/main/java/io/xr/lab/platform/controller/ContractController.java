package io.xr.lab.platform.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.xr.lab.platform.directory.ConfigUserDirectory;
import io.xr.lab.platform.service.ContractService;
import io.xr.lab.shared.api.ContractsApi;
import io.xr.lab.shared.dto.Contract;
import io.xr.lab.shared.dto.ContractStatus;
import io.xr.lab.shared.dto.ContractsListContracts200Response;
import io.xr.lab.shared.dto.CreateContractRequest;
import io.xr.lab.shared.dto.UpdateContractRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** M02.F01 合同（B3，5 端点）。tenant-scoped。 */
@RestController
public class ContractController implements ContractsApi {

  private final ContractService service;
  private final ConfigUserDirectory directory;

  @SuppressFBWarnings(
      value = "EI_EXPOSE_REP2",
      justification = "Spring DI singleton: 控制器按规范持有 service / directory 的共享 bean 引用。")
  public ContractController(ContractService service, ConfigUserDirectory directory) {
    this.service = service;
    this.directory = directory;
  }

  @Override
  @SuppressFBWarnings(
      value = "EI_EXPOSE_REP2",
      justification = "Spring DI singleton: 控制器按规范持有 service / directory 的共享 bean 引用。")
  public ResponseEntity<ContractsListContracts200Response> contractsListContracts(
      Integer page, Integer pageSize, String keyword, ContractStatus status) {
    String tenant = InspectionCatalogController.currentTenantIdOrDefaultStatic(directory);
    List<Contract> list = service.list(tenant, keyword, status);
    return ResponseEntity.ok(new ContractsListContracts200Response().items(list));
  }

  @Override
  public ResponseEntity<Contract> contractsGetContract(String id) {
    return ResponseEntity.ok(
        service.get(InspectionCatalogController.currentTenantIdOrDefaultStatic(directory), id));
  }

  @Override
  public ResponseEntity<Contract> contractsCreateContract(
      CreateContractRequest createContractRequest) {
    return ResponseEntity.ok(
        service.create(
            InspectionCatalogController.currentTenantIdOrDefaultStatic(directory),
            createContractRequest));
  }

  @Override
  public ResponseEntity<Contract> contractsUpdateContract(
      String id, UpdateContractRequest updateContractRequest) {
    return ResponseEntity.ok(
        service.update(
            InspectionCatalogController.currentTenantIdOrDefaultStatic(directory),
            id,
            updateContractRequest));
  }

  @Override
  public ResponseEntity<Void> contractsDeleteContract(String id) {
    service.delete(InspectionCatalogController.currentTenantIdOrDefaultStatic(directory), id);
    return ResponseEntity.noContent().build();
  }
}
