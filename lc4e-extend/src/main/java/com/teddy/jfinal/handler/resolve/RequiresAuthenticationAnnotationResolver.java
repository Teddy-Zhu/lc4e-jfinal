package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.CustomAnnotation;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import com.teddy.jfinal.plugin.CustomAnnotationResolve.AnnotationPluginResolver;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by teddyzhu on 15/12/14.
 */
@CustomAnnotation
public class RequiresAuthenticationAnnotationResolver extends CustomAnnotationPlugin {
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return null;
    }

    @Override
    public Object intercept(Annotation annotation, AnnotationPluginResolver resolver, Object[] objects, Object target, Method method, boolean[] isHandled) throws Throwable {

        if (!SecurityUtils.getSubject().isAuthenticated()) {
            throw new UnauthenticatedException("The current Subject is not authenticated.  Access denied.");
        }
        return resolver.invoke();
    }
}
