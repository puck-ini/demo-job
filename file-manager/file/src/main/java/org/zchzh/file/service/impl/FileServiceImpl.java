package org.zchzh.file.service.impl;

import cn.hutool.core.util.ZipUtil;
import org.zchzh.file.exception.CommonException;
import org.zchzh.file.type.FileType;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zchzh.file.entity.DemoFile;
import org.zchzh.file.repository.DemoFileRepo;
import org.zchzh.file.service.FileService;
import org.zchzh.storage.service.StorageService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

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

    private final String tempDir = System.getProperty("java.io.tmpdir");


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
        return upload(catalogId, new DemoFile(file));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DemoFile upload(Long catalogId, DemoFile file) {
        DemoFile catalog = getCatalog(catalogId);
        catalog.getChildren().add(file);
        demoFileRepo.save(catalog);
        storageService.upload(file.getFullFileName(), file.getInputStream());
        return file;
    }

    @Override
    public List<DemoFile> batchUpload(Long catalogId, MultipartFile[] files) {
        List<DemoFile> demoFileList = new ArrayList<>();
        for (MultipartFile file : files) {
            demoFileList.add(upload(catalogId, file));
        }
        return demoFileList;
    }

    @Override
    public void download(Long id) {
        DemoFile demoFile = get(id);
        if (demoFile.getFileType() == FileType.FILE) {
            downloadFile(demoFile);
        } else {
            downloadCatalogByZip(demoFile);
        }
    }

    private void downloadFile(DemoFile demoFile) {
        try {
            String fileName = URLEncoder.encode(demoFile.getOriginName(),"UTF-8");
            response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + fileName + ";filename*=utf-8''" + fileName);
            @Cleanup OutputStream os = response.getOutputStream();
            //获取数据
            @Cleanup InputStream is = demoFile.getInputStream();
            IOUtils.copy(is,os);
            os.flush();
        } catch (IOException e) {
            log.error("download error", e);
        }
    }

//    @Value("${file.storage.database}")

    /**
     * 下载压缩包
     */
    private void downloadCatalogByZip(DemoFile demoFile) {
        try {
            persistFile(demoFile, tempDir);
            String path = tempDir + demoFile.getFileName();
            String downloadName = URLEncoder.encode(demoFile.getFileName()+ ".zip","UTF-8");
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


    private void persistFile(DemoFile demoFile, String path) throws IOException {
        if (demoFile.getFileType() == FileType.CATALOG) {
            String filePath = path + demoFile.getFileName() + File.separator;
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            for (DemoFile tempFile : demoFile.getChildren()) {
                persistFile(tempFile, filePath);
            }
            return;
        }
        File file = new File(path + File.separator + demoFile.getOriginName());
        if (file.exists()) {
            file = new File(path + File.separator + demoFile.getOriginName() + "_" + demoFile.getFullFileName());
        } else {
            file.createNewFile();
        }
        @Cleanup FileChannel source = ((FileInputStream) storageService.getInputStream(demoFile.getFullFileName())).getChannel();
        @Cleanup FileChannel target = new RandomAccessFile(file, "rw").getChannel();
        source.transferTo(0, source.size(), target);
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
