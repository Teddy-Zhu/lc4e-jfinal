package com.teddy.jfinal.plugin.core;

import com.jfinal.config.*;
import com.jfinal.handler.Handler;
import com.teddy.jfinal.annotation.GlobalHandler;
import com.teddy.jfinal.interfaces.IPlugin;
import com.teddy.jfinal.plugin.CustomPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teddyzhu on 16/6/6.
 */
public class HandlePlugin implements IPlugin {

    private static List<Handler> jfinalHandler = new ArrayList<>();


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
        jfinalHandler.forEach(me::add);
    }

    @Override
    public boolean start(CustomPlugin configPlugin) {
        configPlugin.getAnnotationClass(GlobalHandler.class).forEach(handler -> {
            try {

                //Jfinal handler
                jfinalHandler.add((Handler) handler.newInstance());

            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return true;
    }

    @Override
    public boolean stop(CustomPlugin configPlugin) {
        return true;
    }


}
