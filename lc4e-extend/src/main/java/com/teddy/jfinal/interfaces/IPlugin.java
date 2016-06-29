package com.teddy.jfinal.interfaces;

import com.jfinal.config.*;
import com.teddy.jfinal.plugin.core.CustomPlugin;


/**
 * Created by teddy on 2015/7/20.
 */
public interface IPlugin {

    void init(Plugins me);

    void init(Routes me);

    void init(Constants me);

    void init(Interceptors me);

    void init(Handlers me);

    boolean start(CustomPlugin configPlugin);

    boolean stop(CustomPlugin configPlugin);

    default boolean start() {
        return true;
    }

    default boolean stop() {
        return true;
    }


}

