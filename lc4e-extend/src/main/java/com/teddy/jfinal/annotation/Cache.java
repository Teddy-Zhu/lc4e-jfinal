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

    //cache name
    String cacheName() default Const.COMVAR;
    //cache dynamic key
    int index() default -1;

    // cache key
    String key() default Const.DEFAULT_NONE;

    // defaultValue if null
    String defaultValue() default Const.DEFAULT_NONE;
}
