package org.zchzh.file.service.impl;

import org.zchzh.file.entity.BaseFile;
import org.zchzh.file.repository.BaseFileRepo;
import org.zchzh.file.service.FileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zchzh.file.service.FileService;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zengchzh
 * @date 2021/7/27
 */
@Slf4j
@Service
public class FileManagerImpl implements FileManager {

    @Resource
    private Map<Class<? extends BaseFile>, FileService<? super BaseFile>> fileServiceMap;

    @Autowired
    private BaseFileRepo fileRepo;

    @Override
    public BaseFile get(Long id) {
        BaseFile baseFile = fileRepo.findById(id).orElse(BaseFile.getNull());
        if (baseFile.isNull()) {
            return baseFile;
        }
        return fileServiceMap.get(baseFile.getClass()).get(id);
    }

    @Override
    public BaseFile create(BaseFile file) {
        return fileServiceMap.get(file.getClass()).create(file);
    }

    @Override
    public BaseFile upload(BaseFile file) {
        return fileServiceMap.get(file.getClass()).upload(file);
    }

    @Override
    public void download(Long id) {
        BaseFile file = get(id);
        if (!file.isNull()) {
            fileServiceMap.get(file.getClass()).download(id);
        }
    }

    @Override
    public void remove(Long id) {
        BaseFile file = get(id);
        if (!file.isNull()) {
            fileServiceMap.get(file.getClass()).remove(id);
        }
    }

    @Override
    public List<BaseFile> list() {
        return fileRepo.findAll();
    }

}
