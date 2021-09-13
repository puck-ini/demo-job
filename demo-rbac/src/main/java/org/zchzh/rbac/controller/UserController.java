package org.zchzh.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zchzh.rbac.model.dto.LoginDTO;
import org.zchzh.rbac.model.dto.UserDTO;
import org.zchzh.rbac.model.request.LoginReq;
import org.zchzh.rbac.model.request.RegisterReq;
import org.zchzh.rbac.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zengchzh
 * @date 2021/9/7
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public LoginDTO login(LoginReq req, HttpServletRequest request) {
        return userService.login(req, request);
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody RegisterReq req) {
        return userService.register(req);
    }


    @GetMapping("/test")
    public void test() {

    }
}
