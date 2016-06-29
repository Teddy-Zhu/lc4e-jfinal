package com.teddy.jfinal.annotation;

import com.teddy.jfinal.common.Const;

import java.lang.annotation.*;

/**
 * Created by teddyzhu on 16/6/29.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface API {
    String version() ;

    String value() default Const.DEFAULT_NONE;

}
