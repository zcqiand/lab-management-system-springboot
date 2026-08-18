package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.InspectionReportNameStandardEntity;
import io.xr.lab.platform.entity.enums.ReportNameStandardKey;
import org.springframework.data.jpa.repository.JpaRepository;

/** V009 — 报告名称↔标准 junction（role）。 */
public interface InspectionReportNameStandardRepository
    extends JpaRepository<InspectionReportNameStandardEntity, ReportNameStandardKey> {}
