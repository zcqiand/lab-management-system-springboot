package io.xr.lab.platform.service;

import io.xr.lab.platform.entity.ParamInterfaceEntity;
import io.xr.lab.platform.mapper.ParamInterfaceMapper;
import io.xr.lab.platform.repository.ParamInterfaceRepository;
import io.xr.lab.shared.dto.CreateParamInterfaceRequest;
import io.xr.lab.shared.dto.ParamInterface;
import io.xr.lab.shared.dto.UpdateParamInterfaceRequest;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

/** M06.F08 参数界面（5 子项 CRUD）。平台级字典（无 tenant_id，per V012 备注）。 */
@Service
public class ParamInterfaceService {

  private final ParamInterfaceRepository repo;

  public ParamInterfaceService(ParamInterfaceRepository repo) {
    this.repo = repo;
  }

  public List<ParamInterface> list(String keyword) {
    return repo.filter(n(keyword)).stream().map(ParamInterfaceMapper::toDto).toList();
  }

  public ParamInterface get(String code) {
    return ParamInterfaceMapper.toDto(
        repo.findById(code)
            .orElseThrow(() -> new NoSuchElementException("ParamInterface not found: " + code)));
  }

  public ParamInterface create(CreateParamInterfaceRequest req) {
    String now = nowIso();
    return ParamInterfaceMapper.toDto(repo.save(ParamInterfaceMapper.fromCreate(req, now)));
  }

  public ParamInterface update(String code, UpdateParamInterfaceRequest req) {
    ParamInterfaceEntity entity =
        repo.findById(code)
            .orElseThrow(() -> new NoSuchElementException("ParamInterface not found: " + code));
    ParamInterfaceMapper.applyUpdate(entity, req, nowIso());
    return ParamInterfaceMapper.toDto(repo.save(entity));
  }

  public void delete(String code) {
    if (!repo.existsById(code)) {
      throw new NoSuchElementException("ParamInterface not found: " + code);
    }
    repo.deleteById(code);
  }

  private static String nowIso() {
    return java.time.OffsetDateTime.now(java.time.ZoneOffset.UTC)
        .format(java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }

  private static String n(String s) {
    return s == null ? "" : s;
  }
}
