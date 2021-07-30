package org.zchzh.rbac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zchzh.rbac.entity.Role;

/**
 * @author zengchzh
 * @date 2021/7/30
 */
public interface RoleRepo extends JpaRepository<Role, Long> {
}
