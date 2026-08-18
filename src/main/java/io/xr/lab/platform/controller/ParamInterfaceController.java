package io.xr.lab.platform.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.xr.lab.platform.service.ParamInterfaceService;
import io.xr.lab.shared.api.ParamInterfacesApi;
import io.xr.lab.shared.dto.CreateParamInterfaceRequest;
import io.xr.lab.shared.dto.ParamInterface;
import io.xr.lab.shared.dto.ParamInterfaceLink;
import io.xr.lab.shared.dto.UpdateParamInterfaceRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * M06.F08 参数界面 controller（B5）。本批只实现 5 CRUD 端点（list/get/create/update/delete）。
 *
 * <p>link/unlink（参数↔界面）等下一批。
 */
@RestController
public class ParamInterfaceController implements ParamInterfacesApi {

  private final ParamInterfaceService service;

  @SuppressFBWarnings(
      value = "EI_EXPOSE_REP2",
      justification = "Spring DI singleton: 控制器按规范持有 service 的共享 bean 引用。")
  public ParamInterfaceController(ParamInterfaceService service) {
    this.service = service;
  }

  @Override
  public ResponseEntity<List<ParamInterface>> paramInterfacesListParamInterfaces(String keyword) {
    return ResponseEntity.ok(service.list(keyword));
  }

  @Override
  public ResponseEntity<ParamInterface> paramInterfacesGetParamInterface(String code) {
    return ResponseEntity.ok(service.get(code));
  }

  @Override
  public ResponseEntity<ParamInterface> paramInterfacesCreateParamInterface(
      CreateParamInterfaceRequest body) {
    return ResponseEntity.ok(service.create(body));
  }

  @Override
  public ResponseEntity<ParamInterface> paramInterfacesUpdateParamInterface(
      String code, UpdateParamInterfaceRequest body) {
    return ResponseEntity.ok(service.update(code, body));
  }

  @Override
  public ResponseEntity<Void> paramInterfacesDeleteParamInterface(String code) {
    service.delete(code);
    return ResponseEntity.noContent().build();
  }

  // === junction link/unlink（M06 — 下一批）===

  @Override
  public ResponseEntity<Void> paramInterfacesLinkParamInterface(ParamInterfaceLink body) {
    throw new UnsupportedOperationException("ParamInterface link endpoint deferred to next batch");
  }

  @Override
  public ResponseEntity<Void> paramInterfacesUnlinkParamInterface(
      io.xr.lab.shared.dto.ParamInterfacesUnlinkParamInterfaceRequest body) {
    throw new UnsupportedOperationException(
        "ParamInterface unlink endpoint deferred to next batch");
  }
}
