package io.xr.lab.platform.repository;

import io.xr.lab.platform.entity.ParamInterfaceLinkEntity;
import io.xr.lab.platform.entity.enums.ParamInterfaceLinkKey;
import org.springframework.data.jpa.repository.JpaRepository;

/** V010 — 参数↔界面 junction（M06.F08）。 */
public interface ParamInterfaceLinkRepository
    extends JpaRepository<ParamInterfaceLinkEntity, ParamInterfaceLinkKey> {}
