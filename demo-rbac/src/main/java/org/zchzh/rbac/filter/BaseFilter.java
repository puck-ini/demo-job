package org.zchzh.rbac.filter;

import org.zchzh.rbac.model.context.PipelineContext;

import java.util.Objects;

/**
 * @author zengchzh
 * @date 2021/9/7
 */
public abstract class BaseFilter<T extends PipelineContext> {

    BaseFilter<T> prev;

    BaseFilter<T> next;

    /**
     * 执行过滤
     * @param context 上下文信息
     * @param filter 过滤器
     */
    public void execute(T context, BaseFilter<T> filter) {
        if (Objects.isNull(filter)) {
            return;
        }
        filter.execute(context, filter.next);
    }

    public void onSuccess(T context) {
        context.publishSuccessEvent();
    }

    public void onFail(T context) {
        context.publishFailEvent();
    }

}
