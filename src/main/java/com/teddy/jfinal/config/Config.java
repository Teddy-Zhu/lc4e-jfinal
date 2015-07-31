package com.teddy.jfinal.config;

import com.jfinal.config.*;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.plugin.CustomPlugin;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.jfinal.Exceptions.Lc4eException;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;

/**
 * Created by teddy on 2015/7/18.
 */
public class Config extends com.jfinal.config.JFinalConfig {

    private static final Logger LOGGER = Logger.getLogger(Config.class);

    private CustomPlugin custom = new CustomPlugin();

    public void configConstant(Constants me) {

        //Init Property File And Scan Annotation Classes
        try {
            custom.init(loadPropertyFile(Const.CONFIG_FILE));
        } catch (Lc4eException e) {
            e.printStackTrace();
            LOGGER.error("init failed");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        //Init custom
        custom.init(me);

        //Init original plugins
        resolve(Const.CONFIG_CONSTANT, me);

    }

    public void configRoute(Routes me) {
        //Init custom routes with @Controller
        custom.init(me);

        //Init routes in Config Classes
        resolve(Const.CONFIG_ROUTE, me);
    }


    public void configPlugin(Plugins me) {
        //Init custom plugins with @PluginHandler implements com.teddy.jfinal.IPlugin
        custom.init(me);

        //Init plugins in Config Classes
        resolve(Const.CONFIG_PLUGIN, me);

    }

    public void configInterceptor(Interceptors me) {

        custom.init(me);

        resolve(Const.CONFIG_INTERCEPTOR, me);
    }

    public void configHandler(Handlers me) {
        custom.init(me);

        resolve(Const.CONFIG_HANDLER, me);
    }

    private void resolve(String methodName, Object me) {
        Class<?> clz = CustomPlugin.getClazz();
        Method method = null;

        try {
            method = ReflectTool.getMethodByClassAndName(clz, methodName);
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
        CustomPlugin.getAopHandler().clear();
        CustomPlugin.getClassesMap().clear();
        CustomPlugin.setClazz(null);
        CustomPlugin.getExceptionsMap().clear();
    }

}
