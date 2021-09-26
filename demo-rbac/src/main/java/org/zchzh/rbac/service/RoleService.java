package org.zchzh.rbac.service;

import org.zchzh.rbac.model.entity.Role;

/**
 * @author zengchzh
 * @date 2021/7/30
 *
 * 关联
 */
public interface RoleService extends BaseCrudService<Role, Long> {

    /**
     * 关联角色和权限
     * @param roleId 角色id
     * @param permissionId 权限id
     */
    void associate(Long roleId, Long permissionId);
}
