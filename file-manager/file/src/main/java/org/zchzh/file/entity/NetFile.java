package org.zchzh.file.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.io.FileUtils;
import org.zchzh.file.util.SpringApplicationContextUtil;
import org.zchzh.storage.service.StorageService;

import javax.persistence.Entity;
import java.io.*;
import java.net.URL;
import java.util.Objects;

/**
 * @author zengchzh
 * @date 2022/1/14
 */

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class NetFile extends BaseFile {

    private static final long serialVersionUID = -9134946242953224031L;

    private String url;

    private String md5;

    @JsonIgnore
    public InputStream getInputStream() {
        InputStream inputStream = SpringApplicationContextUtil.getBean(StorageService.class).getInputStream(getFileName());
        if (Objects.isNull(inputStream)) {
            inputStream = getUrlIs();
        }
        return inputStream;
    }

    private InputStream getUrlIs() {
        File file = new File(TEMP_DIR + getFileName());
        try {
            FileUtils.copyURLToFile(new URL(url), file);
            return new FileInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
