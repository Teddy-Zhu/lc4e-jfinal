package com.teddy.jfinal.interfaces;

import com.jfinal.config.*;


/**
 * Created by teddy on 2015/7/20.
 */
public interface IPlugin {

    void init(Plugins me);

    void init(Routes me);

    void init(Constants me);

    void init(Interceptors me);

    void init(Handlers me);

    boolean start();

    boolean stop();
}

