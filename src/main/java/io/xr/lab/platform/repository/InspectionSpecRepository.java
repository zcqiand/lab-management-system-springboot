package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.CatalogEntryKey;
import io.xr.lab.platform.entity.InspectionSpecEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** V004 — 规格码表（M04.F07）。 */
public interface InspectionSpecRepository
    extends JpaRepository<InspectionSpecEntity, CatalogEntryKey> {

  @Query(
      "SELECT s FROM InspectionSpecEntity s WHERE s.tenantId = :tenantId"
          + " AND (:objectCode = '' OR s.inspectionObjectCode = :objectCode)"
          + " AND (:keyword = ''"
          + "   OR LOWER(s.code) LIKE LOWER(CONCAT('%', :keyword, '%'))"
          + "   OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')))"
          + " ORDER BY s.sortOrder, s.code")
  List<InspectionSpecEntity> filter(
      @Param("tenantId") String tenantId,
      @Param("objectCode") String inspectionObjectCode,
      @Param("keyword") String keyword);

  Optional<InspectionSpecEntity> findByTenantIdAndCode(String tenantId, String code);
}
