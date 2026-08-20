package io.xr.lab.platform.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.xr.lab.platform.directory.ConfigUserDirectory;
import io.xr.lab.platform.service.CatalogService;
import io.xr.lab.shared.api.InspectionCatalogApi;
import io.xr.lab.shared.dto.CatalogListBrands200Response;
import io.xr.lab.shared.dto.CatalogListGrades200Response;
import io.xr.lab.shared.dto.CatalogListModels200Response;
import io.xr.lab.shared.dto.CatalogListSpecs200Response;
import io.xr.lab.shared.dto.CreateCatalogEntryRequest;
import io.xr.lab.shared.dto.InspectionBrand;
import io.xr.lab.shared.dto.InspectionGrade;
import io.xr.lab.shared.dto.InspectionModel;
import io.xr.lab.shared.dto.InspectionSpec;
import io.xr.lab.shared.dto.UpdateCatalogEntryRequest;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

/**
 * M04.F06/07/08/09 码表 controller（B2）。实现生成的 {@link InspectionCatalogApi}（16 端点）。
 *
 * <p>tenant_id 从 JWT claim 取（{@link #currentTenantIdOrDefault}），未带 token / 无 claim 时 dev fallback
 * TENANT-001。 业务 全部在 {@link CatalogService}。
 *
 * <p>4 个 list GET 端点返 OpenAPI `Page<T>` 包裹（items / page / pageSize / total）， 与 shared TypeSpec
 * `Page<T>` 一致。
 */
@RestController
public class InspectionCatalogController implements InspectionCatalogApi {

  private static final String DEFAULT_TENANT = "TENANT-001";

  private final CatalogService service;
  private final ConfigUserDirectory directory;

  @SuppressFBWarnings(
      value = "EI_EXPOSE_REP2",
      justification = "Spring DI singleton: 控制器按规范持有 service / directory 的共享 bean 引用。")
  public InspectionCatalogController(CatalogService service, ConfigUserDirectory directory) {
    this.service = service;
    this.directory = directory;
  }

  // === Brand (M04.F09) ===

  @Override
  public ResponseEntity<CatalogListBrands200Response> catalogListBrands(
      Integer page, Integer pageSize, String inspectionObjectCode, String keyword) {
    List<InspectionBrand> list =
        service.listBrands(currentTenantIdOrDefault(), inspectionObjectCode, keyword);
    int effectivePage = page == null ? 1 : page;
    int effectivePageSize = pageSize == null ? list.size() : pageSize;
    return ResponseEntity.ok(
        new CatalogListBrands200Response()
            .items(list)
            .page(effectivePage)
            .pageSize(effectivePageSize)
            .total((long) list.size()));
  }

  @Override
  public ResponseEntity<InspectionBrand> catalogCreateBrand(
      CreateCatalogEntryRequest createCatalogEntryRequest) {
    return ResponseEntity.ok(
        service.createBrand(currentTenantIdOrDefault(), createCatalogEntryRequest));
  }

  @Override
  public ResponseEntity<InspectionBrand> catalogUpdateBrand(
      String code, UpdateCatalogEntryRequest updateCatalogEntryRequest) {
    return ResponseEntity.ok(
        service.updateBrand(currentTenantIdOrDefault(), code, updateCatalogEntryRequest));
  }

  @Override
  public ResponseEntity<Void> catalogDeleteBrand(String code) {
    service.deleteBrand(currentTenantIdOrDefault(), code);
    return ResponseEntity.noContent().build();
  }

  // === Grade (M04.F08) ===

  @Override
  public ResponseEntity<CatalogListGrades200Response> catalogListGrades(
      Integer page, Integer pageSize, String inspectionObjectCode, String keyword) {
    List<InspectionGrade> list =
        service.listGrades(currentTenantIdOrDefault(), inspectionObjectCode, keyword);
    int effectivePage = page == null ? 1 : page;
    int effectivePageSize = pageSize == null ? list.size() : pageSize;
    return ResponseEntity.ok(
        new CatalogListGrades200Response()
            .items(list)
            .page(effectivePage)
            .pageSize(effectivePageSize)
            .total((long) list.size()));
  }

  @Override
  public ResponseEntity<InspectionGrade> catalogCreateGrade(
      CreateCatalogEntryRequest createCatalogEntryRequest) {
    return ResponseEntity.ok(
        service.createGrade(currentTenantIdOrDefault(), createCatalogEntryRequest));
  }

