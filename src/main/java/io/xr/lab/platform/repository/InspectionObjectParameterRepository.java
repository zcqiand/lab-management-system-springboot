package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.InspectionObjectParameterEntity;
import io.xr.lab.platform.entity.enums.ObjectParameterKey;
import org.springframework.data.jpa.repository.JpaRepository;

/** V008 — 项目↔参数 junction（M06.F02/F03）。 */
public interface InspectionObjectParameterRepository
    extends JpaRepository<InspectionObjectParameterEntity, ObjectParameterKey> {}
