package com.teddy.jfinal.handler;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.handler.support.GlobalInterceptorKit;
import com.teddy.jfinal.plugin.CustomPlugin;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by teddy on 2015/7/19.
 */
public class GlobalInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation ai) {
        try {
            GlobalInterceptorKit.handleAOPMethods(ai, Const.BEFORE_INTERCEPT);

            GlobalInterceptorKit.resolveAOPResolver(ai, CustomPlugin.getMethodAnnotationsHandler().get(ai.getActionKey()));

            GlobalInterceptorKit.handleInject(ai);

            ai.invoke();
            // set other attr
            GlobalInterceptorKit.resolveAOPResolver(ai, CustomPlugin.getAfterMethodAnnoHandler().get(ai.getActionKey()));

            GlobalInterceptorKit.handleAOPMethods(ai, Const.AFTER_INTERCEPT);
        } catch (Throwable e) {
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
