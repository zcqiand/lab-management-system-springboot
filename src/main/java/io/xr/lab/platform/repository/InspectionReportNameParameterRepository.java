package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.InspectionReportNameParameterEntity;
import io.xr.lab.platform.entity.enums.ReportNameParameterKey;
import org.springframework.data.jpa.repository.JpaRepository;

/** V009 — 报告名称↔参数 junction。 */
public interface InspectionReportNameParameterRepository
    extends JpaRepository<InspectionReportNameParameterEntity, ReportNameParameterKey> {}
