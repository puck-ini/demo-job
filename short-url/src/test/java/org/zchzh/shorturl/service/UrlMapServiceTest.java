package org.zchzh.shorturl.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zchzh.shorturl.model.entity.UrlMap;

import javax.annotation.Resource;


@Slf4j
@SpringBootTest
class UrlMapServiceTest {

    @Resource
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

//    @Test
//    void create() {
//        log.info(UrlMap.create("https://www.bookstack.cn/read/hutool/2dac05593c1166ad.md").toString());
//    }
//
//    @Test
//    void createTest() {
//        log.info(UrlMap.create("https://www.google.com/search?q=%E9%95%BF%E9%93%BE%E6%8E%A5+wiki&newwindow=1&ei=TfToYbi6H5TOdjsakldjskljdlksjdkluieoqwueoiwqueioqwueiowueioqwjdklasjdkajkldjsklajdksajdlkldksjldjlaskdjksljdljsalkdjlskajdlksajdkljsalkdjsalkdjklasjdklsajdklsajdksajdklsajdklsajdksljdqioueu3218373829738usiejdkljldwkPIPi4WOwA8&ved=0ahUKEwj4lI_Ozb_1AhUUJ0QIHYuCA_gQ4dUDCA4&uact=5&oq=%E9%95%BF%E9%93%BE%E6%8E%A5+wiki&gs_lcp=Cgdnd3Mtd2l6EAM6CggAEIAEELADEAo6BwgAELADEAo6CAgAEIAEELADOgQIABBDOgUIABCABDoHCAAQgAQQDDoGCAAQChAqOgwIABAMEAUQBBAKEB5KBAhBGAFKBQhAEgExSgQIRhgAUJABWJgHYO0JaAFwAHgAgAGPAogBkQqSAQMyLTWYAQCgAQHIAQrAAQE&sclient=gws-wiz").getShortUrl());
//    }

    @Test
    void custom() {
        log.info(urlMapService.custom("1D5JSi86", "https://www.bookstack.cn/read/hutool/2dac05593c1166ad.md"));
    }

}