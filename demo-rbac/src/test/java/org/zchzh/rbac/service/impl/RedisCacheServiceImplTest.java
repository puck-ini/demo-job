package org.zchzh.rbac.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zchzh.rbac.RbacApplication;
import org.zchzh.rbac.service.CacheService;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest(classes = RbacApplication.class)
class RedisCacheServiceImplTest {

    @Autowired
    private CacheService cacheService;


    @Test
    void set() {
        cacheService.set("testinc", 0);
    }

    @Test
    void get() {
        log.info("value" + cacheService.get("testinc"));
    }

    @Test
    void incr() {
        cacheService.incr("testinc");
    }
}