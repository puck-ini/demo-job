package org.zchzh.rbac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.zchzh.rbac.RbacApplication;
import org.zchzh.rbac.model.entity.MyUser;
import org.zchzh.rbac.model.entity.Permission;
import org.zchzh.rbac.model.entity.Role;
import org.zchzh.rbac.model.request.LoginReq;
import org.zchzh.rbac.model.request.RegisterReq;
import org.zchzh.rbac.service.UserService;
import org.zchzh.rbac.type.PermissionType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@SpringBootTest(classes = {RbacApplication.class})
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


    @Test
    public void register() {
        RegisterReq req = new RegisterReq();
        req.setUsername("testuser");
        req.setPassword("pw");
        req.setName("123");
        userService.register(req);
    }

    @Autowired
    private HttpServletRequest request;

    @Test
    public void loginSuccess() {
        LoginReq loginReq = new LoginReq("testuser", "pw");
        log.info(userService.login(loginReq, request).toString());
    }

    @Test
    public void loginFail() {
        LoginReq loginReq = new LoginReq("testuser", "pw123");
        userService.login(loginReq, request);
    }

    @RepeatedTest(value = 10)
    public void loginFail10() {
        loginFail();
    }


}