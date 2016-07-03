package com.teddy.jfinal.plugin.core;

import com.jfinal.config.*;
import com.teddy.jfinal.handler.CacheControlHandler;
import com.teddy.jfinal.interfaces.IPlugin;
import com.teddy.jfinal.plugin.CustomPlugin;

/**
 * Created by teddyzhu on 16/6/29.
 */
public class CacheControlPlugin implements IPlugin {
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
        me.add(new CacheControlHandler());
    }

    @Override
    public boolean start(CustomPlugin configPlugin) {
        return true;
    }

    @Override
    public boolean stop(CustomPlugin configPlugin) {
        return true;
    }
}
