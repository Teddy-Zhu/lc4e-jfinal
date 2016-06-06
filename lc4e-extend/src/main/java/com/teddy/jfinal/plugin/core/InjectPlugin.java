package com.teddy.jfinal.plugin.core;

import com.jfinal.aop.Invocation;
import com.jfinal.config.*;
import com.teddy.jfinal.annotation.Controller;
import com.teddy.jfinal.annotation.Inject;
import com.teddy.jfinal.annotation.Service;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.handler.CustomInterceptor;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import com.teddy.jfinal.interfaces.IPlugin;
import com.teddy.jfinal.plugin.CustomPlugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by teddyzhu on 16/6/6.
 */
public class InjectPlugin implements IPlugin {

    private static Map<Class, Map<Field, Object>> injectObjs = new HashMap<>();


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
        //Autowired with @Autowired Service
        Set<Class> Classes = new HashSet<>();

        Classes.addAll(configPlugin.getAnnotationClass(Service.class));

        Classes.addAll(configPlugin.getAnnotationClass(Controller.class));

        Classes.forEach(service -> {
            List<Field> fieldList = new ArrayList<>();
            Field[] fields = service.getFields();
            Map<Field, Object> injectFields = new HashMap<>();
            for (Field fieldTmp : fields) {
                if (Modifier.isStatic(fieldTmp.getModifiers()) && fieldTmp.getName().equals(Const.INJECT_SERVICE)) {
                    fieldTmp.setAccessible(true);
                    try {
                        fieldTmp.set(service, CustomInterceptor.Proxy(service));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                if (fieldTmp.isAnnotationPresent(Inject.class)) {
                    Class clzss = fieldTmp.getType();
                    fieldTmp.setAccessible(true);
                    fieldList.add(fieldTmp);
                    Object tmp = CustomInterceptor.Proxy(clzss);
                    injectFields.put(fieldTmp, tmp);
                }
            }
            injectObjs.put(service, injectFields);
        });

        //for custom inject
        try {

            Field target = Invocation.class.getDeclaredField("target");
            target.setAccessible(true);
            Map<Field, Object> fieldList = new HashMap<>();
            fieldList.put(target, target.getType().newInstance());
            injectObjs.put(Invocation.class, fieldList);
        } catch (NoSuchFieldException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean stop(CustomPlugin configPlugin) {
        return false;
    }

    public static boolean containsKey(Class clz){
        return injectObjs.containsKey(clz);
    }

    public static Map<Field, Object> get(Class clz){
        return injectObjs.get(clz);
    }
}
