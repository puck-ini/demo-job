package org.zchzh.storage.config;

import lombok.Data;
import org.zchzh.storage.type.StorageType;

/**
 * @author zengchzh
 * @date 2022/1/11
 */

@Data
public class StorageProp {

    private StorageType type = StorageType.DEFAULT;

    private String path;
}
