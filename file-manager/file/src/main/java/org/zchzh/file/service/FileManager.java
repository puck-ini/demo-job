package org.zchzh.file.service;

import org.zchzh.file.entity.BaseFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zengchzh
 * @date 2022/1/12
 */
public interface FileManager extends FileService<BaseFile> {


    List<BaseFile> list();

    void uploadChunk(HttpServletRequest request, HttpServletResponse response, Long folderId);


    void downloadChunk(HttpServletRequest request, HttpServletResponse response, Long id);

    @Override
    default Class<BaseFile> getType() { return BaseFile.class;};
}
