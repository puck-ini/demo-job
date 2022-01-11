package org.zchzh.storage.properties;

import lombok.Data;
import org.springframework.context.annotation.PropertySource;

/**
 * @author zengchzh
 * @date 2022/1/11
 */
@Data
public class MongoProp {

    private String url;

    private String username;

    private String password;

    private String database;

    private String authenticationDb;
}
