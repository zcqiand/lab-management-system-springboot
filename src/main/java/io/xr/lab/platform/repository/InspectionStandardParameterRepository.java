package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.InspectionStandardParameterEntity;
import io.xr.lab.platform.entity.enums.StandardParameterKey;
import org.springframework.data.jpa.repository.JpaRepository;

/** V008 — 标准↔参数 junction（M06.F03/F04）。 */
public interface InspectionStandardParameterRepository
    extends JpaRepository<InspectionStandardParameterEntity, StandardParameterKey> {}
