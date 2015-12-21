package com.teddy.jfinal.plugin;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.config.*;
import com.jfinal.core.ActionKey;
import com.jfinal.handler.Handler;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.teddy.jfinal.annotation.*;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.common.Dict;
import com.teddy.jfinal.entity.Route;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.handler.CustomInterceptor;
import com.teddy.jfinal.handler.GlobalInterceptor;
import com.teddy.jfinal.handler.resolve.*;
import com.teddy.jfinal.interfaces.*;
import com.teddy.jfinal.tools.ClassSearcherTool;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.jfinal.tools.StringTool;
import org.apache.commons.collections.iterators.ObjectArrayIterator;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Function;

/**
 * Created by teddy on 2015/7/18.
 */
public class CustomPlugin implements IPlugin {

    private static final Logger LOGGER = Logger.getLogger(CustomPlugin.class);

    private static Map<Class<? extends Annotation>, Set<Class>> classesMap;

    private static Map<Class<? extends Throwable>, Method> exceptionsMap;

    private static List<IHandler> pluginIhanders;

    private static List<IInterceptor> pluginIinterceptors;

    private static List<com.teddy.jfinal.interfaces.IPlugin> pluginIplugins;

    private static Class<?> clazz;

    private static List<Route> routes;

    private static List<IPlugin> plugins;

    private static List<Interceptor> interceptors;

    private static List<com.jfinal.handler.Handler> handlers;

    private static Set<String> actionKeys;

    private static AttributeKitI attributeKit;

    private static ValidateKitI validateKit;

    private static Map<Class, Map<Field, Object>> injectObjs;

    private static Map<Class<? extends Annotation>, CustomAnnotationPlugin> customs;

    private static Class[] customAns;

    private static Map<Method, Annotation[]> methodAnnotationMap;

    private static Map<Class, Annotation[]> classAnnotationMap;


    public CustomPlugin() {
    }

    public boolean init(Properties properties) throws Lc4eException, InstantiationException, NoSuchFieldException, IllegalAccessException {
        new PropPlugin(properties).start();
        exceptionsMap = new HashMap<>();

        routes = new ArrayList<>();
        plugins = new ArrayList<>();
        interceptors = new ArrayList<>();
        handlers = new ArrayList<>();
        actionKeys = new HashSet<>();
        pluginIhanders = new ArrayList<>();
        pluginIinterceptors = new ArrayList<>();
        pluginIplugins = new ArrayList<>();
        injectObjs = new HashMap<>();
        customs = new HashMap<>();
        classAnnotationMap = new HashMap<>();
        methodAnnotationMap = new HashMap<>();
        List<String> jars = (List<String>) PropPlugin.getObject(Dict.SCAN_JAR);

        if (jars.size() > 0) {
            classesMap = new ClassSearcherTool().includeAllJarsInLib(ClassSearcherTool.isValiJar()).injars(jars).getAllAnnotation();
        } else {
            classesMap = new ClassSearcherTool().getAllAnnotation();
        }
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

        return true;
    }

    public boolean enable() throws InstantiationException, NoSuchFieldException, IllegalAccessException {

        initCustomAnnotation();

        initRoutes();

        initService();

        initPlugins();

        initExceptions();

        initInterceptors();

        initHanders();

        initInject();

        return true;
    }

    @Override
    public boolean start() {
        pluginIplugins.forEach(com.teddy.jfinal.interfaces.IPlugin::start);
        return true;
    }

    @Override
    public boolean stop() {
        pluginIplugins.forEach(com.teddy.jfinal.interfaces.IPlugin::stop);
        return true;
    }


    public static Map<Class<? extends Annotation>, Set<Class>> getClassesMap() {
        return classesMap;
    }

    public static Class<?> getClazz() {
        return clazz;
    }

    public static void setClazz(Class<?> clazz) {
        CustomPlugin.clazz = clazz;
    }


