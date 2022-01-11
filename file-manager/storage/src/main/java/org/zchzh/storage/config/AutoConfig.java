package org.zchzh.storage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zengchzh
 * @date 2022/1/11
 */
@Configuration
public class AutoConfig {

    @Bean
    public StorageProp storageProp() {
        return new StorageProp();
    }
}
