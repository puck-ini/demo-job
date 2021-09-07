package org.zchzh.rbac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zchzh.rbac.model.entity.Permission;

/**
 * @author zengchzh
 * @date 2021/7/30
 */
public interface PermissionRepo extends JpaRepository<Permission, Long> {
}
