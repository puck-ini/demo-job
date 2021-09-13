package org.zchzh.rbac.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.zchzh.rbac.RbacApplication;
import org.zchzh.rbac.model.dto.LoginDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RbacApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String LOCALHOST = "http://127.0.0.1:";

    @Test
    void login() {

    }

    @Test
    void register() {
    }

    @Test
    void test() {
        restTemplate.getForEntity(LOCALHOST + port + "/user/test", Void.class);
    }
}