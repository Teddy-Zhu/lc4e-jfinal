package com.teddy.jfinal.handler;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.teddy.jfinal.handler.support.GlobalInterceptorKit;
import com.teddy.jfinal.plugin.CustomPlugin;

/**
 * Created by teddy on 2015/7/19.
 */
public class GlobalInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation ai) {
        try {

            Invocation invocation = CustomInterceptor.Proxy(ai, true);
            invocation.invoke();

        } catch (Throwable e) {
            try {
                GlobalInterceptorKit.ExceptionHandle(ai, e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
