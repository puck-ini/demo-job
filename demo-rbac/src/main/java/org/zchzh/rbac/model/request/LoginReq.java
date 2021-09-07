package org.zchzh.rbac.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zengchzh
 * @date 2021/9/7
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReq implements Serializable {

    private String username;

    private String password;

}
