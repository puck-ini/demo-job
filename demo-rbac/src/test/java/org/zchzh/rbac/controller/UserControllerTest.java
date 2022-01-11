package org.zchzh.rbac.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.zchzh.rbac.RbacApplication;
import org.zchzh.rbac.model.dto.LoginDTO;
import org.zchzh.rbac.model.dto.ResultDTO;
import org.zchzh.rbac.model.request.LoginReq;
import org.zchzh.rbac.model.request.RegisterReq;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(classes = RbacApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String LOCALHOST = "http://127.0.0.1:";

    private String url;

    @BeforeEach
    public void initUrl() {
        url = LOCALHOST + port;
    }

    @Test
    void login() {
        LoginReq req = new LoginReq("testuser","pw");
        ResultDTO result = restTemplate.getForObject(url + "/user/login?username={username}&password={password}",
                ResultDTO.class, req.getUsername(), req.getPassword());
        Assert.assertNotNull(result);
        log.info("result: " + result);
    }

    @Test
    void register() {
        RegisterReq req = new RegisterReq();
    }

    @Test
    void test() {
        restTemplate.getForEntity(LOCALHOST + port + "/user/test", Void.class);
    }
}