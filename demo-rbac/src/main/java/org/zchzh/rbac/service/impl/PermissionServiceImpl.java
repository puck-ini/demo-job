package org.zchzh.rbac.service.impl;

import org.zchzh.rbac.model.entity.Permission;
import org.zchzh.rbac.repository.PermissionRepo;
import org.zchzh.rbac.service.PermissionService;

/**
 * @author zengchzh
 * @date 2021/7/30
 */
public class PermissionServiceImpl extends AbstractCrudService<Permission, Long> implements PermissionService {

    private final PermissionRepo permissionRepo;

    protected PermissionServiceImpl(PermissionRepo repo) {
        super(repo);
        this.permissionRepo = repo;
    }
}
