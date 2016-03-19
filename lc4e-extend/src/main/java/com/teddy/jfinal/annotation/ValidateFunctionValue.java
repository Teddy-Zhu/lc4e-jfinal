package com.teddy.jfinal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by teddyzhu on 16/1/29.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidateFunctionValue {

    ValidateComVar value();

    Class targetClass();

    String methodName();

    boolean isDeclared() default true;

    String errorInfo() default "validate value is not equal the given one";
}
