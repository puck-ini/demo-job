package org.zchzh.rbac.model.dto;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zengchzh
 * @date 2021/9/7
 */
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 2423765515134605549L;
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
}
