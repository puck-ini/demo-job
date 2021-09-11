package org.zchzh.rbac.filter;

import lombok.extern.slf4j.Slf4j;
import org.zchzh.rbac.model.request.PipelineContext;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zengchzh
 * @date 2021/9/7
 */
@Slf4j
public class TailFilter extends BaseFilter<PipelineContext> {

    @Override
    public void execute(PipelineContext context, BaseFilter<PipelineContext> filter) {
        context.setEndTime(LocalDateTime.now());
        onSuccess(context);
        log.info("filter end : " + context + " : " + context.getEndTime());
    }
}
