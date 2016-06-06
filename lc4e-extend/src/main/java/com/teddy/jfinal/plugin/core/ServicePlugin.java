package com.teddy.jfinal.plugin.core;

import com.jfinal.config.*;
import com.teddy.jfinal.annotation.Service;
import com.teddy.jfinal.interfaces.IPlugin;
import com.teddy.jfinal.plugin.CustomPlugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by teddyzhu on 16/6/6.
 */
public class ServicePlugin implements IPlugin {

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
        configPlugin.getAnnotationClass(Service.class).forEach(service -> {
            Annotation[] serviceAns = service.getAnnotations();
            configPlugin.getClassAnnotationMap().put(service, serviceAns);

            Method[] methods = service.getMethods();
            for (Method method : methods) {
                Annotation[] methodAns = method.getAnnotations();
                configPlugin.getMethodAnnotationMap().put(method, methodAns);
            }
        });

        return true;
    }

    @Override
    public boolean stop(CustomPlugin configPlugin) {
        return true;
    }
}
