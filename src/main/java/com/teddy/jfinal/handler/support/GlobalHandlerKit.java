package com.teddy.jfinal.handler.support;

import com.teddy.jfinal.plugin.CustomPlugin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by teddy on 2015/7/23.
 */
public class GlobalHandlerKit {


    public static void handleAOPMethods(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled, String name) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        for (Method method : CustomPlugin.getAopHandler().get(name)) {
            method.invoke(method.getDeclaringClass().newInstance(), target, request, response, isHandled);
        }
    }


}
