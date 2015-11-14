package com.teddy.jfinal.annotation;

import com.teddy.jfinal.common.Const;

import java.lang.annotation.*;

/**
 * Created by teddy on 2015/8/12.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Job {

    String name() default Const.DEFAULT_NONE;

    String corn();
}
