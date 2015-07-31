package com.teddy.jfinal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by teddy on 2015/5/21.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SetUIData {

    String functionName() default "";

    String attributeName() default "";

    int[] useVarIndex() default {};

    String cacheName() default "data";

    String key() default "";

}
