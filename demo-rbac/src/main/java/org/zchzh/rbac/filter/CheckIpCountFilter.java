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
 */
@Slf4j
public class CheckIpCountFilter extends AbstractLoginFilter {

    private CacheService cacheService;


    /**
     * ip 限制次数
     */
    private static final int IP_FAIL_COUNT = 10;

    public CheckIpCountFilter(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void execute(LoginContext context, AbstractLoginFilter filter) {
        log.info(CheckIpCountFilter.class.getName());
        String key = context.getKeys().getUsernameIp();
        Integer count = (Integer) Optional.ofNullable(cacheService.get(key)).orElseGet(new Supplier<Integer>() {
            @Override
            public Integer get() {
                cacheService.set(key, 0);
                return 0;
            }
        });
        if (count > IP_FAIL_COUNT) {
            whenFail(context);
        }
        super.execute(context, filter);
    }

    @Override
    public void whenFail(LoginContext context) {
        throw new CommonException("ip登录限制");
    }
}
