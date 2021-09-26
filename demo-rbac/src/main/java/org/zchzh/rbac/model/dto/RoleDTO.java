package org.zchzh.rbac.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zengchzh
 * @date 2021/9/13
 */
@Data
public class RoleDTO implements Serializable {
    private static final long serialVersionUID = -4072426211728878567L;

    private String name;

    private String description;
}
