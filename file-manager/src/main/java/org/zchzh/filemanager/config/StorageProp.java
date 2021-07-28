package org.zchzh.filemanager.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zengchzh
 * @date 2021/7/27
 */

@Data
@Component
@ConfigurationProperties(prefix = "file.storage")
public class StorageProp {

    private String type;

    private String url;

    private String username;

    private String password;

    private String database;

    private String authenticationDb;
}
