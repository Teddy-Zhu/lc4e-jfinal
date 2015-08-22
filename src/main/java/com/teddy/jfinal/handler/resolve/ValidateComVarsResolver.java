package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.ValidateComVars;
import com.teddy.jfinal.interfaces.AnnotationResolver;

/**
 * Created by teddy on 2015/8/22.
 */
public class ValidateComVarsResolver implements AnnotationResolver {

    private ValidateComVars comVars;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        ValidateKit.resolveComVars(comVars, invocation);
    }

    public ValidateComVarsResolver(ValidateComVars comVars) {
        this.comVars = comVars;
    }
}
