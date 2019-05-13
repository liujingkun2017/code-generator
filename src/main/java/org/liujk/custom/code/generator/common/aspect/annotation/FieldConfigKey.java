package org.liujk.custom.code.generator.common.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类描述:  字段配置key
 * 根据key值，可以唯一标识具体实体的具体字段
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldConfigKey {

    /**
     * 字段配置key值
     *
     * @return
     */
    String key() default "";
}
