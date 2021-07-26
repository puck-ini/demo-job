package org.zchzh.filemanager.entity;

import lombok.Data;
import org.zchzh.filemanager.type.FileType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author zengchzh
 * @date 2021/7/26
 */

@Data
@Entity
public class DemoFile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String suffix;

    private String originName;

    private Long size;

    private FileType fileType;

    private Long parentId;

}
