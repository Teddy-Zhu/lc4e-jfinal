package com.teddy.jfinal.handler.InternInterceptor;

import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.tools.ReflectTool;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by teddy on 2015/7/25.
 */
public class DateSetterHelper implements MethodInterceptor {
    private Object target;

    public DateSetterHelper(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        String methodName = method.getName();
        if (methodName.equals(Const.DB_UPDATE) || methodName.equals(Const.DB_SAVE)) {
            Date dt = new Date();
            Method setMethod = ReflectTool.getMethodByClassAndNameBase(target.getClass(), Const.DB_SET);
            setMethod.invoke(target, Const.DB_UPDATETIME, dt);
            if (methodName.equals(Const.DB_SAVE)) {
                setMethod.invoke(target, Const.DB_CREATETIME, dt);
            }
        }
        return method.invoke(target, objects);
    }


    @SuppressWarnings("unchecked")
    public static <T> T Proxy(Class<T> targetClass) {
        if (null == targetClass) return null;
        Object proxy = null;
        try {
            Enhancer en = new Enhancer();
            en.setSuperclass(targetClass);
            en.setCallback(new DateSetterHelper(targetClass.newInstance()));
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
        en.setCallback(new DateSetterHelper(target));
        proxy = en.create();

        return (T) proxy;
    }

}
