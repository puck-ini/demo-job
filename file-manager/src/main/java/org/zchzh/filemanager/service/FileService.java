package org.zchzh.filemanager.service;

import org.springframework.web.multipart.MultipartFile;
import org.zchzh.filemanager.entity.DemoFile;

/**
 * @author zengchzh
 * @date 2021/7/26
 */
public interface FileService {


    DemoFile create(DemoFile demoFile);

    DemoFile addCatalog(Long id, String fileName);

    DemoFile createRoot(String fileName);


    DemoFile upload(Long catalogId, MultipartFile file);

    /**
     * 上传文件
     * @param file
     * @param catalogId
     * @return
     */
    DemoFile upload(DemoFile file, Long catalogId);


    void download(Long id);

    DemoFile get(Long id);

    /**
     * 获取文件
     * @param id
     * @return
     */
    DemoFile getFile(Long id);

    DemoFile getCatalog(Long id);

}
