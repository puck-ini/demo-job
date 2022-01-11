package org.zchzh.storage.config;

import lombok.extern.slf4j.Slf4j;
import org.zchzh.storage.annotation.ConditionOnStorageType;
import org.zchzh.storage.properties.DefaultProp;
import org.zchzh.storage.properties.StorageProp;
import org.zchzh.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zchzh.storage.service.impl.DefaultStorageServiceImpl;
import org.zchzh.storage.type.StorageType;

/**
 * @author zengchzh
 * @date 2021/7/28
 */

@Slf4j
@Configuration
@ConditionOnStorageType(value = StorageType.DEFAULT)
public class DefaultStorageConfig {

    @Bean
    public StorageService storageService(@Autowired DefaultProp prop) {
        log.info("storage service : " + StorageType.DEFAULT);
        return new DefaultStorageServiceImpl(prop.getPath());
    }
}
