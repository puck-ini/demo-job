package org.zchzh.rbac.filter;

import org.zchzh.rbac.model.request.LoginContext;

/**
 * @author zengchzh
 * @date 2021/9/7
 */
public class TailFilter extends AbstractLoginFilter {

    @Override
    public void execute(LoginContext context, AbstractLoginFilter filter) {
        whenSuccess(context);
    }
}
