package com.teddy.jfinal.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Method;

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
    String[] value();

    String[] views() default {};

}
