package org.zchzh.storage.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.zchzh.storage.type.StorageType;

/**
 * @author zengchzh
 * @date 2021/7/27
 */

@Data
@ConfigurationProperties(prefix = "file.storage")
public class StorageProp {

    private StorageType type = StorageType.DEFAULT;

    private String path = type.getPath();
}
