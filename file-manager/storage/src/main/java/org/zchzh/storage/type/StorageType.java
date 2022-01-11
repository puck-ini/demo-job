package org.zchzh.storage.type;

/**
 * @author zengchzh
 * @date 2022/1/11
 */
public enum StorageType {

    /**
     * 默认存储方式
     */
    DEFAULT("classpath:default.yml"),
    /**
     * mongodb 存储方式
     */
    MONGODB("classpath:mongodb.yml"),
    /**
     * minio 存储方式
     */
    MINIO("classpath:minio.yml");


    private final String path;

    StorageType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
