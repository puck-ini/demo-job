package org.zchzh.file.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zchzh.file.entity.BaseFile;
import org.zchzh.file.entity.FileFactory;
import org.zchzh.file.entity.Folder;
import org.zchzh.file.service.FileManager;

import java.util.List;


/**
 * @author zengchzh
 * @date 2021/7/27
 */

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {


    @Autowired
    private FileManager fileManager;


    @GetMapping("/{id}")
    public BaseFile get(@PathVariable("id") Long id) {
        return fileManager.get(id);
    }

    @PostMapping("/folder")
    public BaseFile create(@RequestParam("fileName") String fileName,
                           @RequestParam(value = "folderId", required = false) Long folderId) {
        Folder folder = FileFactory.getFile(fileName, Folder.class);
        folder.setFolderId(folderId);
        return fileManager.create(folder);
    }

    @PostMapping("/upload")
    public BaseFile upload(@RequestParam("folderId") Long folderId,
                           @RequestPart("file") MultipartFile file) {
        BaseFile baseFile = FileFactory.getFile(file);
        baseFile.setFolderId(folderId);
        return fileManager.upload(baseFile);
    }

    @GetMapping("/download/{id}")
    public void download(@PathVariable("id") Long id) {
        fileManager.download(id);
    }


    @DeleteMapping
    public void remove(@RequestParam("id") Long id) {
        fileManager.remove(id);
    }

    @GetMapping("/list")
    public List<BaseFile> list() {
        return fileManager.list();
    }

}
