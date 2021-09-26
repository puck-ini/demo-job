package org.zchzh.rbac.model.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zengchzh
 * @date 2021/9/10
 */

@Data
@ToString
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = -4672997795915244784L;
    private String username;

    private String name;

    private Integer age;

    private String phone;

    private String mail;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private String token;
}
