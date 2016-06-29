package com.teddy.jfinal.plugin.core;

import com.jfinal.config.*;
import com.jfinal.plugin.IPlugin;
import com.teddy.jfinal.annotation.ConfigHandler;
import com.teddy.jfinal.common.Dict;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.handler.resolve.AttributeKitI;
import com.teddy.jfinal.handler.resolve.ValidateKitI;
import com.teddy.jfinal.tools.ClassSearcherTool;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by teddy on 2015/7/18.
 */
public class CustomPlugin implements IPlugin {

    private static final Logger LOGGER = Logger.getLogger(CustomPlugin.class);

    private Class<?> clazz;

    private AttributeKitI attributeKit;

    private ValidateKitI validateKit;

    private Map<Method, Annotation[]> methodAnnotationMap = new HashMap<>();

    private Map<Class, Annotation[]> classAnnotationMap = new HashMap<>();

    private Map<Class<? extends Annotation>, Set<Class>> classesMap = new HashMap<>();

    private PropPlugin prop;

    private List<com.teddy.jfinal.interfaces.IPlugin> plugins = new ArrayList<>();


    public CustomPlugin() {
    }

    public boolean init(Properties properties) throws Lc4eException, InstantiationException, NoSuchFieldException, IllegalAccessException {
        //init base
        prop = new PropPlugin(properties);
        prop.start();

        initScanClass();
        initConfigClass();

        //Core
        plugins.add(new JfinalPlugin());

        //autowired  for @Inject
        plugins.add(new InjectPlugin());

        //for @GlobalHandler
        plugins.add(new HandlePlugin());

        //for @Service
        plugins.add(new ServicePlugin());

        //for @Controller
        plugins.add(new RoutePlugin());

        //for @InterceptorHandler
        plugins.add(new InterceptorPlugin());

        //for @ExceptionHandlers @ExceptionHandler
        plugins.add(new ExceptionPlugin());


        //for @CustomAnnotation
        plugins.add(new CustomAnPlugin());

        //Init Shiro
        if (prop.getBool(Dict.USE_SHIRO, true)) {
            plugins.add(new ShiroPlugin());
        }

        //enable CacheControl
        if (prop.getBool(Dict.USE_CACHE_CONTROL, true)) {
            plugins.add(new CacheControlPlugin());
        }


        plugins.forEach(plugin -> plugin.start(this));

        return true;
    }

    private void initConfigClass() throws Lc4eException {
        if (!classesMap.containsKey(ConfigHandler.class)) {
            LOGGER.error("Init Config Failed,Must be submit a config class with Annotation @ConfigHander");
            throw new Lc4eException("Init Config Failed,Must be submit a config class with Annotation @ConfigHander");
        }
        Set<Class> clzes = classesMap.get(ConfigHandler.class);

        if (clzes.size() != 1) {
            LOGGER.error("Init Config Failed,Must be submit a config class with Annotation @ConfigHander");
            throw new Lc4eException("Init Config Failed,Must be submit a config class with Annotation @ConfigHander");
        }
        clzes.forEach(aClass -> clazz = aClass);
    }

    private void initScanClass() {
        List<String> jars = (List<String>) prop.getObject(Dict.SCAN_JAR);

        if (jars.size() > 0) {
            classesMap = new ClassSearcherTool().includeAllJarsInLib(ClassSearcherTool.isValiJar()).injars(jars).getAllAnnotation();
        } else {
            classesMap = new ClassSearcherTool().getAllAnnotation();
        }

    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }


    public void setValidateKit(ValidateKitI validateKit) {
        this.validateKit = validateKit;
    }

    public void setAttributeKit(AttributeKitI attributeKit) {
        this.attributeKit = attributeKit;
    }

    public AttributeKitI getAttributeKit() {
        return attributeKit;
    }

    public ValidateKitI getValidateKit() {
        return validateKit;
    }

    public Map<Method, Annotation[]> getMethodAnnotationMap() {
        return methodAnnotationMap;
    }

    public Map<Class, Annotation[]> getClassAnnotationMap() {
        return classAnnotationMap;
    }

    public Set<Class> getAnnotationClass(Class<? extends Annotation> clz) {
        return classesMap.getOrDefault(clz, new HashSet<>());
    }

    public boolean containsAnnotation(Class<? extends Annotation> clz) {
        return classesMap.containsKey(clz);
    }

    public PropPlugin getProp() {
        return prop;
    }

    public void setProp(PropPlugin prop) {
        this.prop = prop;
    }


    public void init(Plugins me) {
        plugins.forEach(iPlugin -> iPlugin.init(me));
    }

    public void init(Routes me) {
        plugins.forEach(iPlugin -> iPlugin.init(me));
    }

    public void init(Constants me) {
        plugins.forEach(iPlugin -> iPlugin.init(me));
    }

    public void init(Interceptors me) {
        plugins.forEach(iPlugin -> iPlugin.init(me));
    }

    public void init(Handlers me) {
        plugins.forEach(iPlugin -> iPlugin.init(me));
    }

    @Override
    public boolean start() {
        plugins.forEach(com.teddy.jfinal.interfaces.IPlugin::start);
        return true;
    }
    @Override
    public boolean stop() {
        plugins.forEach(com.teddy.jfinal.interfaces.IPlugin::stop);
        plugins.forEach(iPlugin -> iPlugin.stop(this));
        return true;
    }
}
