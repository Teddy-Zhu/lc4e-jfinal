package com.teddy.jfinal.handler.resolve;

import com.teddy.jfinal.annotation.Controller;
import com.teddy.jfinal.annotation.CustomAnnotation;
import com.teddy.jfinal.annotation.ResponseStatus;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import com.teddy.jfinal.plugin.CustomAnnotationResolve.AnnotationPluginResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by teddyzhu on 15/12/14.
 */
@CustomAnnotation
public class ResponseStatusAnnotationResolver extends CustomAnnotationPlugin {
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return ResponseStatus.class;
    }

    @Override
    public Object intercept(Annotation annotation, AnnotationPluginResolver resolver, Object[] objects, Object target, Method method, boolean[] isHandled) throws Throwable {
        Object returnValue;

        returnValue = resolver.invoke();

        ((com.jfinal.core.Controller) target).getResponse().setStatus(((ResponseStatus) annotation).value().toInteger());

        return returnValue;
    }
}
