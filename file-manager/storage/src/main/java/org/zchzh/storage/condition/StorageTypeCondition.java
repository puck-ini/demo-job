package org.zchzh.storage.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.zchzh.storage.annotation.ConditionOnStorageType;
import org.zchzh.storage.type.StorageType;

import java.util.Objects;

/**
 * @author zengchzh
 * @date 2022/1/11
 */
public class StorageTypeCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String typeStr = context.getEnvironment().getProperty("file.storage.type", StorageType.DEFAULT.toString()).toUpperCase();
        StorageType configType = StorageType.valueOf(typeStr);
        StorageType beanType = (StorageType) metadata.getAnnotationAttributes(ConditionOnStorageType.class.getName()).get("value");
        return Objects.equals(configType, beanType);
    }
}
