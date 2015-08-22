package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.interfaces.AnnotationResolver;
import org.apache.shiro.authz.annotation.RequiresGuest;

/**
 * Created by teddy on 2015/8/22.
 */
public class RequiresGuestResolver implements AnnotationResolver {

    private RequiresGuest requiresGuest;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        ValidateKit.resolveShiroGeust(requiresGuest);
    }

    public RequiresGuestResolver(RequiresGuest requiresGuest) {
        this.requiresGuest = requiresGuest;
    }
}
