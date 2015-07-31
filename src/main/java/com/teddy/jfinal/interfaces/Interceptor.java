package com.teddy.jfinal.interfaces;

import com.jfinal.aop.Invocation;

/**
 * Created by teddy on 2015/7/20.
 */
public interface Interceptor {

    /**
     * before all controller
     *
     * @param ai
     */
    void beforeIntercept(Invocation ai);

    /**
     * after all controller
     *
     * @param ai
     */
    void afterIntercept(Invocation ai);

    /***
     * before exception handler
     *
     * @param ai
     * @param e
     */
    void beforeException(Invocation ai, Exception e);

    /**
     * before exception handler
     *
     * @param ai
     * @param e
     */
    void afterException(Invocation ai, Exception e);
}