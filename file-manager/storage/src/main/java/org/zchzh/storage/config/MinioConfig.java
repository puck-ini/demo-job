package org.zchzh.storage.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zchzh.storage.annotation.ConditionOnStorageType;
import org.zchzh.storage.properties.MinioProp;
import org.zchzh.storage.properties.StorageProp;
import org.zchzh.storage.service.StorageService;
import org.zchzh.storage.service.impl.MinioStorageServiceImpl;
import org.zchzh.storage.type.StorageType;

/**
 * @author zengchzh
 * @date 2021/7/27
 */

@Configuration
@ConditionOnStorageType(value = StorageType.MINIO)
@ConditionalOnMissingBean
public class MinioConfig {



    private static final String PREFIX = "http://";

    @Bean
    public MinioClient minioClient(@Autowired MinioProp prop) throws Exception {
        String url = PREFIX + prop.getUrl();
        MinioClient minioClient = MinioClient.builder()
                .endpoint(url)
                .credentials(prop.getUsername(), prop.getPassword())
                .build();
        // 创建默认bucket
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(prop.getBucket()).build())){
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(prop.getBucket()).build());
        }
        return minioClient;
    }

    @Bean
    public StorageService storageService(@Autowired MinioClient minioClient,
                                         @Autowired MinioProp prop) {
        return new MinioStorageServiceImpl(minioClient, prop);
    }
}
