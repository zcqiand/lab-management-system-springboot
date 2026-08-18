package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.InspectionParameterEntity;
import io.xr.lab.shared.dto.InspectionParameterSourceType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** V008 — 检测参数（M06.F03）。平台级字典（无 tenant_id）。 */
public interface InspectionParameterRepository
    extends JpaRepository<InspectionParameterEntity, String> {

  @Query(
      "SELECT p FROM InspectionParameterEntity p"
          + " WHERE (:keyword = ''"
          + "   OR LOWER(p.code) LIKE LOWER(CONCAT('%', :keyword, '%'))"
          + "   OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')))"
          + " AND (:sourceType IS NULL OR p.sourceType = :sourceType)"
          + " ORDER BY p.sortOrder, p.code")
  List<InspectionParameterEntity> filter(
      @Param("keyword") String keyword,
      @Param("sourceType") InspectionParameterSourceType sourceType);
}
