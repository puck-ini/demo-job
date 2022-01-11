package org.zchzh.file.config;

import org.zchzh.file.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zchzh.file.service.impl.DefaultStorageServiceImpl;

/**
 * @author zengchzh
 * @date 2021/7/28
 */

@Configuration
@ConditionalOnMissingBean({MinioConfig.class, MongoConfig.class})
public class DefaultStorageConfig {

    @Bean
    public StorageService storageService(@Autowired StorageProp prop) {
        return new DefaultStorageServiceImpl(prop);
    }
}
