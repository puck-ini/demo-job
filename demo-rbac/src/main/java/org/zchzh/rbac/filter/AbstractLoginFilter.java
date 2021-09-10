package org.zchzh.rbac.filter;

import org.zchzh.rbac.model.request.LoginContext;

import java.util.Objects;

/**
 * @author zengchzh
 * @date 2021/9/7
 */
public abstract class AbstractLoginFilter implements LoginFilter {

    AbstractLoginFilter prev;

    AbstractLoginFilter next;

    @Override
    public void execute(LoginContext context, AbstractLoginFilter filter) {
        if (Objects.isNull(filter)) {
            return;
        }
        filter.execute(context, filter.next);
    }


    public void whenFail(LoginContext context) {
        context.publishFailEvent();
    }

    public void whenSuccess(LoginContext context) {
        context.publishSuccessEvent();
    }
}
