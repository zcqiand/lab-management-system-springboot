package io.xr.lab.platform.mapper;

import io.xr.lab.platform.entity.TestRecordEntity;
import io.xr.lab.shared.dto.CreateTestRecordRequest;
import io.xr.lab.shared.dto.TestRecord;
import io.xr.lab.shared.dto.UpdateTestRecordRequest;

/** 检测记录（M03.F03）DTO ↔ Entity。 */
public final class TestRecordMapper {

  private TestRecordMapper() {}

  public static TestRecord toDto(TestRecordEntity e) {
    return new TestRecord()
        .id(e.getId())
        .tenantId(e.getTenantId())
        .sampleId(e.getSampleId())
        .parameterCode(e.getParameterCode())
        .standardCode(e.getStandardCode())
        .requirementCode(e.getRequirementCode())
        .requirement(e.getRequirement())
        .result(e.getResult())
        .verdict(e.getVerdict())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt());
  }

  public static TestRecordEntity fromCreate(
      CreateTestRecordRequest req, String newId, String tenantId, String now) {
    if (req.getSampleId() == null
        || req.getParameterCode() == null
        || req.getRequirement() == null
        || req.getResult() == null) {
      throw new IllegalArgumentException(
          "sampleId, parameterCode, requirement and result are required");
    }
    TestRecordEntity e = new TestRecordEntity();
    e.setId(newId);
    e.setTenantId(tenantId);
    e.setSampleId(req.getSampleId());
    e.setParameterCode(req.getParameterCode());
    e.setStandardCode(req.getStandardCode());
    e.setRequirementCode(req.getRequirementCode());
    e.setRequirement(req.getRequirement());
    e.setResult(req.getResult());
    e.setVerdict(req.getVerdict());
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    return e;
  }

  public static void applyUpdate(TestRecordEntity e, UpdateTestRecordRequest req, String now) {
    if (req.getParameterCode() != null) {
      e.setParameterCode(req.getParameterCode());
    }
    if (req.getStandardCode() != null) {
      e.setStandardCode(req.getStandardCode());
    }
    if (req.getRequirementCode() != null) {
      e.setRequirementCode(req.getRequirementCode());
    }
    if (req.getRequirement() != null) {
      e.setRequirement(req.getRequirement());
    }
    if (req.getResult() != null) {
      e.setResult(req.getResult());
    }
    if (req.getVerdict() != null) {
      e.setVerdict(req.getVerdict());
    }
    e.setUpdatedAt(now);
  }
}
