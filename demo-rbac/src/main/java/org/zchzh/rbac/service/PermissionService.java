package org.zchzh.rbac.service;

import org.zchzh.rbac.model.entity.Permission;
import org.zchzh.rbac.type.PermissionType;
import org.zchzh.rbac.type.Url;

import java.util.List;

/**
 * @author zengchzh
 * @date 2021/7/30
 */
public interface PermissionService extends BaseCrudService<Permission, Long> {

    /**
     * 判断用户是否有url权限
     * @param userId 用户id
     * @param type 权限类型
     * @param url 判断的url
     * @return 如果有权限返回true
     */
    boolean hasPermission(Long userId, PermissionType type, Url url);

    /**
     * 根据用户id获取用户的所有权限
     * @param userId 用户id
     * @return 返回查询的用户的所有权限
     */
    List<Permission> getPermission(Long userId);
}
