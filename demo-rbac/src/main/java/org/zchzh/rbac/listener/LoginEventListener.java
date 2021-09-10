package org.zchzh.rbac.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.zchzh.rbac.model.event.LoginFailEvent;
import org.zchzh.rbac.model.event.LoginSuccessEvent;
import org.zchzh.rbac.service.CacheService;

/**
 * @author zengchzh
 * @date 2021/9/10
 *
 */
@Slf4j
@Component
public class LoginEventListener {

    @Autowired
    private CacheService cacheService;

    @EventListener(LoginFailEvent.class)
    public void incrLimitCount(LoginFailEvent event) {
        log.info("login fail ----------------" + event.getUsername() + "-----------" + event.getReqIp());
        cacheService.incr(event.getKeys().getUsername());
        cacheService.incr(event.getKeys().getUsernameIp());
    }


    @EventListener(LoginSuccessEvent.class)
    public void testSuccess(LoginSuccessEvent event) {
        log.info("login success ----------------" + event.getUsername() + "-----------" + event.getReqIp());
    }
}
