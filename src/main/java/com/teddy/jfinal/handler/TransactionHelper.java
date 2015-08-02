package com.teddy.jfinal.handler;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.teddy.jfinal.common.Const;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by teddy on 2015/7/28.
 */
public class TransactionHelper implements MethodInterceptor {

    private Object target;

    public TransactionHelper() {
    }

    public TransactionHelper(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Interceptor interceptor = new Tx();
        Invocation invocation = new Invocation(o, method, objects, methodProxy, new Interceptor[]{interceptor});
        Field field = Invocation.class.getDeclaredField(Const.USE_INJECT_TARGET);
        field.setAccessible(true);
        field.set(invocation, false);
        invocation.invoke();
        return invocation.getReturnValue();
    }


    @SuppressWarnings("unchecked")
    public static <T> T Proxy(T target) {
        if (null == target) return null;
        Object proxy = null;

        Enhancer en = new Enhancer();
        en.setSuperclass(target.getClass());
        en.setCallback(new TransactionHelper(target));
        proxy = en.create();

        return (T) proxy;
    }

    @SuppressWarnings("unchecked")
    public static <T> T Proxy(Class<T> targetClass) {
        if (null == targetClass) return null;
        Object proxy = null;
        try {
            Enhancer en = new Enhancer();
            en.setSuperclass(targetClass.getClass());
            en.setCallback(new TransactionHelper(targetClass.newInstance()));
            proxy = en.create();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return (T) proxy;
    }
}
