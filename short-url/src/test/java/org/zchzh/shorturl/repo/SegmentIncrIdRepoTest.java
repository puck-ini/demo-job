package org.zchzh.shorturl.repo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zchzh.shorturl.model.entity.SegmentIncrId;

import static org.junit.jupiter.api.Assertions.*;



@Slf4j
@SpringBootTest
class SegmentIncrIdRepoTest {

    @Autowired
    private SegmentIncrIdRepo segmentIncrIdRepo;

    @Test
    void testPstPersist() {
        SegmentIncrId segmentIncrId = new SegmentIncrId();
        segmentIncrIdRepo.save(segmentIncrId);
        log.info(segmentIncrId.toString());
    }

    @Test
    void get() {
        SegmentIncrId segmentIncrId = segmentIncrIdRepo.findById(1L).get();
        log.info(segmentIncrId.toString());
    }

}