package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.TestRecordEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** V003 — 检测记录（M03.F03 数据录入）。tenant-scoped + sample 过滤。 */
public interface TestRecordRepository extends JpaRepository<TestRecordEntity, String> {

  @Query(
      "SELECT t FROM TestRecordEntity t"
          + " WHERE (:tenantId = '' OR t.tenantId = :tenantId)"
          + " AND (:sampleId = '' OR t.sampleId = :sampleId)"
          + " ORDER BY t.updatedAt DESC, t.id")
  List<TestRecordEntity> filter(
      @Param("tenantId") String tenantId, @Param("sampleId") String sampleId);

  Optional<TestRecordEntity> findByTenantIdAndId(String tenantId, String id);
}
