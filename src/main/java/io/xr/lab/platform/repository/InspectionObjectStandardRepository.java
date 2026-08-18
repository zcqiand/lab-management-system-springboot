package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.InspectionObjectStandardEntity;
import io.xr.lab.platform.entity.enums.ObjectStandardKey;
import org.springframework.data.jpa.repository.JpaRepository;

/** V008 — 项目↔标准 junction（role）。 */
public interface InspectionObjectStandardRepository
    extends JpaRepository<InspectionObjectStandardEntity, ObjectStandardKey> {}
