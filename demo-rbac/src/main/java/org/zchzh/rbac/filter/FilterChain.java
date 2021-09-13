package org.zchzh.rbac.filter;

import org.zchzh.rbac.model.context.PipelineContext;

/**
 * @author zengchzh
 * @date 2021/9/7
 */
public class FilterChain<T extends PipelineContext> {

    public BaseFilter head;

    public BaseFilter tail;

    public FilterChain() {
        this.head = new HeadFilter();
        this.tail = new TailFilter();
        head.next = tail;
        tail.prev = head;
    }

    public FilterChain<T> addLast(BaseFilter<T> filter) {
        BaseFilter<T> temp = tail.prev;
        temp.next = filter;
        filter.prev = temp;
        tail.prev = filter;
        filter.next = tail;
        return this;
    }

    public FilterChain<T> addFirst(BaseFilter<T> filter) {
        BaseFilter<T> temp = head.next;
        temp.prev = filter;
        filter.next = temp;
        filter.prev = head;
        head.next = filter;
        return this;
    }

    public void clear() {
        head.next = tail;
        tail.prev = head;
    }

    public void check(T context) {
        head.execute(context, head.next);
    }


}
