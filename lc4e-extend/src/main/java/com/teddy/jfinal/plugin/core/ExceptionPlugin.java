package com.teddy.jfinal.plugin.core;

import com.jfinal.config.*;
import com.teddy.jfinal.annotation.ExceptionHandler;
import com.teddy.jfinal.annotation.ExceptionHandlers;
import com.teddy.jfinal.interfaces.IPlugin;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by teddyzhu on 16/6/6.
 */
public class ExceptionPlugin implements IPlugin {

    private static Map<Class<? extends Throwable>, Method> exceptionsMap = new HashMap<>();

    @Override
    public void init(Plugins me) {

    }

    @Override
    public void init(Routes me) {

    }

    @Override
    public void init(Constants me) {

    }

    @Override
    public void init(Interceptors me) {

    }

    @Override
    public void init(Handlers me) {

    }

    @Override
    public boolean start(CustomPlugin configPlugin) {
        //Init Exception annotation
        configPlugin.getAnnotationClass(ExceptionHandlers.class).forEach(exceptionClass -> {
            Method[] methods = exceptionClass.getDeclaredMethods();
            for (Method method : methods) {
                // resolve @ExceptionHandler add method into ExceptionMap
                if (method.isAnnotationPresent(ExceptionHandler.class)) {
                    for (Class<? extends Throwable> exception : method.getAnnotation(ExceptionHandler.class).value()) {
                        exceptionsMap.put(exception, method);
                    }
                }
            }
        });
        return false;
    }

    @Override
    public boolean stop(CustomPlugin configPlugin) {
        return false;
    }

    public static boolean containsKey(Class<? extends Throwable> clz) {
        return exceptionsMap.containsKey(clz);
    }

    public static Method get(Class<? extends Throwable> clz) {
        return exceptionsMap.get(clz);
    }

}
