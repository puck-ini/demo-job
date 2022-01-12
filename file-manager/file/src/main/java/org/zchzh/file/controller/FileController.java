package org.zchzh.file.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zchzh.file.entity.DemoFile;
import org.zchzh.file.service.FileService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author zengchzh
 * @date 2021/7/27
 */

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/catalog")
    public DemoFile getCatalog(@RequestParam("id") Long id) {
        return fileService.getCatalog(id);
    }

    @GetMapping
    public DemoFile getFile(@RequestParam("id") Long id) {
        return fileService.getFile(id);
    }

    @PostMapping("/create/catalog")
    public DemoFile createCatalog(@RequestParam("catalogId") Long id,
                           @RequestParam("fileName") String fileName) {
        return fileService.addCatalog(id, fileName);
    }

    @PostMapping("/create/root")
    public DemoFile createRoot(@RequestParam("fileName") String fileName) {
        return fileService.createRoot(fileName);
    }

    @PostMapping("/upload")
    public DemoFile upload(@RequestParam("catalogId") Long id,
                           @RequestPart("file") MultipartFile file) {
        return fileService.upload(id, file);
    }

    @PostMapping("/batch/upload")
    public List<DemoFile> batchUpload(@RequestParam("catalogId") Long id,
                                      @RequestPart("file") MultipartFile[] files) {
        long start  = System.currentTimeMillis();
        List<DemoFile> result = fileService.batchUpload(id, files);
        log.info("cost : " + (System.currentTimeMillis() - start));
        return result;
    }

    @Deprecated
    @PostMapping("/async/upload")
    public List<DemoFile> asyncUpload(@RequestParam("catalogId") Long id,
                                      @RequestPart("file") MultipartFile[] files) {
        long start  = System.currentTimeMillis();
        List<CompletableFuture<DemoFile>> futureList = new ArrayList<>();
        for (MultipartFile file : files) {
            CompletableFuture<DemoFile> future = CompletableFuture.supplyAsync(() -> fileService.upload(id, file));
            futureList.add(future);
        }
        List<DemoFile> result = futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
        log.info("cost : " + (System.currentTimeMillis() - start));
        return result;
    }

    @GetMapping("/download")
    public void download(@RequestParam("fileId") Long id) {
        fileService.download(id);
    }
}
