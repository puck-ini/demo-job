package org.zchzh.rbac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.zchzh.rbac.entity.MyUser;
import org.zchzh.rbac.entity.Permission;
import org.zchzh.rbac.entity.Role;
import org.zchzh.rbac.service.UserService;
import org.zchzh.rbac.type.PermissionType;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void testList() {
        List<MyUser> myUsers = userService.list();
        for (MyUser user : myUsers) {
            log.info(user.toString());
        }
    }
    @Test
    public void addUser() {
        MyUser user = MyUser.builder().name("123").username("13").password("!23").build();
        log.info(userService.create(user).toString());
    }

    @Test
    public void testManyToMany() {
        MyUser user = MyUser.builder().name("123").username("13").password("!23").build();
        Role role = Role.builder().name("test").description("e123").build();
        Permission permission = Permission.builder()
                .description("test")
                .method(HttpMethod.GET)
                .name("test")
                .type(PermissionType.BACK_END)
                .url("test")
                .build();
        role.getPermissions().add(permission);
        user.getRoles().add(role);
        userService.create(user);
    }

    @Test
    public void testUpdate() {
        Role role = Role.builder().name("test").description("e123").build();
        Permission permission = Permission.builder()
                .description("test")
                .method(HttpMethod.GET)
                .name("test")
                .type(PermissionType.BACK_END)
                .url("test")
                .build();
        role.getPermissions().add(permission);
        List<MyUser> myUsers = userService.list();
        myUsers.get(0).getRoles().add(role);
        userService.update(myUsers.get(0));
    }


    @Test
    @AfterEach
    public void removeAll() {
//        userService.removeAll(userService.list().stream().map(MyUser::getId).collect(Collectors.toList()));
    }
}