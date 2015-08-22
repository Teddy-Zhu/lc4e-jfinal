package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.SetComVars;
import com.teddy.jfinal.interfaces.AnnotationResolver;

/**
 * Created by teddy on 2015/8/22.
 */
public class SetComVarsResolver implements AnnotationResolver {

    private SetComVars comVars;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        AttributeKit.setComVars(comVars, invocation);
    }

    public SetComVarsResolver(SetComVars comVars) {
        this.comVars = comVars;
    }
}
