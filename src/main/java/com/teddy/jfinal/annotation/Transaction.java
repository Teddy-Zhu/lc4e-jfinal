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
public @interface Transaction {
    String value() default Const.DEFAULT_NONE;
}
