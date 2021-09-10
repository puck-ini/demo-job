package org.zchzh.rbac.model.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;
import org.zchzh.rbac.model.request.LoginContext;
import org.zchzh.rbac.model.request.LoginReq;

/**
 * @author zengchzh
 * @date 2021/9/10
 */
public class LoginSuccessEvent extends ApplicationEvent {

    private String username;

    private String reqIp;

    private LoginContext.CacheKey keys;

    public LoginSuccessEvent(Object source, String username, String reqIp, LoginContext.CacheKey keys) {
        super(source);
        this.username = username;
        this.reqIp = reqIp;
        this.keys = keys;
    }

    public String getUsername() {
        return username;
    }

    public String getReqIp() {
        return reqIp;
    }

    public LoginContext.CacheKey getKeys() {
        return keys;
    }
}
