package org.zchzh.storage.service;

import java.io.InputStream;

/**
 * @author zengchzh
 * @date 2021/7/26
 */
public interface StorageService {

    /**
     * 保存文件
     * @param fileName 文件名
     * @param is 文件流
     */
    void save(String fileName, InputStream is);

    /**
     * 通过文件名获取文件流
     * @param fileName 文件名
     * @return 返回文件名对应的文件流
     */
    InputStream getInputStream(String fileName);

    /**
     * 删除文件
     * @param fileName 文件名
     */
    void remove(String fileName);


}
