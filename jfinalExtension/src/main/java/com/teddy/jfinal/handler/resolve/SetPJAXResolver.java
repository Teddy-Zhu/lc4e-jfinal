package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.SetPJAX;
import com.teddy.jfinal.interfaces.AnnotationResolver;

import java.lang.annotation.Annotation;

/**
 * Created by teddy on 2015/8/22.
 */
public class SetPJAXResolver implements AnnotationResolver {
    private SetPJAX pjax;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        AttributeKit.setPJAX(pjax, invocation);
    }

    public SetPJAXResolver(SetPJAX pjax) {
        this.pjax = pjax;
    }
}
