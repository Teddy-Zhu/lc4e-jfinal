package com.teddy.jfinal.handler.resolve;

import com.jfinal.core.Controller;
import com.teddy.jfinal.annotation.CustomAnnotation;
import com.teddy.jfinal.annotation.ValidateComVar;
import com.teddy.jfinal.config.Config;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import com.teddy.jfinal.plugin.annotationresolve.AnnotationPluginResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by teddyzhu on 15/12/14.
 */
@CustomAnnotation
public class ValidateComVarAnnotationResolver implements CustomAnnotationPlugin {
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return ValidateComVar.class;
    }

    @Override
    public Object intercept(Annotation annotation, AnnotationPluginResolver resolver, Object[] objects, Object target, Method method, boolean[] isHandled) throws Throwable {

        Config.getCustomConfig().getValidateKit().resolveComVar((ValidateComVar) annotation, (Controller) target);

        return resolver.invoke();
    }
}
