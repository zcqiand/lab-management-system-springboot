package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.InspectionObjectEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** V008 — 检测项目（M06.F02）。平台级字典（无 tenant_id）。 */
public interface InspectionObjectRepository extends JpaRepository<InspectionObjectEntity, String> {

  @Query(
      "SELECT o FROM InspectionObjectEntity o"
          + " WHERE (:specialtyCode = '' OR o.inspectionSpecialtyCode = :specialtyCode)"
          + " AND (:keyword = ''"
          + "   OR LOWER(o.code) LIKE LOWER(CONCAT('%', :keyword, '%'))"
          + "   OR LOWER(o.name) LIKE LOWER(CONCAT('%', :keyword, '%')))"
          + " ORDER BY o.sortOrder, o.code")
  List<InspectionObjectEntity> filter(
      @Param("specialtyCode") String specialtyCode, @Param("keyword") String keyword);
}
