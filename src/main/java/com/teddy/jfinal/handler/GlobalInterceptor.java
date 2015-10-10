package com.teddy.jfinal.handler;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.teddy.jfinal.handler.support.GlobalInterceptorKit;
import com.teddy.jfinal.plugin.CustomPlugin;

/**
 * Created by teddy on 2015/7/19.
 */
public class GlobalInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation ai) {
        try {
            GlobalInterceptorKit.resolveBeforeInterceptor(ai);

            if (!GlobalInterceptorKit.resolveBeforeLc4ePlugin(ai, CustomPlugin.getPluginAOPHandler().get(ai.getActionKey()))) {
                return;
            }

            GlobalInterceptorKit.handleInject(ai);

            ai.invoke();

            // set other attr
            GlobalInterceptorKit.resolveAfterLc4ePlugin(ai, CustomPlugin.getPluginAOPHandler().get(ai.getActionKey()));

            GlobalInterceptorKit.resolveAfterInterceptor(ai);
        } catch (Throwable e) {
            try {
                GlobalInterceptorKit.resolveBeforeException(ai, e);
                GlobalInterceptorKit.ExceptionHandle(ai, e);
                GlobalInterceptorKit.resolveAfterException(ai, e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
