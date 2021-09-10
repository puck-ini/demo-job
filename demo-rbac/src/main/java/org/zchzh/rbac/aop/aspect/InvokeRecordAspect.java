package org.zchzh.rbac.aop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @author zengchzh
 * @date 2021/9/10
 */
@Aspect
@Order(1)
@Component
public class InvokeRecordAspect  extends BaseMethodAspect {

    @Override
    @Pointcut("@annotation(org.zchzh.rbac.annotation.InvokeRecord) || execution(public * org.zchzh.rbac.controller..*.*(..))")
    protected void pointcut() {
    }

    @Override
    protected Class<? extends MethodAdviceHandler<?>> getAdviceHandlerType() {
        return InvokeRecordHandler.class;
    }
}
