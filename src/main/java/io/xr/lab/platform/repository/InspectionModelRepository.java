package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.CatalogEntryKey;
import io.xr.lab.platform.entity.InspectionModelEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** V004 — 型号码表（M04.F06）。 */
public interface InspectionModelRepository
    extends JpaRepository<InspectionModelEntity, CatalogEntryKey> {

  @Query(
      "SELECT m FROM InspectionModelEntity m WHERE m.tenantId = :tenantId"
          + " AND (:objectCode = '' OR m.inspectionObjectCode = :objectCode)"
          + " AND (:keyword = ''"
          + "   OR LOWER(m.code) LIKE LOWER(CONCAT('%', :keyword, '%'))"
          + "   OR LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')))"
          + " ORDER BY m.sortOrder, m.code")
  List<InspectionModelEntity> filter(
      @Param("tenantId") String tenantId,
      @Param("objectCode") String inspectionObjectCode,
      @Param("keyword") String keyword);

  Optional<InspectionModelEntity> findByTenantIdAndCode(String tenantId, String code);
}
