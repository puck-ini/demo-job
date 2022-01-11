package org.zchzh.rbac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.zchzh.rbac.model.entity.Permission;
import org.zchzh.rbac.model.entity.Role;
import org.zchzh.rbac.service.RoleService;
import org.zchzh.rbac.type.PermissionType;
import org.zchzh.rbac.type.Url;

import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void testList() {
        for (Role role : roleService.list()) {
            log.info(role.toString());
        }
    }


    @Test
    public void get() {
        log.info(roleService.get(10L).orElse(new Role()).toString());
    }


    @Test
    public void testAdd() {
        Role role = Role.builder().name("test").description("e123").build();
        Permission permission = Permission.builder()
                .description("test")
                .name("test")
                .type(PermissionType.BACK_END)
                .url(new Url("/user/login", HttpMethod.GET))
                .build();
        role.getPermissions().add(permission);
        roleService.create(role);
    }


    @Test
    @AfterEach
    public void removeAll() {
        roleService.removeAll(roleService.list().stream().map(Role::getId).collect(Collectors.toList()));
    }

}