package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.InspectionSpecialtyObjectEntity;
import io.xr.lab.platform.entity.enums.SpecialtyObjectKey;
import org.springframework.data.jpa.repository.JpaRepository;

/** V008 — 专项↔项目 junction（M06.F01/F02）。 */
public interface InspectionSpecialtyObjectRepository
    extends JpaRepository<InspectionSpecialtyObjectEntity, SpecialtyObjectKey> {}
