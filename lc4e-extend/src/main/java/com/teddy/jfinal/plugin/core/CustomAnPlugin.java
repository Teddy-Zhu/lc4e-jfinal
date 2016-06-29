package com.teddy.jfinal.plugin.core;

import com.jfinal.config.*;
import com.teddy.jfinal.annotation.CustomAnnotation;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import com.teddy.jfinal.interfaces.IPlugin;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by teddyzhu on 16/6/6.
 */
public class CustomAnPlugin implements IPlugin {

    private static Map<Class<? extends Annotation>, CustomAnnotationPlugin> customs = new HashMap<>();

    @Override
    public void init(Plugins me) {

    }

    @Override
    public void init(Routes me) {

    }

    @Override
    public void init(Constants me) {

    }

    @Override
    public void init(Interceptors me) {

    }

    @Override
    public void init(Handlers me) {

    }

    @Override
    public boolean start(CustomPlugin configPlugin) {
        configPlugin.getAnnotationClass(CustomAnnotation.class).forEach(aClass -> {
            if (CustomAnnotationPlugin.class.isAssignableFrom(aClass)) {
                try {
                    CustomAnnotationPlugin plugin = (CustomAnnotationPlugin) aClass.newInstance();
                    customs.put(plugin.getAnnotation(), plugin);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        return true;
    }

    @Override
    public boolean stop(CustomPlugin configPlugin) {
        return false;
    }


    public static boolean containsKey(Class<? extends Annotation> clz) {
        return customs.containsKey(clz);
    }

    public static CustomAnnotationPlugin get(Class<? extends Annotation> clz) {
        return customs.get(clz);
    }
}
