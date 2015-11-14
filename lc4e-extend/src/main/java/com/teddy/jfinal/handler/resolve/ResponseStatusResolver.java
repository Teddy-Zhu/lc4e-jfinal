package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.ResponseStatus;
import com.teddy.jfinal.interfaces.AnnotationResolver;

/**
 * Created by teddy on 2015/8/22.
 */
public class ResponseStatusResolver implements AnnotationResolver {
    private ResponseStatus status;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        ValidateKit.resolveResponseStatus(status, invocation);
    }

    public ResponseStatusResolver(ResponseStatus status) {
        this.status = status;
    }
}
