package org.zchzh.filemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zchzh.filemanager.entity.DemoFile;
import org.zchzh.filemanager.service.FileService;

/**
 * @author zengchzh
 * @date 2021/7/27
 */

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

    @GetMapping("/download")
    public void download(@RequestParam("fileId") Long id) {
        fileService.download(id);
    }
}
