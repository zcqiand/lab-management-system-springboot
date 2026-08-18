package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.InspectionSpecialtyEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** V008 — 检测专项（M06.F01）。平台级字典（无 tenant_id）。 */
public interface InspectionSpecialtyRepository
    extends JpaRepository<InspectionSpecialtyEntity, String> {

  @Query(
      "SELECT s FROM InspectionSpecialtyEntity s"
          + " WHERE (:keyword = ''"
          + "   OR LOWER(s.code) LIKE LOWER(CONCAT('%', :keyword, '%'))"
          + "   OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')))"
          + " ORDER BY s.sortOrder, s.code")
  List<InspectionSpecialtyEntity> filter(@Param("keyword") String keyword);
}
