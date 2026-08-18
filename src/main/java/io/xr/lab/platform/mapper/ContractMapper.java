package io.xr.lab.platform.mapper;

import io.xr.lab.platform.entity.ContractEntity;
import io.xr.lab.shared.dto.Contract;
import io.xr.lab.shared.dto.ContractStatus;
import io.xr.lab.shared.dto.CreateContractRequest;
import io.xr.lab.shared.dto.UpdateContractRequest;

/** M02.F01 合同 DTO ↔ Entity。 */
public final class ContractMapper {

  private ContractMapper() {}

  public static Contract toDto(ContractEntity e) {
    return new Contract()
        .id(e.getId())
        .contractCode(e.getContractCode())
        .clientUnit(e.getClientUnit())
        .projectName(e.getProjectName())
        .projectLocation(e.getProjectLocation())
        .constructionUnit(e.getConstructionUnit())
        .inspectionSpecialtyCode(e.getInspectionSpecialtyCode())
        .buildingUnit(e.getBuildingUnit())
        .supervisorUnit(e.getSupervisorUnit())
        .inspectionPerson(e.getInspectionPerson())
        .inspectionPhone(e.getInspectionPhone())
        .witnessUnit(e.getWitnessUnit())
        .witness(e.getWitness())
        .witnessPhone(e.getWitnessPhone())
        .contactPerson(e.getContactPerson())
        .contactPhone(e.getContactPhone())
        .entrustedDate(e.getEntrustedDate())
        .status(e.getStatus())
        .tenantId(e.getTenantId())
        .createdAt(e.getCreatedAt())
        .updatedAt(e.getUpdatedAt());
  }

  public static ContractEntity fromCreate(
      CreateContractRequest req, String id, String tenantId, String now) {
    ContractEntity e = new ContractEntity();
    e.setId(id);
    e.setTenantId(tenantId);
    e.setContractCode(req.getContractCode());
    e.setClientUnit(req.getClientUnit());
    e.setProjectName(req.getProjectName());
    e.setProjectLocation(req.getProjectLocation());
    e.setConstructionUnit(req.getConstructionUnit());
    e.setInspectionSpecialtyCode(req.getInspectionSpecialtyCode());
    e.setBuildingUnit(req.getBuildingUnit());
    e.setSupervisorUnit(req.getSupervisorUnit());
    e.setInspectionPerson(req.getInspectionPerson());
    e.setInspectionPhone(req.getInspectionPhone());
    e.setWitnessUnit(req.getWitnessUnit());
    e.setWitness(req.getWitness());
    e.setWitnessPhone(req.getWitnessPhone());
    e.setContactPerson(req.getContactPerson());
    e.setContactPhone(req.getContactPhone());
    e.setEntrustedDate(req.getEntrustedDate());
    ContractStatus status = req.getStatus();
    e.setStatus(status != null ? status : ContractStatus.ACTIVE);
    e.setCreatedAt(now);
    e.setUpdatedAt(now);
    return e;
  }

  public static void applyUpdate(ContractEntity e, UpdateContractRequest req, String now) {
    if (req.getClientUnit() != null) e.setClientUnit(req.getClientUnit());
    if (req.getProjectName() != null) e.setProjectName(req.getProjectName());
    if (req.getProjectLocation() != null) e.setProjectLocation(req.getProjectLocation());
    if (req.getConstructionUnit() != null) e.setConstructionUnit(req.getConstructionUnit());
    if (req.getInspectionSpecialtyCode() != null)
      e.setInspectionSpecialtyCode(req.getInspectionSpecialtyCode());
    if (req.getBuildingUnit() != null) e.setBuildingUnit(req.getBuildingUnit());
    if (req.getSupervisorUnit() != null) e.setSupervisorUnit(req.getSupervisorUnit());
    if (req.getInspectionPerson() != null) e.setInspectionPerson(req.getInspectionPerson());
    if (req.getInspectionPhone() != null) e.setInspectionPhone(req.getInspectionPhone());
    if (req.getWitnessUnit() != null) e.setWitnessUnit(req.getWitnessUnit());
    if (req.getWitness() != null) e.setWitness(req.getWitness());
    if (req.getWitnessPhone() != null) e.setWitnessPhone(req.getWitnessPhone());
    if (req.getContactPerson() != null) e.setContactPerson(req.getContactPerson());
    if (req.getContactPhone() != null) e.setContactPhone(req.getContactPhone());
    if (req.getEntrustedDate() != null) e.setEntrustedDate(req.getEntrustedDate());
    if (req.getStatus() != null) e.setStatus(req.getStatus());
    e.setUpdatedAt(now);
  }
}
