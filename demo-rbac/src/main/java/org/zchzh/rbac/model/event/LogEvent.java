package org.zchzh.rbac.model.event;

import org.springframework.context.ApplicationEvent;
import org.zchzh.rbac.model.entity.LogEntity;

/**
 * @author zengchzh
 * @date 2021/6/8
 */

public class LogEvent extends ApplicationEvent {

    private LogEntity logEntity;

    public LogEvent(Object source, LogEntity logEntity) {
        super(source);
        this.logEntity = logEntity;
    }

    public LogEntity getLogEntity() {
        return logEntity;
    }

    public void setLogEntity(LogEntity logEntity) {
        this.logEntity = logEntity;
    }
}
