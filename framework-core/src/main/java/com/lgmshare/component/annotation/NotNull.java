package com.lgmshare.component.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不能为空
 *
 * @author: lim
 * @version: V1.0
 * @description:
 * @datetime: 2015/9/1 15:01
 * @email: lgmshare@gmail.com
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface NotNull {
    String value() default "";
}
