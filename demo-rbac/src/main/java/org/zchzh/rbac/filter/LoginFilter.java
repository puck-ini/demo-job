package org.zchzh.rbac.filter;

import org.zchzh.rbac.model.request.LoginContext;

/**
 * @author zengchzh
 * @date 2021/9/7
 */
public interface LoginFilter {


    /**
     * 执行过滤
     * @param context 登录信息上下文
     * @param filter 链式
     */
    void execute(LoginContext context, AbstractLoginFilter filter);
}
