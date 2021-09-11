package org.zchzh.rbac.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationContext;
import org.zchzh.rbac.model.event.LoginFailEvent;
import org.zchzh.rbac.model.event.LoginSuccessEvent;

/**
 * @author zengchzh
 * @date 2021/9/7
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginContext extends PipelineContext{

    private LoginReq req;

    private String reqIp;

    private CacheKey keys;

    public LoginContext(LoginReq req, String reqIp, ApplicationContext context) {
        this.req = req;
        this.reqIp = reqIp;
        this.context = context;
        this.keys = new CacheKey(this.req.getUsername(), this.reqIp);
        this.successEvent = new LoginSuccessEvent(this, req.getUsername(), reqIp, keys);
        this.failEvent = new LoginFailEvent(this, req.getUsername(), reqIp, keys);
    }

    public static class CacheKey {
        private String username;

        private String usernameIp;

        public CacheKey(String username, String ip) {
            this.username = username;
            this.usernameIp = username + ":" + ip;
        }

        public String getUsername() {
            return username;
        }

        public String getUsernameIp() {
            return usernameIp;
        }
    }

}
