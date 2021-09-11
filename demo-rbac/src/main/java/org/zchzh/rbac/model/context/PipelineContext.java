package org.zchzh.rbac.model.context;

import lombok.Data;
import lombok.ToString;
import org.springframework.context.ApplicationContext;
import org.zchzh.rbac.exception.CommonException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.EventObject;
import java.util.Objects;

/**
 * @author zengchzh
 * @date 2021/9/11
 */
@Data
@ToString
public class PipelineContext {

    /**
     * 处理开始时间
     */
    private LocalDateTime startTime;

    /**
     * 处理结束时间
     */
    private LocalDateTime endTime;

    protected ApplicationContext context;

    protected EventObject successEvent;

    protected EventObject failEvent;

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public void publishSuccessEvent() {
        if (Objects.isNull(context)) {
            throw new CommonException("context未初始无法发布事件");
        }
        context.publishEvent(successEvent);
    }

    public void publishFailEvent() {
        if (Objects.isNull(context)) {
            throw new CommonException("context未初始无法发布事件");
        }
        context.publishEvent(failEvent);
    }

}
