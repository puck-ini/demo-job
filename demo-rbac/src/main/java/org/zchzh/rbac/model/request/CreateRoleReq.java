package org.zchzh.rbac.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zengchzh
 * @date 2021/9/13
 */

@Data
public class CreateRoleReq {

    private String name;

    private String description;
}
