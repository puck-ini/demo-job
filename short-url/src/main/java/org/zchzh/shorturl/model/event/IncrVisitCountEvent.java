package org.zchzh.shorturl.model.event;

import java.util.EventObject;

/**
 * @author zengchzh
 * @date 2022/1/19
 */
public class IncrVisitCountEvent extends EventObject {

    private static final long serialVersionUID = -7238403054861087688L;

    private final Long id;
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public IncrVisitCountEvent(Object source, Long id) {
        super(source);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
