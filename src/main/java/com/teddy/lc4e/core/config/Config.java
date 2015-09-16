package com.teddy.lc4e.core.config;

import com.jfinal.config.*;
import com.teddy.jfinal.annotation.ConfigHandler;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.common.Dict;
import com.teddy.jfinal.config.JFinalConfig;
import com.teddy.jfinal.plugin.PropPlugin;
import com.teddy.jfinal.plugin.beetl.BeetlTool;
import com.teddy.jfinal.plugin.beetl.Lc4eBeetlRenderFactory;
import com.teddy.jfinal.plugin.beetl.tag.staticIncludeTag;
import com.teddy.jfinal.plugin.shiro.ShiroMethod;
import com.teddy.lc4e.core.web.service.ComVarService;
import com.teddy.lc4e.core.web.service.MenuService;
import org.apache.log4j.Logger;
import org.beetl.core.GroupTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by teddy on 2015/7/18.
 */
@ConfigHandler
public class Config implements JFinalConfig {

    private static final Logger LOGGER = Logger.getLogger(Config.class);

    public void configConstant(Constants me) {

        me.setEncoding(PropPlugin.getValue(Dict.ENCODING, "utf-8"));

        me.setDevMode(PropPlugin.getBool(Dict.DEV_MODE, false));

        me.setMainRenderFactory(new Lc4eBeetlRenderFactory());

        GroupTemplate groupTemplate = Lc4eBeetlRenderFactory.groupTemplate;

        groupTemplate.registerFunctionPackage("auth", new ShiroMethod());

        groupTemplate.registerFunctionPackage("tool", new BeetlTool());

        groupTemplate.registerTag("staticInclude", staticIncludeTag.class);
        //groupTemplate.registerFunction("i18nFormat", new I18nFormat());

        /*
        me.setError401View("/common/401.html");
        me.setError403View("/common/403.html");
        me.setError404View("/common/404.html");
        me.setError500View("/common/500.html");
        */
    }

    public void configRoute(Routes me) {

    }


    public void configPlugin(Plugins me) {


    }

    public void configInterceptor(Interceptors me) {


    }

    public void configHandler(Handlers me) {

    }

    public void beforeJFinalStop() {
    }

    public void afterJFinalStart() {
        Map<String, Object> maps = new HashMap<>();
        maps.put("SiteName", ComVarService.service.getComVarValueByName("SiteName"));
        maps.put("menulist", MenuService.service.getMenuTree());
        maps.put("version", PropPlugin.getValue(Dict.version));
        Lc4eBeetlRenderFactory.groupTemplate.setSharedVars(maps);
    }
}
