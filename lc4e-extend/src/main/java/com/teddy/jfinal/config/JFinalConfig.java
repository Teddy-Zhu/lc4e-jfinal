package com.teddy.jfinal.config;

import com.jfinal.config.*;

/**
 * Created by teddy on 2015/7/20.
 */
public interface JFinalConfig {

    void configConstant(Constants var1);

    void configRoute(Routes var1);

    void configPlugin(Plugins var1);

    void configInterceptor(Interceptors var1);

    void configHandler(Handlers var1);

    void beforeConstant(Constants var1);

    void afterJFinalStart();

    void beforeJFinalStop();

}
