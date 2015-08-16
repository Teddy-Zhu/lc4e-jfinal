package com.teddy.jfinal.annotation;

import com.teddy.jfinal.common.Const;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by teddy on 2015/6/14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SetComVar {

    String value();

    String attrName() default Const.DEFAULT_NONE;

    Class type() default String.class;

}

