package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.ValidateComVars;
import com.teddy.jfinal.interfaces.AnnotationResolver;

/**
 * Created by teddy on 2015/8/22.
 */
public class ValidateComVarsResolver implements AnnotationResolver {

    private ValidateComVars comVars;

    private ValidateKitI validateKit;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        validateKit.resolveComVars(comVars, invocation);
    }

    public ValidateComVarsResolver(ValidateComVars comVars, ValidateKitI validateKit) {
        this.comVars = comVars;
        this.validateKit = validateKit;
    }
}
