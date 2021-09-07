package org.zchzh.rbac.service.impl;

import org.springframework.stereotype.Service;
import org.zchzh.rbac.model.entity.Role;
import org.zchzh.rbac.repository.RoleRepo;
import org.zchzh.rbac.service.RoleService;

/**
 * @author zengchzh
 * @date 2021/7/30
 */
@Service
public class RoleServiceImpl extends AbstractCrudService<Role, Long> implements RoleService {

    private final RoleRepo roleRepo;

    protected RoleServiceImpl(RoleRepo repo) {
        super(repo);
        this.roleRepo = repo;
    }
}
