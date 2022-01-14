package org.zchzh.file.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.zchzh.file.entity.BaseFile;
import org.zchzh.file.entity.Folder;
import org.zchzh.file.entity.VirtualFile;
import org.zchzh.file.service.FileManager;
import org.zchzh.file.service.FileService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class FileManagerImplTest {

    @Autowired
    private FileManager fileManager;


    @Test
    void create() {
        Folder folder = new Folder();
        folder.setFileName("test-folder");
        BaseFile sFolder = fileManager.create(folder);
        log.info(sFolder.toString());
    }
    @Test
    void upload() throws FileNotFoundException {
        VirtualFile file = new VirtualFile();
        File file1 = new File("D:\\testdata\\test333.txt");
        file.setFileName(UUID.randomUUID().toString());
        String originName = file1.getName();
        file.setOriginName(originName);
        file.setSuffix(originName.substring(originName.lastIndexOf(".") + 1));
        file.setFolderId(1L);
        file.setInputStream(new FileInputStream(file1));
        fileManager.upload(file);
    }

    @Test
    void get() {
        BaseFile file = fileManager.get(1L);
        log.info(file.toString());
    }

    @Test
    void remove() {
        fileManager.remove(1L);
    }
}