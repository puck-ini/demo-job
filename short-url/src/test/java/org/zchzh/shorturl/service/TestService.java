package org.zchzh.shorturl.service;



import cn.hutool.core.lang.hash.MurmurHash;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;


/**
 * @author zengchzh
 * @date 2022/1/20
 */

@Slf4j
public class TestService {


//    @Test
//    void testHutoolSnowId() {
//        IntStream.range(1000000, 1000010).forEach(i -> {
//            long id = i;
//            String s = MurmurHash62.base62(id);
//            log.info(id + " : " + s);
//        });
//    }
//
//
//    @Test
//    void testMurmurHash() {
//        long id = MurmurHash.hash32("1378921732sajdlaskjdklsjd1378921732sajdlaskjdklsjd1378921732sajdlaskjdklsjd1378921732sajdlaskjdklsjd" +
//                "1378921732sajdlaskjdklsjd1378921732sajdlaskjdklsjd1378921732sajdlaskjdklsjd1378921732sajdlaskjdklsjd1378921732sajdlaskjdklsjd1378921732sajdlaskjdklsjd" +
//                "1378921732sajdlaskjdklsjd1378921732sajdlaskjdklsjd1378921732sajdlaskjdklsjd1378921732sajdlaskjdklsjd1378921732sajdlaskjdklsjd" +
//                "1378921732sajdlaskjdklsjd1378921732sajdlaskjdklsjd1378921732sajdlaskjdklsjd1378921732sajdlaskjdklsjd1378921732sajdlaskjdklsjd1378921732sajdlaskjdklsjd");
//        log.info(id + " : " + MurmurHash62.base62(id));
//    }
}
