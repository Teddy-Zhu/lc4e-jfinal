package com.teddy.jfinal.handler;

import com.jfinal.plugin.ehcache.CacheKit;
import com.teddy.jfinal.annotation.Cache;
import com.teddy.jfinal.annotation.Transaction;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.common.Dict;
import com.teddy.jfinal.handler.support.GlobalInterceptorKit;
import com.teddy.jfinal.plugin.PropPlugin;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.jfinal.tools.StringTool;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by teddy on 2015/7/25.
 */
public class CustomInterceptor implements MethodInterceptor {
    private Object target;

    public CustomInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object returnValue;
        boolean useCache;
        Object cacheKey = null;
        Cache cache = ReflectTool.getAnnotationByMethod(method, Cache.class);
        useCache = cache != null && PropPlugin.getBool(Dict.CACHE_USE, true) && cache.index() < objects.length;
        if (useCache) {
            cacheKey = cache.index() == -1 ? cache.key() : objects[cache.index()];
            if (cacheKey.getClass().isArray()) {
                cacheKey = StringTool.join((String[]) cacheKey, ",");
            } else if (cacheKey instanceof List) {
                cacheKey = StringTool.join((List<String>) cacheKey, ",");
            } else {
                cacheKey = cacheKey.toString();
            }

            cacheKey = method.getName() + cacheKey;

            returnValue = CacheKit.get(cache.cacheName(), cacheKey);
            if (returnValue != null) {
                return returnValue;
            } else if (!cache.defaultValue().equals(Const.DEFAULT_NONE)) {
                CacheKit.put(cache.cacheName(), cacheKey, cache.defaultValue());
                return cache.defaultValue();
            }
        }
        //resolve Inject
        Class clz = target.getClass();

        if (method.isAnnotationPresent(Transaction.class)) {
            Object transObj = TransactionHelper.Proxy(this.target);
            GlobalInterceptorKit.Inject(transObj, clz);
            returnValue = method.invoke(transObj, objects);
        } else {
            GlobalInterceptorKit.Inject(target, clz);
            returnValue = method.invoke(target, objects);
        }
        if (useCache) {
            CacheKit.put(cache.cacheName(), cacheKey, returnValue);
        }

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
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
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

}
