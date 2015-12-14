package com.teddy.jfinal.tools;

import com.teddy.jfinal.handler.CustomInterceptor;

/**
 * Created by teddy on 2015/7/26.
 */
public class CustomTool {

    public static <T> T custom(T target) {
        return CustomInterceptor.Proxy(target);
    }

    public static <T> T custom(Class<T> target) {
        return CustomInterceptor.Proxy(target);
    }

}
