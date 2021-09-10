package org.zchzh.rbac.filter;

import lombok.extern.slf4j.Slf4j;
import org.zchzh.rbac.exception.CommonException;
import org.zchzh.rbac.model.request.LoginContext;
import org.zchzh.rbac.service.CacheService;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author zengchzh
 * @date 2021/9/7
 * 计数
 */
@Slf4j
public class CheckUsernameCountFilter extends AbstractLoginFilter {

    private CacheService cacheService;

    /**
     * 登录名限制次数
     */
    private static final int LOGIN_NAME_FAIL_COUNT = 20;

    public CheckUsernameCountFilter(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void execute(LoginContext context, AbstractLoginFilter filter) {
        log.info(CheckUsernameCountFilter.class.getName());
        String key = context.getKeys().getUsername();
        Integer count = (Integer) Optional.ofNullable(cacheService.get(key)).orElseGet(new Supplier<Integer>() {
            @Override
            public Integer get() {
                cacheService.set(key, 0);
                return 0;
            }
        });
        if (count > LOGIN_NAME_FAIL_COUNT) {
            whenFail(context);
        }
        super.execute(context, filter);
    }

    @Override
    public void whenFail(LoginContext context) {
        throw new CommonException("登录限制");
    }
}
