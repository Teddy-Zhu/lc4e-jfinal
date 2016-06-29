package com.teddy.jfinal.handler.resolve;

import com.jfinal.core.Controller;
import com.teddy.jfinal.annotation.CustomAnnotation;
import com.teddy.jfinal.annotation.SetAJAX;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import com.teddy.jfinal.plugin.annotationresolver.AnnotationPluginResolver;
import com.teddy.jfinal.tools.WebTool;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by teddyzhu on 15/12/14.
 */
@CustomAnnotation
public class SetAJAXAnnotationResolver implements CustomAnnotationPlugin {
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return SetAJAX.class;
    }

    @Override
    public Object intercept(Annotation annotation, AnnotationPluginResolver resolver, Object[] objects, Object target, Method method, boolean[] isHandled) throws Throwable {
        Object returnValue;

        returnValue = resolver.invoke();

        Controller controller = (Controller) target;
        controller.setAttr(((SetAJAX) annotation).value(), WebTool.isAJAX(controller.getRequest()));

        return returnValue;
    }
}
