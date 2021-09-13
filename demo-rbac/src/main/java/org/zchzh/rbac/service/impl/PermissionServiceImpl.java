package org.zchzh.rbac.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.zchzh.rbac.exception.CommonException;
import org.zchzh.rbac.model.entity.MyUser;
import org.zchzh.rbac.model.entity.Permission;
import org.zchzh.rbac.repository.PermissionRepo;
import org.zchzh.rbac.repository.UserRepo;
import org.zchzh.rbac.service.PermissionService;
import org.zchzh.rbac.type.PermissionType;
import org.zchzh.rbac.type.Url;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zengchzh
 * @date 2021/7/30
 */
@Service
public class PermissionServiceImpl extends AbstractCrudService<Permission, Long> implements PermissionService {

    private final PermissionRepo permissionRepo;

    private final UserRepo userRepo;

    protected PermissionServiceImpl(PermissionRepo repo, UserRepo userRepo) {
        super(repo);
        this.permissionRepo = repo;
        this.userRepo = userRepo;
    }

    @Override
    public boolean hasPermission(Long userId, PermissionType type, Url url) {
        List<Permission> permissionList = getPermission(userId);
        for (Permission p : permissionList) {
            PathMatcher matcher = new AntPathMatcher();
            return matcher.match(p.getUrl().getUrl(), url.getUrl())
                    && Objects.equals(p.getUrl().getMethod(), url.getMethod())
                    && Objects.equals(p.getType(), type);
        }
        return false;
    }

    @Override
    public List<Permission> getPermission(Long userId) {
        MyUser user = userRepo.findById(userId).orElseThrow(() -> new CommonException("用户不存在"));
        List<Permission> permissionList = new ArrayList<>();
        user.getRoles().forEach(role -> permissionList.addAll(role.getPermissions()));
        return permissionList;
    }

}
