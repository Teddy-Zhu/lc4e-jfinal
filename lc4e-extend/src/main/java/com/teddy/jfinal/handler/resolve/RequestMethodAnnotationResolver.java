package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.teddy.jfinal.annotation.CustomAnnotation;
import com.teddy.jfinal.annotation.RequestMethod;
import com.teddy.jfinal.exceptions.Lc4eValidateException;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import com.teddy.jfinal.plugin.CustomAnnotationResolve.AnnotationPluginResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by teddyzhu on 15/12/14.
 */
@CustomAnnotation
public class RequestMethodAnnotationResolver extends CustomAnnotationPlugin {
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return RequestMethod.class;
    }

    @Override
    public Object intercept(Annotation annotation, AnnotationPluginResolver resolver, Object[] objects, Object target, Method method, boolean[] isHandled) throws Throwable {

        RequestMethod requestMethod = (RequestMethod) annotation;
        if (requestMethod != null && !requestMethod.value().toString().equals(((Controller) target).getRequest().getMethod().toUpperCase())) {
            // controller.renderError(404);
            throw new Lc4eValidateException("404");
        }

        return resolver.invoke();
    }
}
