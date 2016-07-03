package com.teddy.jfinal.handler.resolve;

import com.jfinal.core.Controller;
import com.teddy.jfinal.annotation.CustomAnnotation;
import com.teddy.jfinal.annotation.ValidateToken;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import com.teddy.jfinal.plugin.annotationresolve.AnnotationPluginResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by teddyzhu on 15/12/14.
 */
@CustomAnnotation
public class ValidateTokenAnnotationResolver implements CustomAnnotationPlugin {
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return ValidateToken.class;
    }

    @Override
    public Object intercept(Annotation annotation, AnnotationPluginResolver resolver, Object[] objects, Object target, Method method, boolean[] isHandled) throws Throwable {
        ValidateKit.resolveToken((ValidateToken) annotation, (Controller) target);
        return resolver.invoke();
    }
}
