package com.teddy.jfinal.interfaces;

import com.jfinal.aop.Invocation;

/**
 * Created by teddy on 2015/8/22.
 */
public interface AnnotationResolver {

    void resolve(Invocation invocation) throws Exception;
}
