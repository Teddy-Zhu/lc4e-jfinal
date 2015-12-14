package com.teddy.jfinal.handler.resolve;

import com.teddy.jfinal.annotation.CustomAnnotation;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import com.teddy.jfinal.plugin.CustomAnnotationResolve.AnnotationPluginResolver;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by teddyzhu on 15/12/14.
 */
@CustomAnnotation
public class RequiresPermissionsAnnotationResolver extends CustomAnnotationPlugin {
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return RequiresPermissions.class;
    }

    @Override
    public Object intercept(Annotation annotation, AnnotationPluginResolver resolver, Object[] objects, Object target, Method method, boolean[] isHandled) throws Throwable {
        RequiresPermissions permissions = (RequiresPermissions) annotation;
        String[] perms = permissions.value();
        Subject subject = SecurityUtils.getSubject();

        if (perms.length == 1) {
            subject.checkPermission(perms[0]);
            return resolver.invoke();
        }
        if (Logical.AND.equals(permissions.logical())) {
            subject.checkPermissions(perms);
            return resolver.invoke();
        }
        if (Logical.OR.equals(permissions.logical())) {
            // Avoid processing exceptions unnecessarily - "delay" throwing the
            // exception by calling hasRole first
            boolean hasAtLeastOnePermission = false;
            for (String permission : perms)
                if (subject.isPermitted(permission))
                    hasAtLeastOnePermission = true;
            // Cause the exception if none of the role match, note that the
            // exception message will be a bit misleading
            if (!hasAtLeastOnePermission)
                subject.checkPermission(perms[0]);
        }
        return resolver.invoke();
    }
}
