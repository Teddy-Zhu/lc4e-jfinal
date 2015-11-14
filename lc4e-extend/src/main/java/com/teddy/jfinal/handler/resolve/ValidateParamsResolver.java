package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.ValidateParams;
import com.teddy.jfinal.interfaces.AnnotationResolver;

/**
 * Created by teddy on 2015/8/22.
 */
public class ValidateParamsResolver implements AnnotationResolver {
    private ValidateParams params;

    private ValidateKitI validateKit;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        ValidateKit.resolveParameters(validateKit, params, invocation);
    }

    public ValidateParamsResolver(ValidateParams params, ValidateKitI validateKit) {
        this.params = params;
        this.validateKit = validateKit;
    }
}
