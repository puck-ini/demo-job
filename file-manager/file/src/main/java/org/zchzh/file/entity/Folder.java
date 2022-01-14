package org.zchzh.file.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.util.CollectionUtils;
import org.zchzh.file.repository.BaseFileRepo;
import org.zchzh.file.service.impl.FolderServiceImpl;
import org.zchzh.file.util.SpringApplicationContextUtil;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author zengchzh
 * @date 2022/1/12
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Folder extends BaseFile {

    private static final long serialVersionUID = 807907644892853657L;


    @Transient
    private Folder folder;

    @Transient
    private List<? extends BaseFile> fileList;

    public List<? extends BaseFile> getFileList() {
        if (Objects.isNull(fileList) && !isNull()) {
            // 文件较多时不适合用这种方法
            fileList = SpringApplicationContextUtil.getBean(BaseFileRepo.class).findAllByFolderId(super.getId());
        }
        return fileList;
    }
}
