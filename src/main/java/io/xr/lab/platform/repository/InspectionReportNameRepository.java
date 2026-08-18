package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.InspectionReportNameEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** V009 — 报告名称（M06.F07）。平台级字典（无 tenant_id）。 */
public interface InspectionReportNameRepository
    extends JpaRepository<InspectionReportNameEntity, String> {

  @Query(
      "SELECT r FROM InspectionReportNameEntity r"
          + " WHERE (:keyword = ''"
          + "   OR LOWER(r.code) LIKE LOWER(CONCAT('%', :keyword, '%'))"
          + "   OR LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%')))"
          + " ORDER BY r.sortOrder, r.code")
  List<InspectionReportNameEntity> filter(@Param("keyword") String keyword);
}
