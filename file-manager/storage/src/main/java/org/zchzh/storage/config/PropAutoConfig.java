package org.zchzh.storage.config;

import cn.hutool.setting.yaml.YamlUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.zchzh.storage.annotation.ConditionOnStorageType;
import org.zchzh.storage.properties.DefaultProp;
import org.zchzh.storage.properties.MinioProp;
import org.zchzh.storage.properties.MongoProp;
import org.zchzh.storage.properties.StorageProp;
import org.zchzh.storage.type.StorageType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author zengchzh
 * @date 2022/1/11
 */
@Configuration
public class PropAutoConfig {

    @Bean
    public StorageProp storageProp() {
        return new StorageProp();
    }

    @Bean
    @ConditionOnStorageType(value = StorageType.DEFAULT)
    public DefaultProp defaultProp(StorageProp prop) {
        return load(prop.getPath(), DefaultProp.class);
    }

    @Bean
    @ConditionOnStorageType(value = StorageType.MINIO)
    public MinioProp minioProp(StorageProp prop) {
        return load(prop.getPath(), MinioProp.class);
    }

    @Bean
    @ConditionOnStorageType(value = StorageType.MONGODB)
    public MongoProp mongoProp(StorageProp prop) {
        return load(prop.getPath(), MongoProp.class);
    }


    private  <T> T load(String path, Class<T> type) {
        InputStream in = null;
        try {
            in = new FileInputStream(ResourceUtils.getFile(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return YamlUtil.load(in, type);
    }
}
