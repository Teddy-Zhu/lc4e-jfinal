package com.teddy.jfinal.plugin.core;

import com.jfinal.config.*;
import com.jfinal.handler.Handler;
import com.teddy.jfinal.annotation.GlobalHandler;
import com.teddy.jfinal.common.Dict;
import com.teddy.jfinal.interfaces.IHandler;
import com.teddy.jfinal.interfaces.IPlugin;
import com.teddy.jfinal.plugin.CustomPlugin;
import com.teddy.jfinal.plugin.PropPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teddyzhu on 16/6/6.
 */
public class HandlePlugin  implements IPlugin{

    private static List<IHandler> lc4eHandler = new ArrayList<>();


    private static List<Handler> jfinalHandler  = new ArrayList<>();


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
        PropPlugin prop = configPlugin.getProp();
        configPlugin.getAnnotationClass(GlobalHandler.class).forEach(handler -> {
            try {
                if (IHandler.class.isAssignableFrom(handler)) {
                    //custom handler with @GlobalHandler
                    lc4eHandler.add((IHandler) handler.newInstance());
                } else {
                    //Jfinal handler
                    jfinalHandler.add((Handler) handler.newInstance());
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        //Init Core Handler
        jfinalHandler.add(new com.teddy.jfinal.handler.GlobalHandler());

        if (prop.getBool(Dict.XSS)) {
            //     handlers.add(new XSSHandler());
        }

        return false;
    }

    @Override
    public boolean stop(CustomPlugin configPlugin) {
        return false;
    }

    public static List<IHandler> getLc4eHandler() {
        return lc4eHandler;
    }
}
