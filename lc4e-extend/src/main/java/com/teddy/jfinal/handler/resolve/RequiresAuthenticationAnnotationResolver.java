package com.teddy.jfinal.handler.resolve;

import com.teddy.jfinal.annotation.CustomAnnotation;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import com.teddy.jfinal.plugin.annotationresolve.AnnotationPluginResolver;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by teddyzhu on 15/12/14.
 */
@CustomAnnotation
public class RequiresAuthenticationAnnotationResolver implements CustomAnnotationPlugin {
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return RequiresAuthentication.class;
    }

    @Override
    public Object intercept(Annotation annotation, AnnotationPluginResolver resolver, Object[] objects, Object target, Method method, boolean[] isHandled) throws Throwable {

        if (!SecurityUtils.getSubject().isAuthenticated()) {
            throw new UnauthenticatedException("The current Subject is not authenticated.  Access denied.");
        }
        return resolver.invoke();
    }
}
