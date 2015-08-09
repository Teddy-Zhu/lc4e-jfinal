package com.teddy.jfinal.annotation;

import com.teddy.jfinal.common.Const;

import java.lang.annotation.*;

/**
 * Created by teddy on 2015/7/18.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Controller {

    /**
     * url paths
     *
     * @return
     */
    String value();

    String views() default Const.DEFAULT_NONE;

}
