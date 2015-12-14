package com.teddy.jfinal.handler.resolve;

import com.jfinal.core.Controller;
import com.teddy.jfinal.annotation.CustomAnnotation;
import com.teddy.jfinal.annotation.ValidateComVar;
import com.teddy.jfinal.annotation.ValidateComVars;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import com.teddy.jfinal.plugin.CustomAnnotationResolve.AnnotationPluginResolver;
import com.teddy.jfinal.plugin.CustomPlugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by teddyzhu on 15/12/14.
 */
@CustomAnnotation
public class ValidateComVarsAnnotationResolver extends CustomAnnotationPlugin {
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return ValidateComVars.class;
    }

    @Override
    public Object intercept(Annotation annotation, AnnotationPluginResolver resolver, Object[] objects, Object target, Method method, boolean[] isHandled) throws Throwable {
        CustomPlugin.getValidateKit().resolveComVars((ValidateComVars) annotation, (Controller) target);

        return resolver.invoke();
    }
}
