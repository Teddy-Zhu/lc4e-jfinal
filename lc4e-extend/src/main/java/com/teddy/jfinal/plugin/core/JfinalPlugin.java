package com.teddy.jfinal.plugin.core;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.annotation.PluginHandler;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.common.Dict;
import com.teddy.jfinal.handler.CustomInterceptor;
import com.teddy.jfinal.interfaces.IPlugin;
import com.teddy.jfinal.plugin.CustomPlugin;
import com.teddy.jfinal.tools.PluginTool;
import com.teddy.jfinal.tools.PropTool;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by teddyzhu on 16/6/6.
 */
public class JfinalPlugin implements IPlugin {

    // plugin which implements jfinal IPlugin by user
    private static List<com.jfinal.plugin.IPlugin> plugins = new ArrayList<>();

    // plugin which implements IPlugin by user
    private static List<IPlugin> lc4ePlugins = new ArrayList<>();

    @Override
    public void init(Plugins me) {
        plugins.forEach(me::add);
        lc4ePlugins.forEach(iPlugin -> iPlugin.init(me));
    }

    @Override
    public void init(Routes me) {
        lc4ePlugins.forEach(iPlugin -> iPlugin.init(me));
    }

    @Override
    public void init(Constants me) {
        lc4ePlugins.forEach(iPlugin -> iPlugin.init(me));
    }

    @Override
    public void init(Interceptors me) {
        lc4ePlugins.forEach(iPlugin -> iPlugin.init(me));
    }

    @Override
    public void init(Handlers me) {
        lc4ePlugins.forEach(iPlugin -> iPlugin.init(me));
    }

    @Override
    public boolean start(CustomPlugin configPlugin) {
        PropTool prop = configPlugin.getProp();
        Set<Class> Classes = configPlugin.getAnnotationClass(Model.class);

        if (Classes != null) {

            //Init Model annotation

            if (prop.getBool(Dict.USE_MYSQL, false)) {
                DruidPlugin druidPlugin = new DruidPlugin(prop.getValue(Dict.DATABASE_URL), prop.getValue(Dict.DATABASE_USERNAME), prop.getValue(Dict.DATABASE_PASSWORD));
                WallFilter filter = new WallFilter();
                filter.setDbType("mysql");
                if (prop.getBool(Dict.DEV_MODE, false)) {
                    filter.setLogViolation(true);
                    filter.setThrowException(false);
                }
                druidPlugin.addFilter(new StatFilter());
                druidPlugin.set(prop.getInt(Dict.DATABASE_INITIAL_SIZE, 50), prop.getInt(Dict.DATABASE_MIN_IDLE, 50), prop.getInt(Dict.DATABASE_MAX_ACTIVE, 100));
                druidPlugin.addFilter(filter);
                ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
                try {
                    for (Class modelClass : Classes) {
                        Model modelBind = (Model) modelClass.getAnnotation(Model.class);
                        if (modelBind != null) {
                            arp.addMapping(Const.DEFAULT_NONE.equals(modelBind.value()) ? modelClass.getName().toLowerCase() : modelBind.value(), StringUtils.join(modelBind.pk(), ","), modelClass);

                            Field field = modelClass.getField(Const.DB_DAO);

                            if (field != null && !Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                                field.setAccessible(true);
                                field.set(null, CustomInterceptor.Proxy(field.getType()));
                            }
                        }
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                arp.setShowSql(prop.getBool(Dict.DEV_MODE, false));
                arp.setDialect(new MysqlDialect());
                arp.setContainerFactory(new CaseInsensitiveContainerFactory(false));
                plugins.add(druidPlugin);
                plugins.add(arp);
            }
        }

        //Init Cache
        if (prop.getBool(Dict.USE_CACHE, true)) {
            plugins.add(new EhCachePlugin(prop.getValue(Dict.CACHE_CONFIG, Const.CONFIG_CACHE_FILE)));
        }


        configPlugin.getAnnotationClass(PluginHandler.class).forEach(plugin -> {
            try {
                if (com.teddy.jfinal.interfaces.IPlugin.class.isAssignableFrom(plugin)) {
                    //resolve Class implements custom IPlugin
                    lc4ePlugins.add((com.teddy.jfinal.interfaces.IPlugin) plugin.newInstance());
                } else {
                    //init jfinal plugins
                    plugins.add((com.jfinal.plugin.IPlugin) plugin.newInstance());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        return PluginTool.startLc4ePlugin(lc4ePlugins, configPlugin);
    }

    @Override
    public boolean stop(CustomPlugin configPlugin) {
        return PluginTool.stopLc4ePlugin(lc4ePlugins, configPlugin);
    }

    @Override
    public boolean stop() {
        return PluginTool.stopLc4ePluginOrigin(lc4ePlugins) && PluginTool.stopJifnalPlugin(plugins);
    }

    @Override
    public boolean start() {
        return PluginTool.startLc4ePluginOrigin(lc4ePlugins) && PluginTool.startJfinalPluin(plugins);
    }
}
