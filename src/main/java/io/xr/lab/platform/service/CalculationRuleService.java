package io.xr.lab.platform.service;

import io.xr.lab.platform.entity.CalculationRuleEntity;
import io.xr.lab.platform.entity.CalculationRuleKey;
import io.xr.lab.platform.mapper.CalculationRuleMapper;
import io.xr.lab.platform.repository.CalculationRuleRepository;
import io.xr.lab.shared.dto.CalculationRule;
import io.xr.lab.shared.dto.CreateCalculationRuleRequest;
import io.xr.lab.shared.dto.UpdateCalculationRuleRequest;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

/** M06.F05 计算规则。平台级字典（无 tenant_id，per V012 备注）。复合主键 (object, parameter)。 */
@Service
public class CalculationRuleService {

  private final CalculationRuleRepository repo;

  public CalculationRuleService(CalculationRuleRepository repo) {
    this.repo = repo;
  }

  public List<CalculationRule> list(String inspectionObjectCode, String inspectionParameterCode) {
    return repo.filter(inspectionObjectCode, inspectionParameterCode).stream()
        .map(CalculationRuleMapper::toDto)
        .toList();
  }

  public CalculationRule get(String inspectionObjectCode, String inspectionParameterCode) {
    return CalculationRuleMapper.toDto(
        repo.findById(new CalculationRuleKey(inspectionObjectCode, inspectionParameterCode))
            .orElseThrow(
                () ->
                    new NoSuchElementException(
                        "CalculationRule not found: "
                            + inspectionObjectCode
                            + "/"
                            + inspectionParameterCode)));
  }

  public CalculationRule create(CreateCalculationRuleRequest req) {
    String now = nowIso();
    return CalculationRuleMapper.toDto(repo.save(CalculationRuleMapper.fromCreate(req, now)));
  }

  public CalculationRule update(
      String inspectionObjectCode,
      String inspectionParameterCode,
      UpdateCalculationRuleRequest req) {
    CalculationRuleEntity entity =
        repo.findById(new CalculationRuleKey(inspectionObjectCode, inspectionParameterCode))
            .orElseThrow(
                () ->
                    new NoSuchElementException(
                        "CalculationRule not found: "
                            + inspectionObjectCode
                            + "/"
                            + inspectionParameterCode));
    CalculationRuleMapper.applyUpdate(entity, req, nowIso());
    return CalculationRuleMapper.toDto(repo.save(entity));
  }

  public void delete(String inspectionObjectCode, String inspectionParameterCode) {
    CalculationRuleKey key = new CalculationRuleKey(inspectionObjectCode, inspectionParameterCode);
    if (!repo.existsById(key)) {
      throw new NoSuchElementException(
          "CalculationRule not found: " + inspectionObjectCode + "/" + inspectionParameterCode);
    }
    repo.deleteById(key);
  }

  private static String nowIso() {
    return java.time.OffsetDateTime.now(java.time.ZoneOffset.UTC)
        .format(java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }
}
