package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.interfaces.AnnotationResolver;
import org.apache.shiro.authz.annotation.RequiresRoles;

import java.lang.annotation.Annotation;

/**
 * Created by teddy on 2015/8/22.
 */
public class RequiresRolesResolver implements AnnotationResolver {

    private RequiresRoles requiresRoles;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        ValidateKit.resolveShiroRole(requiresRoles);
    }

    public RequiresRolesResolver(RequiresRoles requiresRoles) {
        this.requiresRoles = requiresRoles;
    }
}
