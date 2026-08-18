package io.xr.lab.platform.mapper;

import io.xr.lab.platform.entity.SampleEntity;
import io.xr.lab.shared.dto.CreateSampleRequest;
import io.xr.lab.shared.dto.Sample;
import io.xr.lab.shared.dto.UpdateSampleRequest;
import java.util.HashMap;
import java.util.Map;

/** M03.F02/F03 — 样品 DTO ↔ Entity。 */
public final class SampleMapper {

  private SampleMapper() {}

  public static Sample toDto(SampleEntity e) {
    return new Sample()
        .id(e.getId())
        .receiptId(e.getReceiptId())
        .sampleCode(e.getSampleCode())
        .sampleName(e.getSampleName())
        .model(e.getModel())
        .specification(e.getSpecification())
        .grade(e.getGrade())
        .brand(e.getBrand())
        .manufacturer(e.getManufacturer())
        .structuralPart(e.getStructuralPart())
        .representQuantity(e.getRepresentQuantity())
        .sampleQuantity(e.getSampleQuantity())
        .batchNumber(e.getBatchNumber())
        .supplyUnit(e.getSupplyUnit())
        .arrivalDate(e.getArrivalDate())
        .samplingDate(e.getSamplingDate())
        .curingCondition(e.getCuringCondition())
        .age(e.getAge())
        .ext(e.getExt())
        .remark(e.getRemark())
        .tenantId(e.getTenantId())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt());
  }

  public static SampleEntity fromCreate(
      CreateSampleRequest req, String id, String tenantId, String now) {
    SampleEntity e = new SampleEntity();
    e.setId(id);
    e.setTenantId(tenantId);
    e.setReceiptId(req.getReceiptId());
    e.setSampleCode(req.getSampleCode());
    e.setSampleName(req.getSampleName());
    e.setModel(req.getModel());
    e.setSpecification(req.getSpecification());
    e.setGrade(req.getGrade());
    e.setBrand(req.getBrand());
    e.setManufacturer(req.getManufacturer());
    e.setStructuralPart(req.getStructuralPart());
    e.setRepresentQuantity(req.getRepresentQuantity());
    e.setSampleQuantity(req.getSampleQuantity());
    e.setBatchNumber(req.getBatchNumber());
    e.setSupplyUnit(req.getSupplyUnit());
    e.setArrivalDate(req.getArrivalDate());
    e.setSamplingDate(req.getSamplingDate());
    e.setCuringCondition(req.getCuringCondition());
    e.setAge(req.getAge());
    Map<String, String> ext = req.getExt();
    e.setExt(ext != null ? ext : new HashMap<>());
    e.setRemark(req.getRemark());
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    return e;
  }

  public static void applyUpdate(SampleEntity e, UpdateSampleRequest req, String now) {
    if (req.getSampleName() != null) e.setSampleName(req.getSampleName());
    if (req.getModel() != null) e.setModel(req.getModel());
    if (req.getSpecification() != null) e.setSpecification(req.getSpecification());
    if (req.getGrade() != null) e.setGrade(req.getGrade());
    if (req.getBrand() != null) e.setBrand(req.getBrand());
    if (req.getManufacturer() != null) e.setManufacturer(req.getManufacturer());
    if (req.getStructuralPart() != null) e.setStructuralPart(req.getStructuralPart());
    if (req.getRepresentQuantity() != null) e.setRepresentQuantity(req.getRepresentQuantity());
    if (req.getSampleQuantity() != null) e.setSampleQuantity(req.getSampleQuantity());
    if (req.getBatchNumber() != null) e.setBatchNumber(req.getBatchNumber());
    if (req.getSupplyUnit() != null) e.setSupplyUnit(req.getSupplyUnit());
    if (req.getArrivalDate() != null) e.setArrivalDate(req.getArrivalDate());
    if (req.getSamplingDate() != null) e.setSamplingDate(req.getSamplingDate());
    if (req.getCuringCondition() != null) e.setCuringCondition(req.getCuringCondition());
    if (req.getAge() != null) e.setAge(req.getAge());
    if (req.getExt() != null) e.setExt(new HashMap<>(req.getExt()));
    if (req.getRemark() != null) e.setRemark(req.getRemark());
    e.setUpdatedAt(now);
  }
}