    public void init(Routes me) throws InstantiationException {
        routes.forEach(route -> {
            if (StringTool.equalEmpty(route.getViewPath()))
                me.add(route.getControllerKey(), route.getControllerClass());
            else
                me.add(route.getControllerKey(), route.getControllerClass(), route.getViewPath());
        });
        for (com.teddy.jfinal.interfaces.IPlugin plugin : pluginIplugins) {
            plugin.init(me);
        }
    }


    public void init(Plugins me) throws InstantiationException {
        plugins.forEach(me::add);
        for (com.teddy.jfinal.interfaces.IPlugin plugin : pluginIplugins) {
            plugin.init(me);
        }
    }

    public void init(Constants me) throws InstantiationException {
        for (com.teddy.jfinal.interfaces.IPlugin plugin : pluginIplugins) {
            plugin.init(me);
        }
    }

    public void init(Interceptors me) throws InstantiationException {
        interceptors.forEach(me::add);
        for (com.teddy.jfinal.interfaces.IPlugin plugin : pluginIplugins) {
            plugin.init(me);
        }
    }

    public void init(Handlers me) throws InstantiationException {
        handlers.forEach(me::add);
        for (com.teddy.jfinal.interfaces.IPlugin plugin : pluginIplugins) {
            plugin.init(me);
        }
    }


