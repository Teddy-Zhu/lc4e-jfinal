package com.teddy.jfinal.config;

import com.jfinal.config.*;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.plugin.CustomPlugin;
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
        resolve(Const.CONFIG_CONSTANT, me);


    }

    public void configRoute(Routes me) {
        customConfig.init(me);
        resolve(Const.CONFIG_ROUTE, me);

    }


    public void configPlugin(Plugins me) {
        me.add(customConfig);
        customConfig.init(me);
        resolve(Const.CONFIG_PLUGIN, me);
    }

    public void configInterceptor(Interceptors me) {
        customConfig.init(me);
        resolve(Const.CONFIG_INTERCEPTOR, me);
    }

    public void configHandler(Handlers me) {
        customConfig.init(me);
        resolve(Const.CONFIG_HANDLER, me);
    }

    private void resolve(String methodName, Object me) {
        Class<?> clz = customConfig.getClazz();
        Method method;
        try {
            method = ReflectTool.getDeclaredMethodByClassAndName(clz, methodName);
            method.setAccessible(true);
            if (me == null) {
                method.invoke(clz.newInstance());
            } else {
                method.invoke(clz.newInstance(), me);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterJFinalStart() {
        resolve(Const.AFTER_JFINAL_START, null);
    }

    @Override
    public void beforeJFinalStop() {
        resolve(Const.BEFORE_JFINAL_STOP, null);
    }


    public static CustomPlugin getCustomConfig() {
        return customConfig;
    }
}
