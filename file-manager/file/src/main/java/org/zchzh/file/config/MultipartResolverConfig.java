package org.zchzh.file.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;

/**
 * @author zengchzh
 * @date 2022/6/20
 */

@Configuration
public class MultipartResolverConfig {

    @Bean
    public MultipartResolver multipartResolver() {
        return new TestCommonsMultipartResolver();
    }
}
