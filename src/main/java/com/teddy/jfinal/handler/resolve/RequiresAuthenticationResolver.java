package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.interfaces.AnnotationResolver;
import org.apache.shiro.authz.annotation.RequiresAuthentication;

/**
 * Created by teddy on 2015/8/22.
 */
public class RequiresAuthenticationResolver implements AnnotationResolver {

    private RequiresAuthentication requiresAuthentication;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        ValidateKit.resolveShiroAuthentication(requiresAuthentication);
    }

    public RequiresAuthenticationResolver(RequiresAuthentication requiresAuthentication) {
        this.requiresAuthentication = requiresAuthentication;
    }
}
