package io.xr.lab.platform.service;

import io.xr.lab.platform.entity.TechnicalRequirementEntity;
import io.xr.lab.platform.entity.TechnicalRequirementKey;
import io.xr.lab.platform.mapper.TechnicalRequirementMapper;
import io.xr.lab.platform.repository.TechnicalRequirementRepository;
import io.xr.lab.shared.dto.CreateTechnicalRequirementRequest;
import io.xr.lab.shared.dto.RequirementVerificationStatus;
import io.xr.lab.shared.dto.TechnicalRequirement;
import io.xr.lab.shared.dto.UpdateTechnicalRequirementRequest;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

/**
 * M06.F06 技术要求。PK 是业务三键 (object, parameter, judgmentStandard)；tenant-scoped（V012）。
 *
 * <p>list 支持 4 过滤（object/parameter/standard/status），按 tenant 收口；get/delete 按业务三键 + tenant。
 */
@Service
public class TechnicalRequirementService {

  private final TechnicalRequirementRepository repo;

  public TechnicalRequirementService(TechnicalRequirementRepository repo) {
    this.repo = repo;
  }

  public List<TechnicalRequirement> list(
      String tenantId,
      String inspectionObjectCode,
      String inspectionParameterCode,
      String judgmentStandardCode,
      RequirementVerificationStatus status) {
    return repo
        .filter(
            tenantId, inspectionObjectCode, inspectionParameterCode, judgmentStandardCode, status)
        .stream()
        .map(TechnicalRequirementMapper::toDto)
        .toList();
  }

  public TechnicalRequirement get(
      String tenantId,
      String inspectionObjectCode,
      String inspectionParameterCode,
      String judgmentStandardCode) {
    TechnicalRequirementKey key =
        new TechnicalRequirementKey(
            tenantId, inspectionObjectCode, inspectionParameterCode, judgmentStandardCode);
    return TechnicalRequirementMapper.toDto(
        repo.findById(key)
            .orElseThrow(
                () ->
                    new NoSuchElementException(
                        "TechnicalRequirement not found: "
                            + inspectionObjectCode
                            + "/"
                            + inspectionParameterCode
                            + "/"
                            + judgmentStandardCode)));
  }

  public TechnicalRequirement create(CreateTechnicalRequirementRequest req, String tenantId) {
    String now = nowIso();
    return TechnicalRequirementMapper.toDto(
        repo.save(TechnicalRequirementMapper.fromCreate(req, tenantId, now)));
  }

  public TechnicalRequirement update(
      String tenantId,
      String inspectionObjectCode,
      String inspectionParameterCode,
      String judgmentStandardCode,
      UpdateTechnicalRequirementRequest req) {
    TechnicalRequirementEntity entity =
        repo.findById(
                new TechnicalRequirementKey(
                    tenantId, inspectionObjectCode, inspectionParameterCode, judgmentStandardCode))
            .orElseThrow(
                () ->
                    new NoSuchElementException(
                        "TechnicalRequirement not found: "
                            + inspectionObjectCode
                            + "/"
                            + inspectionParameterCode
                            + "/"
                            + judgmentStandardCode));
    TechnicalRequirementMapper.applyUpdate(entity, req, nowIso());
    return TechnicalRequirementMapper.toDto(repo.save(entity));
  }

  public void delete(
      String tenantId,
      String inspectionObjectCode,
      String inspectionParameterCode,
      String judgmentStandardCode) {
    TechnicalRequirementKey key =
        new TechnicalRequirementKey(
            tenantId, inspectionObjectCode, inspectionParameterCode, judgmentStandardCode);
    if (!repo.existsById(key)) {
      throw new NoSuchElementException(
          "TechnicalRequirement not found: "
              + inspectionObjectCode
              + "/"
              + inspectionParameterCode
              + "/"
              + judgmentStandardCode);
    }
    repo.deleteById(key);
  }

  private static String nowIso() {
    return java.time.OffsetDateTime.now(java.time.ZoneOffset.UTC)
        .format(java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }
}
