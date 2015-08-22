package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.interfaces.AnnotationResolver;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * Created by teddy on 2015/8/22.
 */
public class RequiresPermissionsResovler implements AnnotationResolver {
    private RequiresPermissions permissions;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        ValidateKit.resolveShiroPermission(permissions);
    }

    public RequiresPermissionsResovler(RequiresPermissions permissions) {
        this.permissions = permissions;
    }
}
