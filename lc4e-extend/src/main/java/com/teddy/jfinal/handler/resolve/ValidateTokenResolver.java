package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.ValidateToken;
import com.teddy.jfinal.interfaces.AnnotationResolver;

import java.lang.annotation.Annotation;

/**
 * Created by teddy on 2015/8/22.
 */
public class ValidateTokenResolver implements AnnotationResolver {

    private ValidateToken token;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        ValidateKit.resolveToken(token, invocation);
    }

    public ValidateTokenResolver(ValidateToken token) {
        this.token = token;
    }
}
