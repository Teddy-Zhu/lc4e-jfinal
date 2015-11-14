package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.RequestHeader;
import com.teddy.jfinal.interfaces.AnnotationResolver;

/**
 * Created by teddy on 2015/8/22.
 */
public class RequestHeaderResolver implements AnnotationResolver {

    private RequestHeader header;

    @Override
    public void resolve(Invocation invocation) throws Exception {
        ValidateKit.resolveRequestHeader(header, invocation);
    }

    public RequestHeaderResolver(RequestHeader header) {
        this.header = header;
    }
}
