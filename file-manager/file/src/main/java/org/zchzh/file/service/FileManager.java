package org.zchzh.file.service;

import org.zchzh.file.entity.BaseFile;

import java.util.List;

/**
 * @author zengchzh
 * @date 2022/1/12
 */
public interface FileManager extends FileService<BaseFile> {


    List<BaseFile> list();

    @Override
    default Class<BaseFile> getType() { return BaseFile.class;};
}
