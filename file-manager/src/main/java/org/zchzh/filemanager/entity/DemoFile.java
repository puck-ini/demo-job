package org.zchzh.filemanager.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zchzh.filemanager.type.FileType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * @author zengchzh
 * @date 2021/7/26
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class DemoFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String suffix;

    private String originName;

    private Long size;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<DemoFile> children;

    public String getFullFileName() {
        if (fileType == FileType.CATALOG) {
            return fileName;
        }
        return fileName + "." + suffix;
    }

    public synchronized Long getSize() {
        if (fileType == FileType.CATALOG) {
            for (DemoFile file : children) {
                size = size + file.getSize();
            }
        }
        return size;
    }

}
