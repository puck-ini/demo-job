package org.zchzh.rbac.service.impl;

import org.springframework.stereotype.Service;
import org.zchzh.rbac.exception.CommonException;
import org.zchzh.rbac.model.entity.Permission;
import org.zchzh.rbac.model.entity.Role;
import org.zchzh.rbac.repository.PermissionRepo;
import org.zchzh.rbac.repository.RoleRepo;
import org.zchzh.rbac.service.RoleService;

/**
 * @author zengchzh
 * @date 2021/7/30
 */
@Service
public class RoleServiceImpl extends AbstractCrudService<Role, Long> implements RoleService {

    private final RoleRepo roleRepo;

    private final PermissionRepo permissionRepo;

    protected RoleServiceImpl(RoleRepo repo, PermissionRepo permissionRepo) {
        super(repo);
        this.roleRepo = repo;
        this.permissionRepo = permissionRepo;
    }

    @Override
    public void associate(Long roleId, Long permissionId) {
        Role role = roleRepo.findById(roleId).orElseThrow(() -> new CommonException("无该角色"));
        Permission permission = permissionRepo.findById(permissionId).orElseThrow(() -> new CommonException("无该权限"));
        role.getPermissions().add(permission);
        roleRepo.save(role);
    }
}
