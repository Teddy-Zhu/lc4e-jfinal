package com.teddy.jfinal.config;

import com.jfinal.config.*;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.plugin.CustomPlugin;
import com.teddy.jfinal.plugin.core.JfinalPlugin;
import com.teddy.jfinal.tools.ReflectTool;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;

import static org.apache.log4j.Logger.getLogger;

/**
 * Created by teddy on 2015/7/18.
 */
public class Config extends com.jfinal.config.JFinalConfig {


    private static final Logger logger = getLogger(Config.class);

    private static CustomPlugin customConfig = new CustomPlugin();

    public void configConstant(Constants me) {
        //Init Property File And Scan Annotation Classes
        try {
            if (!customConfig.init(loadPropertyFile(Const.CONFIG_FILE)))
                throw new RuntimeException("custom plugin init error");
        } catch (Lc4eException | InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            logger.error("init failed");
        }
        customConfig.init(me);


        //Init original Config
        customConfig.getConfigs().forEach(config -> config.configConstant(me));

    }

    public void configRoute(Routes me) {
        customConfig.init(me);
        customConfig.getConfigs().forEach(config -> config.configRoute(me));

    }


    public void configPlugin(Plugins me) {
        me.add(customConfig);
        customConfig.init(me);
        customConfig.getConfigs().forEach(config -> config.configPlugin(me));
    }

    public void configInterceptor(Interceptors me) {
        customConfig.init(me);
        customConfig.getConfigs().forEach(config -> config.configInterceptor(me));
    }

    public void configHandler(Handlers me) {
        customConfig.init(me);
        customConfig.getConfigs().forEach(config -> config.configHandler(me));
    }

    @Override
    public void afterJFinalStart() {
        customConfig.getConfigs().forEach(config -> config.afterJFinalStart());
    }

    @Override
    public void beforeJFinalStop() {
        customConfig.getConfigs().forEach(config -> config.beforeJFinalStop());
    }


    public static CustomPlugin getCustomConfig() {
        return customConfig;
    }
}
