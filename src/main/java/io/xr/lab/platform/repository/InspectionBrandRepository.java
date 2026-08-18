package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.CatalogEntryKey;
import io.xr.lab.platform.entity.InspectionBrandEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** V004 — 牌号码表（M04.F09）。 */
public interface InspectionBrandRepository
    extends JpaRepository<InspectionBrandEntity, CatalogEntryKey> {

  @Query(
      "SELECT b FROM InspectionBrandEntity b WHERE b.tenantId = :tenantId"
          + " AND (:objectCode = '' OR b.inspectionObjectCode = :objectCode)"
          + " AND (:keyword = ''"
          + "   OR LOWER(b.code) LIKE LOWER(CONCAT('%', :keyword, '%'))"
          + "   OR LOWER(b.name) LIKE LOWER(CONCAT('%', :keyword, '%')))"
          + " ORDER BY b.sortOrder, b.code")
  List<InspectionBrandEntity> filter(
      @Param("tenantId") String tenantId,
      @Param("objectCode") String inspectionObjectCode,
      @Param("keyword") String keyword);

  Optional<InspectionBrandEntity> findByTenantIdAndCode(String tenantId, String code);
}
