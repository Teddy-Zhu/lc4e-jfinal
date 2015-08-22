package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.interfaces.AnnotationResolver;
import org.apache.shiro.authz.annotation.RequiresUser;

/**
 * Created by teddy on 2015/8/22.
 */
public class RequiresUserResolver implements AnnotationResolver {


    private RequiresUser requiresUser;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        ValidateKit.resolveShiroUser(requiresUser);
    }

    public RequiresUserResolver(RequiresUser requiresUser) {
        this.requiresUser = requiresUser;
    }
}
