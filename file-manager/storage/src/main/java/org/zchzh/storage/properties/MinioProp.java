package org.zchzh.storage.properties;

import lombok.Data;
import org.springframework.context.annotation.PropertySource;

/**
 * @author zengchzh
 * @date 2022/1/11
 */
@Data
public class MinioProp {

    private String url;

    private String username;

    private String password;

    private String bucket;
}
