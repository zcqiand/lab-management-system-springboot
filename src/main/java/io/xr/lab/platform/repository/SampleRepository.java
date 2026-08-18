package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.SampleEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** V002 — 样品（M03.F02/F03）。tenant-scoped + receipt 过滤。 */
public interface SampleRepository extends JpaRepository<SampleEntity, String> {

  @Query(
      "SELECT s FROM SampleEntity s"
          + " WHERE (:tenantId = '' OR s.tenantId = :tenantId)"
          + " AND (:receiptId = '' OR s.receiptId = :receiptId)"
          + " AND (:keyword = '' OR LOWER(s.sampleCode) LIKE LOWER(CONCAT('%', :keyword, '%'))"
          + "   OR LOWER(s.sampleName) LIKE LOWER(CONCAT('%', :keyword, '%')))"
          + " ORDER BY s.createdAt DESC, s.sampleCode")
  List<SampleEntity> filter(
      @Param("tenantId") String tenantId,
      @Param("receiptId") String receiptId,
      @Param("keyword") String keyword);

  Optional<SampleEntity> findByTenantIdAndId(String tenantId, String id);
}
