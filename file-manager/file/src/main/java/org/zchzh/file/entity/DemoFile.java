package org.zchzh.file.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.zchzh.file.type.FileType;
import org.zchzh.file.util.MD5Util;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author zengchzh
 * @date 2021/7/26
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Slf4j
@NoArgsConstructor
public class DemoFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String suffix;

    private String originName;

    private Long size;

    private String md5;

    @JsonIgnore
    @Transient
    private InputStream inputStream;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<DemoFile> children;

    public DemoFile(MultipartFile file) {
        fileName = UUID.randomUUID().toString();
        originName = file.getOriginalFilename();
        if (originName != null) {
            suffix = originName.substring(originName.lastIndexOf(".") + 1);
        }
        size = file.getSize();
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            log.error("MultipartFile getInputStream error", e);
        }
        fileType = FileType.FILE;
        children = null;
        try {
            md5 = MD5Util.getMd5(file.getInputStream());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static DemoFile newCatalog(String fileName) {
        DemoFile demoFile = new DemoFile();
        demoFile.setFileName(fileName);
        demoFile.setFileType(FileType.CATALOG);
        demoFile.setChildren(new ArrayList<>());
        return demoFile;
    }

    public String getFullFileName() {
        if (fileType == FileType.CATALOG) {
            return fileName;
        }
        return fileName + "." + suffix;
    }

//    public synchronized Long getSize() {
//        if (fileType == FileType.CATALOG) {
//            for (DemoFile file : children) {
//                size = (size == null ? 0 : size) + file.getSize();
//            }
//        }
//        return size;
//    }

    public InputStream getInputStream() {
        if (fileType == FileType.CATALOG) {
            return null;
        }
        return inputStream;
    }

}
