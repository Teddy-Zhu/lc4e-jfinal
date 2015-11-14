package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.RequestMethod;
import com.teddy.jfinal.exceptions.Lc4eValidateException;
import com.teddy.jfinal.interfaces.AnnotationResolver;

/**
 * Created by teddy on 2015/8/22.
 */
public class RequestMethodResolver implements AnnotationResolver {

    private RequestMethod requestMethod;

    @Override
    public void resolve(Invocation invocation) throws Lc4eValidateException {
        ValidateKit.resolveRequestMethod(requestMethod, invocation);
    }

    public RequestMethodResolver(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }
}
