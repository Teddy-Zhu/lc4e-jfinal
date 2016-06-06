package com.teddy.jfinal.plugin.core;

import com.jfinal.aop.Interceptor;
import com.jfinal.config.*;
import com.teddy.jfinal.annotation.InterceptorHandler;
import com.teddy.jfinal.handler.GlobalInterceptor;
import com.teddy.jfinal.interfaces.IPlugin;
import com.teddy.jfinal.plugin.CustomPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teddyzhu on 16/6/6.
 */
public class InterceptorPlugin implements IPlugin {


    private static List<Interceptor> interceptors = new ArrayList<>();

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
        interceptors.forEach(me::add);
    }

    @Override
    public void init(Handlers me) {

    }

    @Override
    public boolean start(CustomPlugin configPlugin) {

        configPlugin.getAnnotationClass(InterceptorHandler.class).forEach(interceptor -> {
            try {
                if (Interceptor.class.isAssignableFrom(interceptor)) {
                    //Jfinal Interceptor
                    interceptors.add((Interceptor) interceptor.newInstance());
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        //Init Core Interceptor
        interceptors.add(new GlobalInterceptor());

        return true;
    }

    @Override
    public boolean stop(CustomPlugin configPlugin) {
        return false;
    }
}
