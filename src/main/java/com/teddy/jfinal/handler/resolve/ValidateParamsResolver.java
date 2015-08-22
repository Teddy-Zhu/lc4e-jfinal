package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.ValidateParams;
import com.teddy.jfinal.interfaces.AnnotationResolver;

/**
 * Created by teddy on 2015/8/22.
 */
public class ValidateParamsResolver implements AnnotationResolver {
    private ValidateParams params;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        ValidateKit.resolveParameters(params, invocation);
    }

    public ValidateParamsResolver(ValidateParams params) {
        this.params = params;
    }
}
