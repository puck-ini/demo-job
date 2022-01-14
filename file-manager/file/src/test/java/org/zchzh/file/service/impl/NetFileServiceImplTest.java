package org.zchzh.file.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zchzh.file.entity.BaseFile;
import org.zchzh.file.entity.NetFile;
import org.zchzh.file.repository.NetFileRepo;
import org.zchzh.file.service.FileManager;
import org.zchzh.file.util.MD5Util;

import static org.junit.jupiter.api.Assertions.*;



@Slf4j
@SpringBootTest
class NetFileServiceImplTest {

    @Autowired
    private FileManager fileManager;

    @Test
    void cerate() {
        NetFile file = new NetFile();
        file.setFileName("test1.jpg");
        file.setUrl("https://www.runoob.com/wp-content/uploads/2013/12/java.jpg");
        file.setMd5(MD5Util.getMd5(file.getInputStream()));
        BaseFile file1 = fileManager.create(file);
        log.info(file1.toString());
    }

    @Test
    void get() {
        log.info(fileManager.get(1L).toString());
    }

}