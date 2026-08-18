package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.CatalogEntryKey;
import io.xr.lab.platform.entity.InspectionGradeEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** V004 — 等级码表（M04.F08）。 */
public interface InspectionGradeRepository
    extends JpaRepository<InspectionGradeEntity, CatalogEntryKey> {

  @Query(
      "SELECT g FROM InspectionGradeEntity g WHERE g.tenantId = :tenantId"
          + " AND (:objectCode = '' OR g.inspectionObjectCode = :objectCode)"
          + " AND (:keyword = ''"
          + "   OR LOWER(g.code) LIKE LOWER(CONCAT('%', :keyword, '%'))"
          + "   OR LOWER(g.name) LIKE LOWER(CONCAT('%', :keyword, '%')))"
          + " ORDER BY g.sortOrder, g.code")
  List<InspectionGradeEntity> filter(
      @Param("tenantId") String tenantId,
      @Param("objectCode") String inspectionObjectCode,
      @Param("keyword") String keyword);

  Optional<InspectionGradeEntity> findByTenantIdAndCode(String tenantId, String code);
}
