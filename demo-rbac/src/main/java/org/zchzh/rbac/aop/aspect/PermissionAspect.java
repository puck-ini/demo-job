package org.zchzh.rbac.aop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author zengchzh
 * @date 2021/9/13
 */

@Aspect
@Component
public class PermissionAspect extends BaseMethodAspect {


    @Pointcut("execution(public * org.zchzh.rbac.controller..*.*(..))")
    @Override
    protected void pointcut() {
    }

    @Override
    protected Class<? extends MethodAdviceHandler<?>> getAdviceHandlerType() {
        return PermissionHandler.class;
    }
}
