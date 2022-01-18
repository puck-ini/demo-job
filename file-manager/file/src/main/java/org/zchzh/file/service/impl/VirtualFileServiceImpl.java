package org.zchzh.file.service.impl;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zchzh.file.entity.BaseFile;
import org.zchzh.file.entity.Folder;
import org.zchzh.file.entity.VirtualFile;
import org.zchzh.file.exception.CommonException;
import org.zchzh.file.repository.BaseFileRepo;
import org.zchzh.file.repository.VirtualFileRepo;
import org.zchzh.file.service.FileService;
import org.zchzh.storage.service.StorageService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * @author zengchzh
 * @date 2022/1/12
 */
@Slf4j
@Component
public class VirtualFileServiceImpl implements FileService<VirtualFile> {

    @Autowired
    private VirtualFileRepo virtualFileRepo;

    @Autowired
    private StorageService storageService;

    @Autowired
    private BaseFileRepo fileRepo;

    @Resource
    private HttpServletResponse response;

    @Override
    public VirtualFile get(Long id) {
        return virtualFileRepo.findById(id).orElse(new VirtualFile());
    }

    @Override
    public VirtualFile create(VirtualFile file) {
        if (Objects.nonNull(file.getFolderId())) {
            BaseFile folder = fileRepo.findById(file.getFolderId()).orElse(new Folder());
            if (!(folder instanceof Folder) || folder.isNull()) {
                throw new CommonException("文件夹不存在");
            }
        }
        return virtualFileRepo.save(file);
    }

    @Override
    public VirtualFile upload(VirtualFile file) {
        BaseFile folder = fileRepo.findById(file.getFolderId()).orElse(new Folder());
        if (!(folder instanceof Folder) || folder.isNull()) {
            throw new CommonException("上传到的文件不是一个文件夹");
        }
        VirtualFile newFile = virtualFileRepo.save(file);
        storageService.save(file.getFileName(), file.getInputStream());
        return newFile;
    }

    @Override
    public void download(Long id) {
        VirtualFile file = get(id);
        if (!file.isNull()) {
            try {
                String fileName = URLEncoder.encode(file.getOriginName(),"UTF-8");
                response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + fileName + ";filename*=utf-8''" + fileName);
                @Cleanup OutputStream os = response.getOutputStream();
                //获取数据
                @Cleanup InputStream is = file.getInputStream();
                IOUtils.copy(is, os);
                os.flush();
            } catch (IOException e) {
                log.error("download error", e);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void remove(Long id) {
        VirtualFile file = get(id);
        if (!file.isNull()) {
            virtualFileRepo.deleteById(id);
            storageService.remove(file.getFileName());
        }
    }

    @Override
    public Class<VirtualFile> getType() {
        return VirtualFile.class;
    }
}
