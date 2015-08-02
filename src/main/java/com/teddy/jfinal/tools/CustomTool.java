package com.teddy.jfinal.tools;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.handler.CustomInterceptor;
import com.teddy.jfinal.handler.DateSetterHelper;
import com.teddy.jfinal.handler.TransactionHelper;

/**
 * Created by teddy on 2015/7/26.
 */
public class CustomTool {

    public static <T> T auto(Class<T> target) {
        if (target.isAnnotationPresent(Model.class)) {
            return DateSetterHelper.Proxy(target);
        } else {
            try {
                return target.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> T auto(T target) {
        if (target.getClass().isAnnotationPresent(Model.class)) {
            return DateSetterHelper.Proxy(target);
        } else {
            return target;
        }
    }

    public static <T> T transaction(T target) {
        return TransactionHelper.Proxy(target);
    }

    public static <T> T transaction(Class<T> target) {
        return TransactionHelper.Proxy(target);
    }

    public static <T> T custom(T target) {
        return CustomInterceptor.Proxy(target);
    }

    public static <T> T custom(Class<T> target) {
        return CustomInterceptor.Proxy(target);
    }

}
