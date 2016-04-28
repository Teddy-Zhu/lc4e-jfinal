package com.teddy.jfinal.interfaces;

import com.teddy.jfinal.plugin.CustomAnnotationResolve.AnnotationPluginResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by teddyzhu on 15/12/12.
 */
public interface CustomAnnotationPlugin {

    default int getOrder() {
        return 1000;
    }

    Class<? extends Annotation> getAnnotation();

    Object intercept(Annotation annotation, AnnotationPluginResolver resolver, Object[] objects, Object target, Method method, boolean[] isHandled) throws Throwable;
}