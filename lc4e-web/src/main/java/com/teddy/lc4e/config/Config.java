package com.teddy.lc4e.config;

import com.jfinal.config.*;
import com.jfinal.kit.JsonKit;
import com.teddy.jfinal.annotation.ConfigHandler;
import com.teddy.jfinal.common.Dict;
import com.teddy.jfinal.plugin.core.PropPlugin;
import com.teddy.jfinal.plugin.core.ShiroPlugin;
import com.teddy.jfinal.plugin.jetbrick.function.TemplateTool;
import com.teddy.jfinal.plugin.jetbrick.tag.staticIncludeTag;
import com.teddy.jfinal.plugin.shiro.ShiroMethod;
import com.teddy.lc4e.util.plugins.AttributeKit;
import com.teddy.lc4e.util.plugins.ValidateKit;
import com.teddy.lc4e.util.shiro.UserRealm;
import com.teddy.lc4e.web.service.ComVarService;
import com.teddy.lc4e.web.service.MenuService;
import jetbrick.template.JetConfig;
import jetbrick.template.JetEngine;
import jetbrick.template.JetGlobalContext;
import jetbrick.template.loader.ServletResourceLoader;
import jetbrick.template.resolver.GlobalResolver;
import jetbrick.template.web.JetWebEngine;
import jetbrick.template.web.jfinal.JetTemplateRenderFactory;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by teddy on 2015/7/18.
 */
@ConfigHandler
public class Config extends JFinalConfig {

    private static final Logger LOGGER = Logger.getLogger(Config.class);

    public void configConstant(Constants me) {
        PropPlugin prop = com.teddy.jfinal.config.Config.getCustomConfig().getProp();
        //enable comvar and validate comvar function
        com.teddy.jfinal.config.Config.getCustomConfig().setAttributeKit(new AttributeKit());
        com.teddy.jfinal.config.Config.getCustomConfig().setValidateKit(new ValidateKit());
        ShiroPlugin.setRealm(new UserRealm());

        me.setEncoding(prop.getValue(Dict.ENCODING, "utf-8"));

        me.setDevMode(prop.getBool(Dict.DEV_MODE, false));

        me.setMainRenderFactory(new JetTemplateRenderFactory());

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

        JetEngine engine = JetWebEngine.getEngine();

        JetGlobalContext globalContext = engine.getGlobalContext();

        GlobalResolver resolver = engine.getGlobalResolver();
        resolver.registerFunctions(ShiroMethod.class);
        resolver.registerFunctions(TemplateTool.class);
        resolver.registerTags(staticIncludeTag.class);

        globalContext.set(String.class, "SiteName", ComVarService.service.getComVarValueByName("SiteName"));
        globalContext.set(List.class, "menulist", MenuService.service.getMenuTree());
        globalContext.set(String.class, "menusString", JsonKit.toJson(MenuService.service.getMenuTree()));
        globalContext.set(String.class, "version", com.teddy.jfinal.config.Config.getCustomConfig().getProp().getValue(Dict.version));
        globalContext.set(String.class, "Theme", "/themes/" + ComVarService.service.getComVarValueByName("DefaultTheme"));


        Key.kvs.put("Theme", ComVarService.service.getComVarValueByName("DefaultTheme"));

        Field field, field1;
        try {

            field = JetConfig.class.getDeclaredField("templateSuffix");
            field.setAccessible(true);
            field.set(engine.getConfig(), ".html");

            field = JetConfig.class.getDeclaredField("templateLoaders");
            field.setAccessible(true);

            List<ServletResourceLoader> loaders = (List<ServletResourceLoader>) field.get(engine.getConfig());

            field1 = ServletResourceLoader.class.getSuperclass().getDeclaredField("root");
            field1.setAccessible(true);
            field1.set(loaders.get(0), "/WEB-INF/views/themes/" + Key.kvs.get("Theme"));

            field = JetConfig.class.getDeclaredField("trimDirectiveComments");
            field.setAccessible(true);
            field.set(engine.getConfig(), true);

            field = JetConfig.class.getDeclaredField("trimDirectiveCommentsPrefix");
            field.setAccessible(true);
            field.set(engine.getConfig(), "<!---");

            field = JetConfig.class.getDeclaredField("trimDirectiveCommentsSuffix");
            field.setAccessible(true);
            field.set(engine.getConfig(), "--->");
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
