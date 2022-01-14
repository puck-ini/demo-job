package org.zchzh.file.service.impl;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zchzh.file.entity.BaseFile;
import org.zchzh.file.entity.Folder;
import org.zchzh.file.entity.NetFile;
import org.zchzh.file.exception.CommonException;
import org.zchzh.file.repository.BaseFileRepo;
import org.zchzh.file.repository.NetFileRepo;
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
 * @date 2022/1/14
 */

@Slf4j
@Component
public class NetFileServiceImpl implements FileService<NetFile> {

    @Autowired
    private NetFileRepo netFileRepo;

    @Autowired
    private BaseFileRepo fileRepo;

    @Autowired
    private StorageService storageService;

    @Resource
    private HttpServletResponse response;

    @Override
    public NetFile get(Long id) {
        return netFileRepo.findById(id).orElse(new NetFile());
    }

    @Override
    public NetFile create(NetFile file) {
        if (Objects.nonNull(file.getFolderId())) {
            BaseFile folder = fileRepo.findById(file.getFolderId()).orElse(new Folder());
            if (!(folder instanceof Folder) || folder.isNull()) {
                throw new CommonException("文件夹不存在");
            }
        }
        NetFile sFile = netFileRepo.save(file);
        storageService.upload(sFile.getFileName(), sFile.getInputStream());
        return sFile;
    }

    @Override
    public NetFile upload(NetFile file) {
        return create(file);
    }

    @Override
    public void download(Long id) {
        NetFile file = get(id);
        if (!file.isNull()) {
            try {
                String fileName = URLEncoder.encode(file.getFileName(),"UTF-8");
                response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
                response.setHeader("Content-Disposition",
                        "attachment;filename=" + fileName + ";filename*=utf-8''" + fileName);
                @Cleanup OutputStream os = response.getOutputStream();
                //获取数据
                @Cleanup InputStream is = file.getInputStream();
                IOUtils.copy(is,os);
                os.flush();
            } catch (IOException e) {
                log.error("download error", e);
            }
        }
    }

    @Override
    public void remove(Long id) {
        NetFile file = get(id);
        if (!file.isNull()) {
            netFileRepo.deleteById(id);
            storageService.remove(file.getFileName());
        }
    }

    @Override
    public Class<NetFile> getType() {
        return NetFile.class;
    }
}
