package org.zchzh.storage.annotation;

import org.springframework.context.annotation.Conditional;
import org.zchzh.storage.condition.StorageTypeCondition;
import org.zchzh.storage.type.StorageType;

import java.lang.annotation.*;

/**
 * @author zengchzh
 * @date 2022/1/11
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(StorageTypeCondition.class)
public @interface ConditionOnStorageType {
    StorageType value();
}
