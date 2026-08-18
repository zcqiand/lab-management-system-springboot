package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.InspectionStandardEntity;
import io.xr.lab.shared.dto.InspectionStandardStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** V008 — 检测标准（M06.F04）。平台级字典（无 tenant_id）。 */
public interface InspectionStandardRepository
    extends JpaRepository<InspectionStandardEntity, String> {

  @Query(
      "SELECT s FROM InspectionStandardEntity s"
          + " WHERE (:keyword = ''"
          + "   OR LOWER(s.code) LIKE LOWER(CONCAT('%', :keyword, '%'))"
          + "   OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')))"
          + " AND (:status IS NULL OR s.status = :status)"
          + " ORDER BY s.sortOrder, s.code")
  List<InspectionStandardEntity> filter(
      @Param("keyword") String keyword, @Param("status") InspectionStandardStatus status);
}
