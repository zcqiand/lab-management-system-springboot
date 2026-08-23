package io.xr.lab.platform.service;

import io.xr.lab.platform.entity.CalculationMethodEntity;
import io.xr.lab.platform.entity.CalculationMethodKey;
import io.xr.lab.platform.mapper.CalculationMethodMapper;
import io.xr.lab.platform.repository.CalculationMethodRepository;
import io.xr.lab.shared.dto.CalculationMethod;
import io.xr.lab.shared.dto.CreateCalculationMethodRequest;
import io.xr.lab.shared.dto.UpdateCalculationMethodRequest;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

/** M06.F05 计算方法。平台级字典（无 tenant_id，per V012 备注）。复合主键 (object, parameter)。 */
@Service
public class CalculationMethodService {

  private final CalculationMethodRepository repo;

  public CalculationMethodService(CalculationMethodRepository repo) {
    this.repo = repo;
  }

  public List<CalculationMethod> list(String inspectionObjectCode, String inspectionParameterCode) {
    return repo.filter(inspectionObjectCode, inspectionParameterCode).stream()
        .map(CalculationMethodMapper::toDto)
        .toList();
  }

  public CalculationMethod get(String inspectionObjectCode, String inspectionParameterCode) {
    return CalculationMethodMapper.toDto(
        repo.findById(new CalculationMethodKey(inspectionObjectCode, inspectionParameterCode))
            .orElseThrow(
                () ->
                    new NoSuchElementException(
                        "CalculationMethod not found: "
                            + inspectionObjectCode
                            + "/"
                            + inspectionParameterCode)));
  }

  public CalculationMethod create(CreateCalculationMethodRequest req) {
    String now = nowIso();
    return CalculationMethodMapper.toDto(repo.save(CalculationMethodMapper.fromCreate(req, now)));
  }

  public CalculationMethod update(
      String inspectionObjectCode,
      String inspectionParameterCode,
      UpdateCalculationMethodRequest req) {
    CalculationMethodEntity entity =
        repo.findById(new CalculationMethodKey(inspectionObjectCode, inspectionParameterCode))
            .orElseThrow(
                () ->
                    new NoSuchElementException(
                        "CalculationMethod not found: "
                            + inspectionObjectCode
                            + "/"
                            + inspectionParameterCode));
    CalculationMethodMapper.applyUpdate(entity, req, nowIso());
    return CalculationMethodMapper.toDto(repo.save(entity));
  }

  public void delete(String inspectionObjectCode, String inspectionParameterCode) {
    CalculationMethodKey key =
        new CalculationMethodKey(inspectionObjectCode, inspectionParameterCode);
    if (!repo.existsById(key)) {
      throw new NoSuchElementException(
          "CalculationMethod not found: " + inspectionObjectCode + "/" + inspectionParameterCode);
    }
    repo.deleteById(key);
  }

  private static String nowIso() {
    return java.time.OffsetDateTime.now(java.time.ZoneOffset.UTC)
        .format(java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }
}
