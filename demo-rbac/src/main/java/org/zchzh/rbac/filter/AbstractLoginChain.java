package org.zchzh.rbac.filter;

import org.zchzh.rbac.model.request.LoginContext;

/**
 * @author zengchzh
 * @date 2021/9/7
 */
public abstract class AbstractLoginChain implements LoginFilter {

    public final AbstractLoginFilter head;

    public final AbstractLoginFilter tail;

    public AbstractLoginChain() {
        this.head = new HeadFilter();
        this.tail = new TailFilter();
        head.next = tail;
        tail.prev = head;
    }


    public void addLast(AbstractLoginFilter filter) {
        AbstractLoginFilter temp = tail.prev;
        temp.next = filter;
        filter.prev = temp;
        tail.prev = filter;
        filter.next = tail;
    }

    public void addFirst(AbstractLoginFilter filter) {
        AbstractLoginFilter temp = head.next;
        temp.prev = filter;
        filter.next = temp;
        filter.prev = head;
        head.next = filter;
    }

    public void clear() {
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public void execute(LoginContext context, AbstractLoginFilter filter) {
        filter.execute(context, filter.next);
    }


    public void check(LoginContext loginContext) {
        this.execute(loginContext, head);
    }


}
