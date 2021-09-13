package org.zchzh.rbac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.zchzh.rbac.RbacApplication;
import org.zchzh.rbac.model.entity.MyUser;
import org.zchzh.rbac.model.entity.Permission;
import org.zchzh.rbac.model.entity.Role;
import org.zchzh.rbac.service.PermissionService;
import org.zchzh.rbac.service.UserService;
import org.zchzh.rbac.type.PermissionType;
import org.zchzh.rbac.type.Url;


@Slf4j
@SpringBootTest(classes = RbacApplication.class)
class PermissionServiceImplTest {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;


    @Test
    void create() {
        MyUser user = userService.get(6L).orElse(new MyUser());
        Role role = Role.builder().name("admin").description("admin").build();
        Permission permission = Permission.builder()
                .name("login")
                .description("login")
                .type(PermissionType.BACK_END)
                .url(new Url("/user/login", HttpMethod.GET)).build();
        role.getPermissions().add(permission);
        user.getRoles().add(role);
        userService.update(user);
    }
    @Test
    void hasPermission() {
        Assert.assertFalse(permissionService.hasPermission(6L, PermissionType.BACK_END, new Url("/user/register", HttpMethod.POST)));
        Assert.assertTrue(permissionService.hasPermission(6L, PermissionType.BACK_END, new Url("/user/login", HttpMethod.GET)));
    }

    @Test
    void getPermission() {
        for (Permission permission : permissionService.getPermission(6L)) {
            log.info(permission.toString());
        }
    }
}