package org.zchzh.rbac.filter;

import lombok.extern.slf4j.Slf4j;
import org.zchzh.rbac.model.request.LoginContext;
import org.zchzh.rbac.model.request.PipelineContext;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zengchzh
 * @date 2021/9/7
 */
@Slf4j
public class HeadFilter extends BaseFilter<PipelineContext> {



    @Override
    public void execute(PipelineContext context, BaseFilter<PipelineContext> filter) {
        context.setStartTime(LocalDateTime.now());
        log.info("filter start : " + context + " : " + context.getStartTime());
        super.execute(context, filter);
    }
}
