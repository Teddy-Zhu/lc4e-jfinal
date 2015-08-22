package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.SetUIData;
import com.teddy.jfinal.interfaces.AnnotationResolver;

import java.lang.annotation.Annotation;

/**
 * Created by teddy on 2015/8/22.
 */
public class SetUIDataResolver implements AnnotationResolver {

    private SetUIData uiData;


    @Override
    public void resolve(Invocation invocation) throws Exception {
        AttributeKit.setUIData(uiData, invocation);
    }

    public SetUIDataResolver(SetUIData uiData) {
        this.uiData = uiData;
    }
}
