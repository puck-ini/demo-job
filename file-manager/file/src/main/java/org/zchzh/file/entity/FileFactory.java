package org.zchzh.file.entity;

import org.springframework.web.multipart.MultipartFile;
import org.zchzh.file.util.MD5Util;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * @author zengchzh
 * @date 2022/1/13
 */
public class FileFactory {

    public static BaseFile getFile(MultipartFile file) {
        VirtualFile virtualFile = new VirtualFile();
        virtualFile.setFileName(UUID.randomUUID().toString());
        virtualFile.setOriginName(file.getOriginalFilename());
        if (Objects.nonNull(virtualFile.getOriginName())) {
            virtualFile.setSuffix(virtualFile.getOriginName().substring(virtualFile.getOriginName().lastIndexOf(".") + 1));
        }
        virtualFile.setSize(file.getSize());
        try {
            virtualFile.setInputStream(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        virtualFile.setMd5(MD5Util.getMd5(virtualFile.getInputStream()));
        return virtualFile;
    }

    public static <T extends BaseFile> T getFile(String fileName, Class<T> clazz) {
        BaseFile baseFile = new BaseFile();
        try {
            baseFile = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        baseFile.setFileName(fileName);
        return (T) baseFile;
    }
    
}
