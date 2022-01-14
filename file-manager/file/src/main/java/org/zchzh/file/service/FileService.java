package org.zchzh.file.service;


import org.zchzh.file.entity.BaseFile;

/**
 * @author zengchzh
 * @date 2021/7/26
 */
public interface FileService<T extends BaseFile> {

    /**
     * 获取文件
     * @param id
     * @return
     */
    T get(Long id);


    T create(T file);
    /**
     * 上传文件
     * @param file
     * @return
     */
    T upload(T file);

    /**
     * 下载文件
     * @param id
     */
    void download(Long id);

    /**
     * 删除文件
     * @param id
     */
    void remove(Long id);

    Class<T> getType();

}
