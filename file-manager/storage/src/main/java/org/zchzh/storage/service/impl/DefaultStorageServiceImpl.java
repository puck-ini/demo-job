package org.zchzh.storage.service.impl;

import org.zchzh.storage.properties.StorageProp;
import org.zchzh.storage.service.StorageService;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Objects;

/**
 * @author zengchzh
 * @date 2021/7/28
 */
public class DefaultStorageServiceImpl implements StorageService {

    private String path;

    public DefaultStorageServiceImpl(String path) {
        if (Objects.nonNull(path)) {
            this.path = path;
        } else {
            this.path = System.getProperty("java.io.tmpdir");
        }
        if (!this.path.endsWith(File.separator)) {
            this.path = this.path + File.separator;
        }
    }

    @Override
    public void save(String fileName, InputStream is) {
        File file = new File(path + fileName);
        try (InputStream inputStream = is;
             FileChannel source = ((FileInputStream) inputStream).getChannel();
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
            throw new RuntimeException("文件不存在");
        }
        try {
            return new FileInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("获取文件流失败");
        }
    }

    @Override
    public void remove(String fileName) {
        File file = new File(path + fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
