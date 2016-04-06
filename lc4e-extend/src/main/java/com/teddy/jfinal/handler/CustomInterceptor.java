package com.teddy.jfinal.handler;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.handler.support.GlobalInterceptorKit;
import com.teddy.jfinal.plugin.CustomAnnotationResolve.AnnotationPluginResolver;
import com.teddy.jfinal.plugin.CustomPlugin;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by teddy on 2015/7/25.
 */
public class CustomInterceptor implements MethodInterceptor {
    private Object target;

    private boolean isClass = false;


    private CustomInterceptor(Object target) {
        this.target = target;
    }

    private CustomInterceptor(Object target, boolean isClass) {
        this.target = target;
        this.isClass = isClass;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        boolean[] isHandled = new boolean[]{false};

        Object returnValue;
        //resolve Inject
        Class clz = target.getClass();

        AnnotationPluginResolver resolver = new AnnotationPluginResolver(isHandled, target, method, objects, methodProxy, isClass ? CustomPlugin.getClassAnnotationMap().get(clz) : CustomPlugin.getMethodAnnotationMap().get(method));

        GlobalInterceptorKit.Inject(target, clz);


        returnValue = resolver.invoke();


        return returnValue;
    }


    @SuppressWarnings("unchecked")
    public static <T> T Proxy(Class<T> targetClass) {
        if (null == targetClass) return null;
        Object proxy = null;
        try {
            Enhancer en = new Enhancer();
            en.setSuperclass(targetClass);
            en.setCallback(new CustomInterceptor(targetClass.newInstance()));
            proxy = en.create();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) proxy;
    }


    @SuppressWarnings("unchecked")
    public static <T> T Proxy(T target) {
        if (null == target) return null;
        Object proxy = null;

        Enhancer en = new Enhancer();
        en.setSuperclass(target.getClass());
        en.setCallback(new CustomInterceptor(target));
        proxy = en.create();

        return (T) proxy;
    }


    @SuppressWarnings("unchecked")
    public static <T> T Proxy(T target, boolean isClass) {
        if (null == target) return null;
        Object proxy = null;

        Enhancer en = new Enhancer();
        en.setSuperclass(target.getClass());
        en.setCallback(new CustomInterceptor(target, isClass));
        proxy = en.create();

        return (T) proxy;
    }
}
