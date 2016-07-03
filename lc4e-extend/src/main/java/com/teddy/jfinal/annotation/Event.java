package com.teddy.jfinal.annotation;

import java.lang.annotation.*;

/**
 * Created by teddyzhu on 16/7/3.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Event {

    //order
    int order() default Integer.MIN_VALUE;

    //event sync or not
    boolean async() default false;

    //wait until the event listener end when async true
    boolean asyncWait() default false;
}
