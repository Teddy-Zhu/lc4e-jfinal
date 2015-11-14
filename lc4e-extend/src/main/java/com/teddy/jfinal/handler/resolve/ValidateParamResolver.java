package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.ValidateParam;
import com.teddy.jfinal.interfaces.AnnotationResolver;

/**
 * Created by teddy on 2015/8/22.
 */
public class ValidateParamResolver implements AnnotationResolver {
    private ValidateParam param;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        ValidateKit.resolveParameter(param, invocation);
    }

    public ValidateParamResolver(ValidateParam param) {
        this.param = param;
    }
}
