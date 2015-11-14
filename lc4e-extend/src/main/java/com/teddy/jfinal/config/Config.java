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

    private CustomPlugin custom = new CustomPlugin();

    public void configConstant(Constants me) {
        //Init Property File And Scan Annotation Classes
        try {
            custom.init(loadPropertyFile(Const.CONFIG_FILE));

            resolve(Const.CONFIG_BEFORE_CONSTANT, me);

            custom.enable();

            custom.init(me);



        } catch (Lc4eException | InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            logger.error("init failed");
        }

        //Init original plugins
        resolve(Const.CONFIG_CONSTANT, me);
    }

    public void configRoute(Routes me) {
        try {
            //Init custom routes with @Controller
            custom.init(me);
            //Init routes in Config Classes
            resolve(Const.CONFIG_ROUTE, me);
        } catch (InstantiationException e) {
            e.printStackTrace();
            logger.error("init Route error");
        }
    }


    public void configPlugin(Plugins me) {

        try {
            //Init custom plugins with @PluginHandler implements com.teddy.jfinal.IPlugin
            custom.init(me);
            //Init plugins in Config Classes
            resolve(Const.CONFIG_PLUGIN, me);
        } catch (InstantiationException e) {
            e.printStackTrace();
            logger.error("init plugin error");
        }


    }

    public void configInterceptor(Interceptors me) {

        try {
            custom.init(me);
            resolve(Const.CONFIG_INTERCEPTOR, me);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }

    public void configHandler(Handlers me) {
        try {
            custom.init(me);
            resolve(Const.CONFIG_HANDLER, me);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }

    private void resolve(String methodName, Object me) {
        Class<?> clz = CustomPlugin.getClazz();
        Method method;
        try {
            method = ReflectTool.getMethodByClassAndName(clz, methodName);
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
        CustomPlugin.getClassesMap().clear();
        CustomPlugin.setClazz(null);
        CustomPlugin.getExceptionsMap().clear();
    }

}
