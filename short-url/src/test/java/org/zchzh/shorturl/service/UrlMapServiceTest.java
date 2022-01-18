package org.zchzh.shorturl.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zchzh.shorturl.entity.UrlMap;


@Slf4j
@SpringBootTest
class UrlMapServiceTest {

    @Autowired
    private UrlMapService urlMapService;


    @Test
    void getShort() {
      log.info(urlMapService.getShortUrl("https://www.bookstack.cn/read/hutool/2dac05593c1166ad.md"));
      log.info(urlMapService.getShortUrl("https://www.bookstack.cn/read/hutool/2dac05593c1166ad.md"));
    }


    @Test
    void getLong() {
        log.info(urlMapService.getLongUrl("1D5JSi"));

    }

    @Test
    void create() {
        log.info(UrlMap.create("https://www.bookstack.cn/read/hutool/2dac05593c1166ad.md").toString());

    }

}