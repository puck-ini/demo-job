package org.zchzh.file.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.zchzh.file.util.MD5Util;
import org.zchzh.file.util.SpringApplicationContextUtil;
import org.zchzh.storage.service.StorageService;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author zengchzh
 * @date 2022/1/12
 */

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class VirtualFile extends BaseFile implements StorageFile {

    private static final long serialVersionUID = -5472199178409502157L;

    private String suffix;

    private String originName;

    private Long size;

    private String md5;

    private String contentType;

    @JsonIgnore
    @Transient
    private InputStream inputStream;

    @Override
    public String getFileName() {
        return super.getFileName() + "." + suffix;
    }

    @Override
    public InputStream getInputStream() {
        if (Objects.isNull(inputStream)) {
            inputStream = SpringApplicationContextUtil.getBean(StorageService.class).getInputStream(getFileName());
            return inputStream;
        }
        return inputStream;
    }
}
