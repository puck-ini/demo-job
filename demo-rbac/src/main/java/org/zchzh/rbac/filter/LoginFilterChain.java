package org.zchzh.rbac.filter;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zchzh.rbac.model.context.LoginContext;
import org.zchzh.rbac.model.context.PipelineContext;
import org.zchzh.rbac.repository.UserRepo;
import org.zchzh.rbac.service.CacheService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zengchzh
 * @date 2021/9/7
 */

@Component
public class LoginFilterChain /*extends FilterChain<LoginContext>*/ {

    private static final Map<Class<? extends PipelineContext>, FilterChain> MAP = new HashMap<>();

    @Autowired
    public LoginFilterChain(UserRepo userRepo, CacheService cacheService) {
//        super();
//        addLast(new CheckUsernameCountFilter(cacheService));
//        addLast(new CheckIpCountFilter(cacheService));
//        addLast(new CheckPwFilter(userRepo));
//        addFirst(new DefaultFilter(userRepo, cacheService));
//        addFirst(new CheckIpCountFilter(cacheService));
//        addFirst(new CheckUsernameCountFilter(cacheService));

        MAP.put(LoginContext.class,  new FilterChain<LoginContext>()
                .addLast(new CheckUsernameCountFilter(cacheService))
                .addLast(new CheckIpCountFilter(cacheService))
                .addLast(new CheckPwFilter(userRepo)));
        MAP.put(PipelineContext.class, new FilterChain<>());
    }

    public <T extends PipelineContext> void check(T t) {
        MAP.get(t.getClass()).check(t);
    }
}
