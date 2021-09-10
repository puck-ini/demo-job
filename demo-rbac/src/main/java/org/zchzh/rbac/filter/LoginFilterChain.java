package org.zchzh.rbac.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zchzh.rbac.repository.UserRepo;
import org.zchzh.rbac.service.CacheService;

/**
 * @author zengchzh
 * @date 2021/9/7
 */

@Component
public class LoginFilterChain extends AbstractLoginChain {


    @Autowired
    public LoginFilterChain(UserRepo userRepo, CacheService cacheService) {
        super();
        addLast(new CheckUsernameCountFilter(cacheService));
        addLast(new CheckIpCountFilter(cacheService));
        addLast(new DefaultFilter(userRepo));
//        addFirst(new DefaultFilter(userRepo, cacheService));
//        addFirst(new CheckIpCountFilter(cacheService));
//        addFirst(new CheckUsernameCountFilter(cacheService));
    }
}
