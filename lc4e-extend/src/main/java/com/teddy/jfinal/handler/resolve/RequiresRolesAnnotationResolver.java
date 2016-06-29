package com.teddy.jfinal.handler.resolve;

import com.teddy.jfinal.annotation.CustomAnnotation;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import com.teddy.jfinal.plugin.annotationresolver.AnnotationPluginResolver;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by teddyzhu on 15/12/14.
 */
@CustomAnnotation
public class RequiresRolesAnnotationResolver implements CustomAnnotationPlugin {
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return RequiresRoles.class;
    }

    @Override
    public Object intercept(Annotation annotation, AnnotationPluginResolver resolver, Object[] objects, Object target, Method method, boolean[] isHandled) throws Throwable {
        RequiresRoles requiresRoles = (RequiresRoles) annotation;
        String[] roles = requiresRoles.value();
        Subject subject = SecurityUtils.getSubject();
        if (roles.length == 1) {
            subject.checkRole(roles[0]);
            return resolver.invoke();
        }
        if (Logical.AND.equals(requiresRoles.logical())) {
            subject.checkRoles(Arrays.asList(roles));
            return resolver.invoke();
        }
        if (Logical.OR.equals(requiresRoles.logical())) {
            // Avoid processing exceptions unnecessarily - "delay" throwing the exception by calling hasRole first
            boolean hasAtLeastOneRole = false;
            for (String role : roles) if (subject.hasRole(role)) hasAtLeastOneRole = true;
            // Cause the exception if none of the role match, note that the exception message will be a bit misleading
            if (!hasAtLeastOneRole) subject.checkRole(roles[0]);
        }
        return resolver.invoke();
    }
}
