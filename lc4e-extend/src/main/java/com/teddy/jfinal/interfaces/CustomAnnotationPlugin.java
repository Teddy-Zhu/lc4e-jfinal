package com.teddy.jfinal.interfaces;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.plugin.CustomAnnotationResolve.AnnotationPluginResolver;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by teddyzhu on 15/12/12.
 */
public abstract class CustomAnnotationPlugin {

    public int getOrder() {
        return 0;
    }

    public abstract Class<? extends Annotation> getAnnotation();

    public abstract Object intercept(Annotation annotation, AnnotationPluginResolver resolver, Object[] objects, Object target, Method method, boolean[] isHandled) throws Throwable;

}