package com.teddy.jfinal.annotation;

import com.teddy.jfinal.common.Const;

import java.lang.annotation.*;

/**
 * Created by teddy on 2015/7/24.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Cache {

    String cacheName() default Const.COMVAR;

    int index() default -1;

    String key() default Const.DEFAULT_NONE;

    String defaultValue() default Const.DEFAULT_NONE;
}