  @Override
  public ResponseEntity<InspectionGrade> catalogUpdateGrade(
      String code, UpdateCatalogEntryRequest updateCatalogEntryRequest) {
    return ResponseEntity.ok(
        service.updateGrade(currentTenantIdOrDefault(), code, updateCatalogEntryRequest));
  }

  @Override
  public ResponseEntity<Void> catalogDeleteGrade(String code) {
    service.deleteGrade(currentTenantIdOrDefault(), code);
    return ResponseEntity.noContent().build();
  }

  // === Model (M04.F06) ===

  @Override
  public ResponseEntity<CatalogListModels200Response> catalogListModels(
      Integer page, Integer pageSize, String inspectionObjectCode, String keyword) {
    List<InspectionModel> list =
        service.listModels(currentTenantIdOrDefault(), inspectionObjectCode, keyword);
    int effectivePage = page == null ? 1 : page;
    int effectivePageSize = pageSize == null ? list.size() : pageSize;
    return ResponseEntity.ok(
        new CatalogListModels200Response()
            .items(list)
            .page(effectivePage)
            .pageSize(effectivePageSize)
            .total((long) list.size()));
  }

  @Override
  public ResponseEntity<InspectionModel> catalogCreateModel(
      CreateCatalogEntryRequest createCatalogEntryRequest) {
    return ResponseEntity.ok(
        service.createModel(currentTenantIdOrDefault(), createCatalogEntryRequest));
  }

  @Override
  public ResponseEntity<InspectionModel> catalogUpdateModel(
      String code, UpdateCatalogEntryRequest updateCatalogEntryRequest) {
    return ResponseEntity.ok(
        service.updateModel(currentTenantIdOrDefault(), code, updateCatalogEntryRequest));
  }

  @Override
  public ResponseEntity<Void> catalogDeleteModel(String code) {
    service.deleteModel(currentTenantIdOrDefault(), code);
    return ResponseEntity.noContent().build();
  }

  // === Spec (M04.F07) ===

  @Override
  public ResponseEntity<CatalogListSpecs200Response> catalogListSpecs(
      Integer page, Integer pageSize, String inspectionObjectCode, String keyword) {
    List<InspectionSpec> list =
        service.listSpecs(currentTenantIdOrDefault(), inspectionObjectCode, keyword);
    int effectivePage = page == null ? 1 : page;
    int effectivePageSize = pageSize == null ? list.size() : pageSize;
    return ResponseEntity.ok(
        new CatalogListSpecs200Response()
            .items(list)
            .page(effectivePage)
            .pageSize(effectivePageSize)
            .total((long) list.size()));
  }

  @Override
  public ResponseEntity<InspectionSpec> catalogCreateSpec(
      CreateCatalogEntryRequest createCatalogEntryRequest) {
    return ResponseEntity.ok(
        service.createSpec(currentTenantIdOrDefault(), createCatalogEntryRequest));
  }

  @Override
  public ResponseEntity<InspectionSpec> catalogUpdateSpec(
      String code, UpdateCatalogEntryRequest updateCatalogEntryRequest) {
    return ResponseEntity.ok(
        service.updateSpec(currentTenantIdOrDefault(), code, updateCatalogEntryRequest));
  }

  @Override
  public ResponseEntity<Void> catalogDeleteSpec(String code) {
    service.deleteSpec(currentTenantIdOrDefault(), code);
    return ResponseEntity.noContent().build();
  }

  // === JWT claims helper (镜像 AuthController#currentClaims) ===

  private String currentTenantIdOrDefault() {
    Map<String, Object> claims = currentClaims();
    Object t = claims.get("tenant_id");
    if (t != null && !t.toString().isEmpty()) {
      return t.toString();
    }
    return directory.defaultTenant().getTenantId();
  }

  static Map<String, Object> currentClaims() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth instanceof JwtAuthenticationToken token && token.getToken() != null) {
      Jwt jwt = token.getToken();
      return jwt.getClaims();
    }
    return Map.of();
  }

  /** 静态访问 helper：其它 controller 复用，从 SecurityContext 读 tenant_id claim 缺省回退 directory 默认租户。 */
  public static String currentTenantIdOrDefaultStatic(ConfigUserDirectory directory) {
    Map<String, Object> claims = currentClaims();
    Object t = claims.get("tenant_id");
    if (t != null && !t.toString().isEmpty()) {
      return t.toString();
    }
    return directory.defaultTenant().getTenantId();
  }
}
