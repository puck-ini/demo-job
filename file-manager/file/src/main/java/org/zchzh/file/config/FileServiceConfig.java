package org.zchzh.file.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zchzh.file.entity.BaseFile;
import org.zchzh.file.service.FileService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zengchzh
 * @date 2022/1/12
 */

@Configuration
public class FileServiceConfig implements ApplicationContextAware {


    @Bean
    public Map<Class<? extends BaseFile>, FileService<? extends BaseFile>> fileServiceMap() {
        Map<Class<? extends BaseFile>, FileService<? extends BaseFile>> fileServiceMap = new HashMap<>();
        context.getBeansOfType(FileService.class).values().forEach(item -> {
            if (!Objects.equals(item.getType(), BaseFile.class)) {
                fileServiceMap.put(item.getType(), item);
            }
        });
        return fileServiceMap;
    }

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
