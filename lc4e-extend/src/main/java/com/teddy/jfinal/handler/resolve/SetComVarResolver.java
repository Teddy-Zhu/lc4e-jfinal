package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.SetComVar;
import com.teddy.jfinal.interfaces.AnnotationResolver;

/**
 * Created by teddy on 2015/8/22.
 */
public class SetComVarResolver implements AnnotationResolver {

    private SetComVar comVar;

    private AttributeKitI attributeKit;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        attributeKit.setComVar(comVar, invocation);
    }

    public SetComVarResolver(SetComVar comVar, AttributeKitI attributeKit) {
        this.comVar = comVar;
        this.attributeKit = attributeKit;
    }
}
