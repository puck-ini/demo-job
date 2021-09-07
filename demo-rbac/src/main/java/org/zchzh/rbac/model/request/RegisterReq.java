package org.zchzh.rbac.model.request;

import lombok.Data;

/**
 * @author zengchzh
 * @date 2021/9/7
 */
@Data
public class RegisterReq {

    private String username;

    private String password;

    private String name;
}
