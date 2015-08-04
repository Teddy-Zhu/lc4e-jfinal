package com.teddy.jfinal.handler;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.handler.support.GlobalInterceptorKit;
import com.teddy.jfinal.tools.ReflectTool;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by teddy on 2015/7/19.
 */
public class GlobalInterceptor implements Interceptor {

    /**
     * include beforeIntercept afterIntercept beforeException afterException
     */
    private Map<Class<? extends Annotation>, Annotation> ans;

    @Override
    public void intercept(Invocation ai) {

        try {
            GlobalInterceptorKit.handleAOPMethods(ai, Const.BEFORE_INTERCEPT);
            ans = ReflectTool.getAnnotationsMap(ai.getMethod());
            GlobalInterceptorKit.handleAnnotationsOnControllerMethod(ai, ans);

            GlobalInterceptorKit.handleInject(ai);

            ai.invoke();
            // set other attr
            GlobalInterceptorKit.handleOtherAnnotataion(ai, ans);

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
