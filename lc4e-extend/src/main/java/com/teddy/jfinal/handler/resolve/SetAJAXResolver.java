package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.SetAJAX;
import com.teddy.jfinal.interfaces.AnnotationResolver;

/**
 * Created by teddy on 2015/8/22.
 */
public class SetAJAXResolver implements AnnotationResolver {

    private SetAJAX ajax;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        AttributeKit.setAJAX(ajax, invocation);
    }

    public SetAJAXResolver(SetAJAX ajax) {
        this.ajax = ajax;
    }
}
