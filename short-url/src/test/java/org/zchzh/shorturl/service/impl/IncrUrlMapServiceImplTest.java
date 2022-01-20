package org.zchzh.shorturl.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
class IncrUrlMapServiceImplTest {


    @Autowired
    private IncrUrlMapServiceImpl urlMapService;


    @Test
    void getShort() {
        log.info(urlMapService.getShortUrl("https://www.bookstack.cn/read/hutool/2dac05593c1166ad.md"));
        log.info(urlMapService.getShortUrl("https://www.bookstack.cn/read/hutool/2dac05593c1166ad.md"));
    }


    @Test
    void getLong() {
        log.info(urlMapService.getLongUrl("B"));

    }

}