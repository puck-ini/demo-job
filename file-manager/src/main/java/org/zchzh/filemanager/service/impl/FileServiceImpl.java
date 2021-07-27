package org.zchzh.filemanager.service.impl;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zchzh.filemanager.entity.DemoFile;
import org.zchzh.filemanager.exception.CommonException;
import org.zchzh.filemanager.repository.DemoFileRepo;
import org.zchzh.filemanager.service.FileService;
import org.zchzh.filemanager.service.StorageService;
import org.zchzh.filemanager.type.FileType;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @author zengchzh
 * @date 2021/7/27
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private DemoFileRepo demoFileRepo;

    @Autowired
    private StorageService storageService;

    @Resource
    private HttpServletResponse response;


    @Override
    public DemoFile create(DemoFile demoFile) {
        return demoFileRepo.save(demoFile);
    }


    @Override
    public DemoFile addCatalog(Long id, String fileName) {
        DemoFile demoFile = getCatalog(id);
        DemoFile catalog = DemoFile.newCatalog(fileName);
        demoFile.getChildren().add(catalog);
        demoFileRepo.save(demoFile);
        return catalog;
    }

    @Override
    public DemoFile createRoot(String fileName) {
        return create(DemoFile.newCatalog(fileName));
    }

    @Override
    public DemoFile upload(Long catalogId, MultipartFile file) {
        return upload(new DemoFile(file), catalogId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DemoFile upload(DemoFile file, Long catalogId) {
        DemoFile demoFile = getCatalog(catalogId);
        demoFile.getChildren().add(file);
        demoFileRepo.save(demoFile);
        storageService.upload(file.getFullFileName(), file.getInputStream());
        return file;
    }

    @Override
    public void download(Long id) {
        DemoFile demoFile = getFile(id);
        try {
            String fileName = URLEncoder.encode(demoFile.getFileName(),"UTF-8");
            response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + fileName + ";filename*=utf-8''" + fileName);
            @Cleanup OutputStream os = response.getOutputStream();
            //获取数据
            @Cleanup InputStream inputStream = demoFile.getInputStream();
            IOUtils.copy(inputStream,os);
            os.flush();
        } catch (IOException e) {
            log.error("download error", e);
        }
    }

    @Override
    public DemoFile get(Long id) {
        DemoFile file = demoFileRepo.findById(id).orElseThrow(() -> new CommonException("文件不存在"));
        if (file.getFileType() == FileType.FILE) {
            file.setInputStream(storageService.getInputStream(file.getFullFileName()));
        }
        return file;
    }

    @Override
    public DemoFile getFile(Long id) {
        DemoFile file = get(id);
        if (file.getFileType() != FileType.FILE) {
            throw new CommonException("查找的不是文件");
        }
        return file;
    }

    @Override
    public DemoFile getCatalog(Long id) {
        DemoFile demoFile = get(id);
        if (demoFile.getFileType() != FileType.CATALOG) {
            throw new CommonException("查找的不是目录");
        }
        return demoFile;
    }
}
