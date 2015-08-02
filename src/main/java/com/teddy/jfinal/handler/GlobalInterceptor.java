package com.teddy.jfinal.handler;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.handler.support.GlobalInterceptorKit;

/**
 * Created by teddy on 2015/7/19.
 */
public class GlobalInterceptor implements Interceptor {

    /**
     * include beforeIntercept afterIntercept beforeException afterException
     */

    @Override
    public void intercept(Invocation ai) {

        try {
            GlobalInterceptorKit.handleAOPMethods(ai, Const.BEFORE_INTERCEPT);

            GlobalInterceptorKit.handleAnnotationsOnControllerMethod(ai);

            GlobalInterceptorKit.handleInject(ai);

            ai.invoke();
            GlobalInterceptorKit.handleAOPMethods(ai, Const.AFTER_INTERCEPT);
        } catch (Exception e) {
            try {
                GlobalInterceptorKit.handleAOPMethods(ai, Const.BEFORE_EXCEPTION);
                GlobalInterceptorKit.ExceptionHandle(ai, e);
                GlobalInterceptorKit.handleAOPMethods(ai, Const.AFTER_EXCEPTION);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

}
