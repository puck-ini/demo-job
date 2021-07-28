package org.zchzh.filemanager.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.zchzh.filemanager.constants.Constants;
import org.zchzh.filemanager.service.StorageService;
import org.zchzh.filemanager.service.impl.MinioStorageServiceImpl;

import java.util.Objects;

/**
 * @author zengchzh
 * @date 2021/7/27
 */

@Configuration
@ConditionalOnProperty(prefix = "file.storage", name = "type", havingValue = Constants.MINIO)
public class MinioConfig {


//    private MinioClient minioClient;

    private static final String PREFIX = "http://";

    @Bean
    public MinioClient minioClient(@Autowired StorageProp prop) throws Exception {
        String url = PREFIX + prop.getUrl();
        MinioClient minioClient = MinioClient.builder()
                .endpoint(url)
                .credentials(prop.getUsername(), prop.getPassword())
                .build();
        // 创建默认bucket
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(prop.getDatabase()).build())){
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(prop.getDatabase()).build());
        }
        return minioClient;
    }

    @Bean
    public StorageService storageService(@Autowired MinioClient minioClient,
                                         @Autowired StorageProp prop) {
        return new MinioStorageServiceImpl(minioClient, prop);
    }
}
