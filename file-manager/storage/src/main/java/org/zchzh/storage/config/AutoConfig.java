package org.zchzh.storage.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author zengchzh
 * @date 2022/1/11
 */
@Configuration
public class AutoConfig {


//
//    @Bean
//    public StorageService storageService(@Autowired StorageProp prop) {
//        return new DefaultStorageServiceImpl(prop);
//    }
//
//    @Bean
//
//    public DefaultStorageConfig defaultStorageConfig() {
//        return new DefaultStorageConfig();
//    }
//
//    @Bean
//    @ConditionalOnProperty(prefix = "file.storage", name = "type", havingValue = Constants.MINIO)
//    public MinioConfig minioConfig() {
//        return new MinioConfig();
//    }
//
//    @Bean
//    @ConditionalOnProperty(prefix = "file.storage", name = "type", havingValue = Constants.MONGO)
//    public MongoConfig mongoConfig() {
//        return new MongoConfig();
//    }

}
