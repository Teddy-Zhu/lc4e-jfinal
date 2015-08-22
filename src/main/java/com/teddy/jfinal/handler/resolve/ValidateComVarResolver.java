package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.ValidateComVar;
import com.teddy.jfinal.interfaces.AnnotationResolver;

/**
 * Created by teddy on 2015/8/22.
 */
public class ValidateComVarResolver implements AnnotationResolver {

    private ValidateComVar comVar;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        ValidateKit.resolveComVar(comVar, invocation);
    }

    public ValidateComVarResolver(ValidateComVar comVar) {
        this.comVar = comVar;
    }
}
