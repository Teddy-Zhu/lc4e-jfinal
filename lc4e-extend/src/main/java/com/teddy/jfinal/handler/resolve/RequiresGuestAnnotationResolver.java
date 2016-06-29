package com.teddy.jfinal.handler.resolve;

import com.teddy.jfinal.annotation.CustomAnnotation;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import com.teddy.jfinal.plugin.annotationresolver.AnnotationPluginResolver;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.RequiresGuest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by teddyzhu on 15/12/14.
 */
@CustomAnnotation
public class RequiresGuestAnnotationResolver implements CustomAnnotationPlugin {
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return RequiresGuest.class;
    }

    @Override
    public Object intercept(Annotation annotation, AnnotationPluginResolver resolver, Object[] objects, Object target, Method method, boolean[] isHandled) throws Throwable {
        if (SecurityUtils.getSubject().getPrincipal() != null) {
            throw new UnauthenticatedException("Attempting to perform a guest-only operation.  The current Subject is " +
                    "not a guest (they have been authenticated or remembered from a previous login).  Access " +
                    "denied.");
        }
        return resolver.invoke();
    }
}
