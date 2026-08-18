package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.ParamInterfaceEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** V010 — 参数界面（M06.F08）。平台级字典（无 tenant_id）。 */
public interface ParamInterfaceRepository extends JpaRepository<ParamInterfaceEntity, String> {

  @Query(
      "SELECT p FROM ParamInterfaceEntity p"
          + " WHERE (:keyword = ''"
          + "   OR LOWER(p.code) LIKE LOWER(CONCAT('%', :keyword, '%'))"
          + "   OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')))"
          + " ORDER BY p.sortOrder, p.code")
  List<ParamInterfaceEntity> filter(@Param("keyword") String keyword);
}