    private void initInject() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        if (!classesMap.containsKey(Service.class) && !classesMap.containsKey(Controller.class)) {
            return;
        }
        //Inject with @Inject Service
        Set<Class> Classes = new HashSet<>();
        if (classesMap.containsKey(Service.class)) {
            Classes.addAll(classesMap.get(Service.class));
        }
        if (classesMap.containsKey(Controller.class)) {
            Classes.addAll(classesMap.get(Controller.class));
        }
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
        Field target = Invocation.class.getDeclaredField("target");
        target.setAccessible(true);
        Map<Field, Object> fieldList = new HashMap<>();
        fieldList.put(target, target.getType().newInstance());
        injectObjs.put(Invocation.class, fieldList);
    }

    private void initCustomAnnotation() {
        if (classesMap.containsKey(CustomAnnotation.class)) {
            Set<Class> Classes = classesMap.get(CustomAnnotation.class);
            Classes.forEach(aClass -> {
                if (CustomAnnotationPlugin.class.isAssignableFrom(aClass)) {
                    try {
                        CustomAnnotationPlugin plugin = (CustomAnnotationPlugin) aClass.newInstance();
                        customs.put(plugin.getAnnotation(), plugin);
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
            customAns = customs.keySet().toArray(new Class[customs.keySet().size()]);
        } else {
            customAns = new Class[0];
        }
    }

    private void initService() {
        if (classesMap.containsKey(Service.class)) {
            Set<Class> Classes = classesMap.get(Service.class);

            Classes.forEach(service -> {
                Annotation[] serviceAns = buildAnnotation(service);
                classAnnotationMap.put(service, serviceAns);

                Method[] methods = service.getMethods();
                for (Method method : methods) {
                    Annotation[] methodAns = buildAnnotation(method);
                    methodAnnotationMap.put(method, methodAns);
                }
            });
        }

    }

    private void initRoutes() {
        if (classesMap.containsKey(Controller.class)) {
            Set<Class> Classes = classesMap.get(Controller.class);
            Set<String> excludedMethodName = ReflectTool.buildExcludedMethodName(com.jfinal.core.Controller.class, BaseController.class);

            Classes.forEach(controller -> {
                Controller controllerBind = (Controller) controller.getAnnotation(Controller.class);
                if (controllerBind != null && BaseController.class.isAssignableFrom(controller)) {
                    Annotation[] controllerAns = buildAnnotation(controller);
                    classAnnotationMap.put(controller, controllerAns);
                    String controllerKey = controllerBind.value();
                    String controllerView = controllerBind.views();
                    if (controllerKey.equals("")) {
                        LOGGER.error(controller.getName() + " Path must not be empty");
                        return;
                    }
                    routes.add(new Route(controllerKey, controller, controllerView));
                    Method[] methods = controller.getMethods();
                    for (Method method : methods) {
                        if (!excludedMethodName.contains(method.getName())
                                && method.getParameterTypes().length == 0) {
                            String actionKey = createActionKey(controller, method, controllerKey);
                            Annotation[] methodAns = buildAnnotation(method);
                            actionKeys.add(actionKey);
                            methodAnnotationMap.put(method, methodAns);
                        }
                    }
                    LOGGER.debug("Controller Registered : controller = " + controller + ", Mapping URL = " + controllerKey);
                }
            });
        }
    }


    private Annotation[] buildAnnotation(Annotation[] methodAns, Annotation[] classAns) {

        for (Annotation an :
                classAns) {
            int index = contain(methodAns, an.annotationType());
            if (index != -1) {
                ArrayUtils.remove(methodAns, index);
            }
        }
        return ArrayUtils.addAll(classAns, methodAns);
    }

    private int contain(Annotation[] methodAns, Class<? extends Annotation> clz) {
        for (int i = 0, len = methodAns.length; i < len; i++) {
            if (methodAns[i].annotationType() == clz) {
                return i;
            }
        }
        return -1;
    }

    private Annotation[] buildAnnotation(Class clz) {
        return clz.getAnnotations();
    }

    private Annotation[] buildAnnotation(Method method) {
        return method.getAnnotations();
    }

    private String createActionKey(Class<? extends BaseController> controllerClass,
                                   Method method, String controllerKey) {
        String methodName = method.getName();
        String actionKey;

        ActionKey ak = method.getAnnotation(ActionKey.class);
        if (ak != null) {
            actionKey = ak.value().trim();
            if ("".equals(actionKey))
                throw new IllegalArgumentException(controllerClass.getName() + "." + methodName + "(): The argument of ActionKey can not be blank.");
            if (!actionKey.startsWith(Const.SLASH))
                actionKey = Const.SLASH + actionKey;
        } else if (methodName.equals("index")) {
            actionKey = controllerKey;
        } else {
            actionKey = controllerKey.equals(Const.SLASH) ? Const.SLASH + methodName : controllerKey + Const.SLASH + methodName;
        }
        return actionKey;
    }

    private void initPlugins() throws InstantiationException {

        if (classesMap.containsKey(Model.class)) {

            Set<Class> Classes = classesMap.get(Model.class);

            //Init Model annotation

            if (PropPlugin.getBool(Dict.USE_MYSQL, false)) {
                DruidPlugin druidPlugin = new DruidPlugin(PropPlugin.getValue(Dict.DATABASE_URL), PropPlugin.getValue(Dict.DATABASE_USERNAME), PropPlugin.getValue(Dict.DATABASE_PASSWORD));
                WallFilter filter = new WallFilter();
                filter.setDbType("mysql");
                if (PropPlugin.getBool(Dict.DEV_MODE, false)) {
                    filter.setLogViolation(true);
                    filter.setThrowException(false);
                }
                druidPlugin.addFilter(new StatFilter());
                druidPlugin.set(PropPlugin.getInt(Dict.DATABASE_INITIAL_SIZE, 50), PropPlugin.getInt(Dict.DATABASE_MIN_IDLE, 50), PropPlugin.getInt(Dict.DATABASE_MAX_ACTIVE, 100));
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
                arp.setShowSql(PropPlugin.getBool(Dict.DEV_MODE, false));
                arp.setDialect(new MysqlDialect());
                arp.setContainerFactory(new CaseInsensitiveContainerFactory(false));
                plugins.add(druidPlugin);
                plugins.add(arp);
            }
        }

        //Init Cache
        if (PropPlugin.getBool(Dict.USE_CACHE, true)) {
            plugins.add(new EhCachePlugin(PropPlugin.getValue(Dict.CACHE_CONFIG, Const.CONFIG_CACHE_FILE)));
        }
        //Init Shiro
        if (PropPlugin.getBool(Dict.USE_SHIRO, true)) {
            plugins.add(new ShiroPlugin());
        }


        if (classesMap.containsKey(PluginHandler.class)) {
            //Init Other Plugin By @PluginHandler
            Set<Class> Classes = classesMap.get(PluginHandler.class);
            for (Class plugin : Classes) {
                try {
                    if (com.teddy.jfinal.interfaces.IPlugin.class.isAssignableFrom(plugin)) {
                        //resolve Class implements custom IPlugin
                        pluginIplugins.add((com.teddy.jfinal.interfaces.IPlugin) plugin.newInstance());
                    } else {
                        //init jfinal plugins
                        plugins.add((IPlugin) plugin.newInstance());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //Init CORE plugin
        plugins.add(new CustomPlugin());

    }

    private void initExceptions() {
        //Init Exception annotation
        if (classesMap.containsKey(ExceptionHandlers.class)) {
            Set<Class> Classes = classesMap.get(ExceptionHandlers.class);
            for (Class exceptionClass : Classes) {

                Method[] methods = exceptionClass.getDeclaredMethods();
                for (Method method : methods) {
                    // resolve @ExceptionHandler add method into ExceptionMap
                    if (method.isAnnotationPresent(ExceptionHandler.class)) {
                        for (Class<? extends Throwable> exception : method.getAnnotation(ExceptionHandler.class).value()) {
                            exceptionsMap.put(exception, method);
                        }
                    }

                }
            }
        }
    }

    private void initInterceptors() {
        if (classesMap.containsKey(InterceptorHandler.class)) {
            Set<Class> Classes = classesMap.get(InterceptorHandler.class);
            try {
                for (Class interceptor : Classes) {
                    if (IInterceptor.class.isAssignableFrom(interceptor)) {
                        //custom IInterceptor
                        pluginIinterceptors.add((IInterceptor) interceptor.newInstance());
                    } else {
                        //Jfinal Interceptor
                        interceptors.add((Interceptor) interceptor.newInstance());
                    }
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //Init Core Interceptor
        interceptors.add(new GlobalInterceptor());
    }

    private void initHanders() {
        if (classesMap.containsKey(GlobalHandler.class)) {
            Set<Class> Classes = classesMap.get(GlobalHandler.class);
            try {
                for (Class handler : Classes) {
                    if (IHandler.class.isAssignableFrom(handler)) {
                        //custom handler with @GlobalHandler
                        pluginIhanders.add((IHandler) handler.newInstance());
                    } else {
                        //Jfinal handler
                        handlers.add((Handler) handler.newInstance());
                    }
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (PropPlugin.getBool(Dict.USE_HTTP_CACHE)) {
            //handlers.add(new HttpCacheHandler());
        }

        //Init Core Handler
        handlers.add(new com.teddy.jfinal.handler.GlobalHandler());

        if (PropPlugin.getBool(Dict.XSS)) {
            //     handlers.add(new XSSHandler());
        }


    }

    public static Map<Class<? extends Throwable>, Method> getExceptionsMap() {
        return exceptionsMap;
    }

    public static List<IHandler> getPluginIhanders() {
        return pluginIhanders;
    }

    public static void setValidateKit(ValidateKitI validateKit) {
        CustomPlugin.validateKit = validateKit;
    }

    public static void setAttributeKit(AttributeKitI attributeKit) {
        CustomPlugin.attributeKit = attributeKit;
    }

    public static Map<Class, Map<Field, Object>> getInjectObjs() {
        return injectObjs;
    }


    public static Map<Class<? extends Annotation>, CustomAnnotationPlugin> getCustoms() {
        return customs;
    }

    public static Class<? extends Annotation>[] getCustomAns() {
        return customAns;
    }

    public static AttributeKitI getAttributeKit() {
        return attributeKit;
    }

    public static ValidateKitI getValidateKit() {
        return validateKit;
    }

    public static Map<Method, Annotation[]> getMethodAnnotationMap() {
        return methodAnnotationMap;
    }

    public static Map<Class, Annotation[]> getClassAnnotationMap() {
        return classAnnotationMap;
    }
}
