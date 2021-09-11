package org.zchzh.rbac.filter;

import org.zchzh.rbac.model.request.LoginContext;
import org.zchzh.rbac.model.request.PipelineContext;

/**
 * @author zengchzh
 * @date 2021/9/7
 */
public abstract class BaseChain<T extends PipelineContext> {

    public BaseFilter head;

    public BaseFilter tail;

    public BaseChain() {
        this.head = new HeadFilter();
        this.tail = new TailFilter();
        head.next = tail;
        tail.prev = head;
    }

    public void addLast(BaseFilter<T> filter) {
        BaseFilter<T> temp = tail.prev;
        temp.next = filter;
        filter.prev = temp;
        tail.prev = filter;
        filter.next = tail;
    }

    public void addFirst(BaseFilter<T> filter) {
        BaseFilter<T> temp = head.next;
        temp.prev = filter;
        filter.next = temp;
        filter.prev = head;
        head.next = filter;
    }

    public void clear() {
        head.next = tail;
        tail.prev = head;
    }

    public void check(T context) {
        head.execute(context, head.next);
    }


}
