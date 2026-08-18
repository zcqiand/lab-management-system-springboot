package io.xr.lab.platform.service;

import io.xr.lab.platform.entity.InspectionObjectEntity;
import io.xr.lab.platform.entity.InspectionParameterEntity;
import io.xr.lab.platform.entity.InspectionSpecialtyEntity;
import io.xr.lab.platform.entity.InspectionStandardEntity;
import io.xr.lab.platform.mapper.InspectionDictionaryMapper;
import io.xr.lab.platform.repository.InspectionObjectRepository;
import io.xr.lab.platform.repository.InspectionParameterRepository;
import io.xr.lab.platform.repository.InspectionSpecialtyRepository;
import io.xr.lab.platform.repository.InspectionStandardRepository;
import io.xr.lab.shared.dto.CreateInspectionObjectRequest;
import io.xr.lab.shared.dto.CreateInspectionParameterRequest;
import io.xr.lab.shared.dto.CreateInspectionSpecialtyRequest;
import io.xr.lab.shared.dto.CreateInspectionStandardRequest;
import io.xr.lab.shared.dto.InspectionObject;
import io.xr.lab.shared.dto.InspectionParameter;
import io.xr.lab.shared.dto.InspectionParameterSourceType;
import io.xr.lab.shared.dto.InspectionSpecialty;
import io.xr.lab.shared.dto.InspectionStandard;
import io.xr.lab.shared.dto.InspectionStandardStatus;
import io.xr.lab.shared.dto.UpdateInspectionObjectRequest;
import io.xr.lab.shared.dto.UpdateInspectionParameterRequest;
import io.xr.lab.shared.dto.UpdateInspectionSpecialtyRequest;
import io.xr.lab.shared.dto.UpdateInspectionStandardRequest;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

/**
 * M06 字典 3 实体（M06.F01 专项 / M06.F03 参数 / M06.F04 标准）— 共 12 子项。平台级字典（无 tenant_id， per V012
 * 备注），service 不读 JWT claims。
 */
@Service
public class InspectionDictionaryService {

  private final InspectionSpecialtyRepository specialtyRepo;
  private final InspectionObjectRepository objectRepo;
  private final InspectionParameterRepository parameterRepo;
  private final InspectionStandardRepository standardRepo;

  public InspectionDictionaryService(
      InspectionSpecialtyRepository specialtyRepo,
      InspectionObjectRepository objectRepo,
      InspectionParameterRepository parameterRepo,
      InspectionStandardRepository standardRepo) {
    this.specialtyRepo = specialtyRepo;
    this.objectRepo = objectRepo;
    this.parameterRepo = parameterRepo;
    this.standardRepo = standardRepo;
  }

  // === M06.F01 Specialty ===

  public List<InspectionSpecialty> listSpecialties(String keyword) {
    return specialtyRepo.filter(n(keyword)).stream()
        .map(InspectionDictionaryMapper::toDto)
        .toList();
  }

  public InspectionSpecialty createSpecialty(CreateInspectionSpecialtyRequest req) {
    String now = nowIso();
    return InspectionDictionaryMapper.toDto(
        specialtyRepo.save(InspectionDictionaryMapper.fromCreateSpecialty(req, now)));
  }

  public InspectionSpecialty updateSpecialty(String code, UpdateInspectionSpecialtyRequest req) {
    InspectionSpecialtyEntity entity =
        specialtyRepo
            .findById(code)
            .orElseThrow(() -> new NoSuchElementException("Specialty not found: " + code));
    InspectionDictionaryMapper.applyUpdateSpecialty(entity, req, nowIso());
    return InspectionDictionaryMapper.toDto(specialtyRepo.save(entity));
  }

  public void deleteSpecialty(String code) {
    if (!specialtyRepo.existsById(code)) {
      throw new NoSuchElementException("Specialty not found: " + code);
    }
    specialtyRepo.deleteById(code);
  }

  // === M06.F02 Object ===

  public List<InspectionObject> listObjects(String inspectionSpecialtyCode, String keyword) {
    return objectRepo.filter(n(inspectionSpecialtyCode), n(keyword)).stream()
        .map(InspectionDictionaryMapper::toDto)
        .toList();
  }

  public InspectionObject createObject(CreateInspectionObjectRequest req) {
    String now = nowIso();
    return InspectionDictionaryMapper.toDto(
        objectRepo.save(InspectionDictionaryMapper.fromCreateObject(req, now)));
  }

  public InspectionObject updateObject(String code, UpdateInspectionObjectRequest req) {
    InspectionObjectEntity entity =
        objectRepo
            .findById(code)
            .orElseThrow(() -> new NoSuchElementException("Object not found: " + code));
    InspectionDictionaryMapper.applyUpdateObject(entity, req, nowIso());
    return InspectionDictionaryMapper.toDto(objectRepo.save(entity));
  }

  public void deleteObject(String code) {
    if (!objectRepo.existsById(code)) {
      throw new NoSuchElementException("Object not found: " + code);
    }
    objectRepo.deleteById(code);
  }

  // === M06.F03 Parameter ===

  public List<InspectionParameter> listParameters(
      String keyword, InspectionParameterSourceType src) {
    return parameterRepo.filter(n(keyword), src).stream()
        .map(InspectionDictionaryMapper::toDto)
        .toList();
  }

  public InspectionParameter createParameter(CreateInspectionParameterRequest req) {
    String now = nowIso();
    return InspectionDictionaryMapper.toDto(
        parameterRepo.save(InspectionDictionaryMapper.fromCreateParameter(req, now)));
  }

  public InspectionParameter updateParameter(String code, UpdateInspectionParameterRequest req) {
    InspectionParameterEntity entity =
        parameterRepo
            .findById(code)
            .orElseThrow(() -> new NoSuchElementException("Parameter not found: " + code));
    InspectionDictionaryMapper.applyUpdateParameter(entity, req, nowIso());
    return InspectionDictionaryMapper.toDto(parameterRepo.save(entity));
  }

  public void deleteParameter(String code) {
    if (!parameterRepo.existsById(code)) {
      throw new NoSuchElementException("Parameter not found: " + code);
    }
    parameterRepo.deleteById(code);
  }

  // === M06.F04 Standard ===

  public List<InspectionStandard> listStandards(String keyword, InspectionStandardStatus status) {
    return standardRepo.filter(n(keyword), status).stream()
        .map(InspectionDictionaryMapper::toDto)
        .toList();
  }

  public InspectionStandard createStandard(CreateInspectionStandardRequest req) {
    String now = nowIso();
    return InspectionDictionaryMapper.toDto(
        standardRepo.save(InspectionDictionaryMapper.fromCreateStandard(req, now)));
  }

  public InspectionStandard updateStandard(String code, UpdateInspectionStandardRequest req) {
    InspectionStandardEntity entity =
        standardRepo
            .findById(code)
            .orElseThrow(() -> new NoSuchElementException("Standard not found: " + code));
    InspectionDictionaryMapper.applyUpdateStandard(entity, req, nowIso());
    return InspectionDictionaryMapper.toDto(standardRepo.save(entity));
  }

  public void deleteStandard(String code) {
    if (!standardRepo.existsById(code)) {
      throw new NoSuchElementException("Standard not found: " + code);
    }
    standardRepo.deleteById(code);
  }

  private static String nowIso() {
    return java.time.OffsetDateTime.now(java.time.ZoneOffset.UTC)
        .format(java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME);
  }

  private static String n(String s) {
    return s == null ? "" : s;
  }
}
