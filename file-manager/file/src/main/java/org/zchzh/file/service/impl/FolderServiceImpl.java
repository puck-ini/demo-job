package org.zchzh.file.service.impl;

import cn.hutool.core.util.ZipUtil;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zchzh.file.entity.BaseFile;
import org.zchzh.file.entity.Folder;
import org.zchzh.file.entity.VirtualFile;
import org.zchzh.file.exception.CommonException;
import org.zchzh.file.repository.BaseFileRepo;
import org.zchzh.file.repository.FolderRepo;
import org.zchzh.file.repository.VirtualFileRepo;
import org.zchzh.file.service.FileService;
import org.zchzh.file.util.SpringApplicationContextUtil;
import org.zchzh.storage.service.StorageService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zengchzh
 * @date 2022/1/12
 */
@Slf4j
@Component
public class FolderServiceImpl implements FileService<Folder> {

    @Autowired
    private FolderRepo folderRepo;

    @Autowired
    private StorageService storageService;

    @Resource
    private HttpServletResponse response;

    @Override
    public Folder get(Long id) {
        return folderRepo.findById(id).orElse(new Folder());
    }

    @Override
    public Folder create(Folder file) {
        if (Objects.nonNull(file.getFolderId())) {
            Folder folder = get(file.getFolderId());
            if (folder.isNull()) {
                throw new CommonException("文件夹不存在");
            }
        }
        return folderRepo.save(file);
    }

    @Override
    public Folder upload(Folder file) {
        return null;
    }

    @Override
    public void download(Long id) {
        downloadCatalogByZip(get(id));
    }

    private final String tempDir = System.getProperty("java.io.tmpdir");

    /**
     * 下载压缩包
     */
    private void downloadCatalogByZip(Folder folder) {
        try {
            persistFile(folder, tempDir);
            String path = tempDir + folder.getFileName();
            String downloadName = URLEncoder.encode(folder.getFileName()+ ".zip","UTF-8");
            log.info(downloadName);
            response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + downloadName + ";filename*=utf-8''" + downloadName);
            @Cleanup OutputStream os = response.getOutputStream();
            File zipFile = ZipUtil.zip(path);
            @Cleanup InputStream is = new FileInputStream(zipFile);
            IOUtils.copy(is,os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void persistFile(BaseFile baseFile, String path) throws IOException {
        if (baseFile instanceof Folder) {
            Folder folder = (Folder) baseFile;
            String filePath = path + baseFile.getFileName() + File.separator;
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            for (BaseFile tempFile : folder.getFileList()) {
                persistFile(tempFile, filePath);
            }
            return;
        }
        File file = new File(path + File.separator + baseFile.getFileName());
        if (!file.exists()) {
            file.createNewFile();
            @Cleanup FileChannel source = ((FileInputStream) storageService.getInputStream(baseFile.getFileName())).getChannel();
            @Cleanup FileChannel target = new RandomAccessFile(file, "rw").getChannel();
            source.transferTo(0, source.size(), target);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void remove(Long id) {
        Folder folder = get(id);
        for (BaseFile file : folder.getFileList()) {
            if (file instanceof Folder) {
                remove(file.getId());
            } else {
                ((FileService)((Map) SpringApplicationContextUtil.getBean("fileServiceMap")).get(file.getClass())).remove(file.getId());
            }
        }
        folderRepo.deleteById(id);
    }

    @Override
    public Class<Folder> getType() {
        return Folder.class;
    }
}
