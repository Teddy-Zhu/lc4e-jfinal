package com.teddy.jfinal.handler;

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
public class DBHelper implements MethodInterceptor {
    private Object target;

    public DBHelper(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {


        Method setMethod = ReflectTool.getMethodByClassAndNameBase(obj.getClass(), Const.DB_SET);
        String methodName = method.getName();
        if (methodName.equals(Const.DB_UPDATE) || methodName.equals(Const.DB_SAVE)) {
            Date dt = new Date();
            setMethod.invoke(obj, Const.DB_UPDATETIME, dt);
            if (methodName.equals(Const.DB_SAVE)) {
                setMethod.invoke(obj, Const.DB_CREATETIME, dt);
            }
        }
        return methodProxy.invokeSuper(obj, objects);
    }


    @SuppressWarnings("unchecked")
    public static <T> T Proxy(Class<T> targetClass) {
        if (null == targetClass) return null;
        Object proxy = null;
        try {
            Enhancer en = new Enhancer();
            en.setSuperclass(targetClass);
            en.setCallback(new DBHelper(targetClass.newInstance()));
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
        en.setCallback(new DBHelper(target));
        proxy = en.create();

        return (T) proxy;
    }

}