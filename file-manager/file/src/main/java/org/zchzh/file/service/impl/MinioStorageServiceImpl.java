package org.zchzh.file.service.impl;

import org.zchzh.file.config.StorageProp;
import org.zchzh.file.service.StorageService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import java.io.InputStream;

/**
 * @author zengchzh
 * @date 2021/7/27
 */

public class MinioStorageServiceImpl implements StorageService {

    private final MinioClient minioClient;

    private final StorageProp prop;

    public MinioStorageServiceImpl(MinioClient minioClient, StorageProp prop) {
        this.minioClient = minioClient;
        this.prop = prop;
    }

    @Override
    public void upload(String fileName, InputStream is) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(prop.getDatabase())
                    .object(fileName)
                    .stream(is, is.available(), -1)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStream getInputStream(String fileName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(prop.getDatabase())
                    .object(fileName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
