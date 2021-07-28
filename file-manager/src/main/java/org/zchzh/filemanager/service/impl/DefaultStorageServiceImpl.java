package org.zchzh.filemanager.service.impl;

import org.zchzh.filemanager.config.StorageProp;
import org.zchzh.filemanager.exception.CommonException;
import org.zchzh.filemanager.service.StorageService;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Objects;

/**
 * @author zengchzh
 * @date 2021/7/28
 */
public class DefaultStorageServiceImpl implements StorageService {

    private String path;

    public DefaultStorageServiceImpl(StorageProp prop) {
        if (Objects.nonNull(prop.getDatabase())) {
            this.path = prop.getDatabase();
        } else {
            this.path = System.getProperty("java.io.tmpdir");
        }
        if (!this.path.endsWith(File.separator)) {
            this.path = this.path + File.separator;
        }
    }

    @Override
    public void upload(String fileName, InputStream is) {
        File file = new File(path + fileName);
        try (FileChannel source = ((FileInputStream) is).getChannel();
             FileChannel target = new RandomAccessFile(file, "rw").getChannel()) {
            source.transferTo(0, source.size(), target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStream getInputStream(String fileName) {
        File file = new File(path + fileName);
        if (!file.exists()) {
            throw new CommonException("文件不存在");
        }
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return fileInputStream;
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommonException("获取文件流失败");
        }
    }
}
