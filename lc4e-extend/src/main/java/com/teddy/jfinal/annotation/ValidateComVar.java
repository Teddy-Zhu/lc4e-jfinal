package com.teddy.jfinal.annotation;

import com.teddy.jfinal.common.Const;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by teddy on 2015/6/23.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidateComVar {

    String name() default Const.DEFAULT_NONE;

    String value() default Const.DEFAULT_NONE;

    Class type() default Boolean.class;

    boolean validateEqual() default true;
}
