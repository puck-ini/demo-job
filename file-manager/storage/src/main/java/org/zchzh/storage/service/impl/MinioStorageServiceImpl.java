package org.zchzh.storage.service.impl;

import io.minio.RemoveObjectArgs;
import org.zchzh.storage.properties.MinioProp;
import org.zchzh.storage.properties.StorageProp;
import org.zchzh.storage.service.StorageService;
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

    private final MinioProp prop;

    public MinioStorageServiceImpl(MinioClient minioClient, MinioProp prop) {
        this.minioClient = minioClient;
        this.prop = prop;
    }

    @Override
    public void upload(String fileName, InputStream is) {
        try (InputStream inputStream = is){
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(prop.getBucket())
                    .object(fileName)
                    .stream(inputStream, inputStream.available(), -1)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStream getInputStream(String fileName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(prop.getBucket())
                    .object(fileName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(prop.getBucket()).object(fileName).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
