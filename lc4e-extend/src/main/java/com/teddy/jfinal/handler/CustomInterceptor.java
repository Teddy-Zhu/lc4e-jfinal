package com.teddy.jfinal.handler;

import com.teddy.jfinal.config.Config;
import com.teddy.jfinal.handler.support.GlobalInterceptorKit;
import com.teddy.jfinal.plugin.annotationresolve.AnnotationPluginResolver;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by teddy on 2015/7/25.
 */
public class CustomInterceptor implements MethodInterceptor {

    private static Map<Class, AnnotationPluginResolver> annotationPluginResolverClzCache = new HashMap<>();
    private static Map<Method, AnnotationPluginResolver> annotationPluginResolverMethodCache = new HashMap<>();


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

        Class clz = target.getClass();

        //cached
        AnnotationPluginResolver resolver = isClass ? annotationPluginResolverClzCache.get(clz) : annotationPluginResolverMethodCache.get(method);
        if (resolver == null) {
            resolver = new AnnotationPluginResolver(isHandled, target, method, objects, methodProxy,
                    isClass ? Config.getCustomConfig().getClassAnnotationMap().get(clz) : Config.getCustomConfig().getMethodAnnotationMap().get(method));
            if (isClass) {
                annotationPluginResolverClzCache.put(clz, resolver);
            } else {
                annotationPluginResolverMethodCache.put(method, resolver);
            }
        } else {
            resolver.init(isHandled, target, method, objects, methodProxy);
        }

        GlobalInterceptorKit.Autowired(target, clz);

        //resolve Autowired
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

        Enhancer en = new Enhancer();
        en.setSuperclass(target.getClass());
        en.setCallback(new CustomInterceptor(target));

        return (T) en.create();
    }


    @SuppressWarnings("unchecked")
    public static <T> T Proxy(T target, boolean isClass) {
        if (null == target) return null;

        Enhancer en = new Enhancer();
        en.setSuperclass(target.getClass());
        en.setCallback(new CustomInterceptor(target, isClass));

        return (T) en.create();
    }
}
