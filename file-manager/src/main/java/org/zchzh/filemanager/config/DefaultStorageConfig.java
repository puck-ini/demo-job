package org.zchzh.filemanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zchzh.filemanager.service.StorageService;
import org.zchzh.filemanager.service.impl.DefaultStorageServiceImpl;

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
