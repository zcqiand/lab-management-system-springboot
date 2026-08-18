package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.InspectionObjectReportNameEntity;
import io.xr.lab.platform.entity.enums.ObjectReportNameKey;
import org.springframework.data.jpa.repository.JpaRepository;

/** V009 — 项目↔报告名称 junction（M06.F02/F07）。 */
public interface InspectionObjectReportNameRepository
    extends JpaRepository<InspectionObjectReportNameEntity, ObjectReportNameKey> {}
