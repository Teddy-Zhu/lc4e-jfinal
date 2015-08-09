package com.teddy.jfinal.plugin;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.aop.Interceptor;
import com.jfinal.config.*;
import com.jfinal.core.ActionKey;
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
import com.teddy.jfinal.interfaces.BaseController;
import com.teddy.jfinal.interfaces.Handler;
import com.teddy.jfinal.tools.ClassSearcherTool;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.jfinal.tools.StringTool;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by teddy on 2015/7/18.
 */
public class CustomPlugin implements IPlugin {

    private static final Logger LOGGER = Logger.getLogger(CustomPlugin.class);

    private static Map<Class<? extends Annotation>, Set<Class>> classesMap;

    private static Map<Class<? extends Exception>, Method> exceptionsMap;

    private static Map<String, Set<Method>> aopHandler;

    private static Map<String, List<Annotation>> methodAnnotationsHandler;

    private static Map<String, List<Annotation>> afterMethodAnnoHandler;

    private static Class<?> clazz;

    private static List<Route> routes;

    private static List<IPlugin> plugins;

    private static List<Interceptor> interceptors;

    private static List<com.jfinal.handler.Handler> handlers;


    public CustomPlugin() {
    }

    public boolean init(Properties properties) throws Lc4eException, InstantiationException, NoSuchFieldException, IllegalAccessException {
        new PropPlugin(properties).start();
        exceptionsMap = new HashMap<>();
        aopHandler = new HashMap<>();

        List<String> list = ReflectTool.getKeyWordConst("PLUGIN_", "AFTER_", "BEFORE_");
        list.forEach(name -> aopHandler.put(name, new HashSet<>()));
        routes = new ArrayList<>();
        plugins = new ArrayList<>();
        interceptors = new ArrayList<>();
        handlers = new ArrayList<>();

        methodAnnotationsHandler = new HashMap<>();
        afterMethodAnnoHandler = new HashMap<>();
        List<String> jars = (List<String>) PropPlugin.getObject(Dict.SCAN_JAR);
        if (jars.size() > 0) {
            classesMap = ClassSearcherTool.of(Service.class, PluginHandler.class, ConfigHandler.class, Controller.class, Model.class, ExceptionHandlers.class, InterceptorHandler.class).includeAllJarsInLib(ClassSearcherTool.isValiJar()).injars(jars).search();
        } else {
            classesMap = ClassSearcherTool.of(Service.class, PluginHandler.class, ConfigHandler.class, Controller.class, Model.class, ExceptionHandlers.class, InterceptorHandler.class).search();
        }

        Set<Class> clzes = classesMap.get(ConfigHandler.class);

        if (clzes.size() != 1) {
            LOGGER.error("Init Config Failed,Must be submit a config class with Annotation @ConfigHander");
            throw new Lc4eException("Init Config Failed,Must be submit a config class with Annotation @ConfigHander");
        }
        clzes.forEach(aClass -> clazz = aClass);

        initRoutes();

        initPlugins();

        initExceptions();

        initInterceptors();

        initHanders();

        initInject();
        return true;
    }


    @Override
    public boolean start() {
        resolveMethod(Const.PLUGIN_START, null);
        return true;
    }

    @Override
    public boolean stop() {
        resolveMethod(Const.PLUGIN_STOP, null);
        return true;
    }


    public static Map<Class<? extends Annotation>, Set<Class>> getClassesMap() {
        return classesMap;
    }

    public static void setClassesMap(Map<Class<? extends Annotation>, Set<Class>> classesMap) {
        CustomPlugin.classesMap = classesMap;
    }

    public static Class<?> getClazz() {
        return clazz;
    }

    public static void setClazz(Class<?> clazz) {
        CustomPlugin.clazz = clazz;
    }


    public void init(Routes me) {
        routes.forEach(route -> {
            if (StringTool.equalEmpty(route.getViewPath()))
                me.add(route.getControllerKey(), route.getControllerClass());
            else
                me.add(route.getControllerKey(), route.getControllerClass(), route.getViewPath());
        });
        resolveMethod(Const.PLUGIN_INIT_ROUTES, me);
    }


    public void init(Plugins me) {
        plugins.forEach(me::add);
        resolveMethod(Const.PLUGIN_INIT_PLUGINS, me);
    }

    public void init(Constants me) {
        resolveMethod(Const.PLUGIN_INIT_CONSTANTS, me);
    }

    public void init(Interceptors me) {
        interceptors.forEach(me::add);
        resolveMethod(Const.PLUGIN_INIT_INTERCEPTOR, me);
    }

    public void init(Handlers me) {
        handlers.forEach(me::add);
        resolveMethod(Const.PLUGIN_INIT_HANDLER, me);
    }

    private void initInject() {
        //Inject with @Inject Service
        Set<Class> Classes = classesMap.get(Service.class);
        Classes.forEach(service -> {
            Field field = ReflectTool.getFieldByClass(service, Const.INJECT_SERVICE);
            if (field != null) {

                field.setAccessible(true);
                try {
                    field.set(service, CustomInterceptor.Proxy(service));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initRoutes() {
        Set<Class> Classes = classesMap.get(Controller.class);
        Set<String> excludedMethodName = ReflectTool.buildExcludedMethodName(com.jfinal.core.Controller.class, BaseController.class);
        //the Order is important
        Class<? extends Annotation>[] methodRequiredAnnotations = new Class[]{RequestMethod.class, RequestHeader.class
                , ValidateToken.class, RequiresAuthentication.class, RequiresPermissions.class, RequiresRoles.class, RequiresUser.class,
                RequiresGuest.class, ValidateComVars.class, ValidateComVar.class, ValidateParams.class, ValidateParam.class};
        Class<? extends Annotation>[] contrllerRequiredAnnotations = new Class[]{RequestMethod.class, RequestHeader.class
                , ValidateToken.class, RequiresAuthentication.class, RequiresPermissions.class, RequiresRoles.class, RequiresUser.class,
                RequiresGuest.class};
        Class<? extends Annotation>[] afterMethodRequiredAnnotations = new Class[]{ResponseStatus.class, SetComVar.class, SetUIDatas.class, SetUIData.class};
        Classes.forEach(controller -> {
            Controller controllerBind = (Controller) controller.getAnnotation(Controller.class);
            if (controllerBind != null && BaseController.class.isAssignableFrom(controller)) {

                List<Annotation> controllerAns = buildAnnotation(controller, contrllerRequiredAnnotations);

                String controllerKey = controllerBind.value();
                String controllerView = controllerBind.views();
                if (controllerKey.equals("")) {
                    LOGGER.error(controller.getName() + "Path must not be empty");
                    return;
                }
                routes.add(new Route(controllerKey, controller, controllerView));

                Method[] methods = controller.getMethods();
                for (Method method : methods) {
                    if (!excludedMethodName.contains(method.getName())
                            && method.getParameterTypes().length == 0) {
                        if (method.getAnnotation(ClearShiro.class) != null) {
                            continue;
                        }
                        String actionKey = createActionKey(controller, method, controllerKey);
                        List<Annotation> methodAns = buildAnnotation(method, methodRequiredAnnotations);
                        methodAns.removeAll(controllerAns);
                        methodAns.addAll(controllerAns);
                        methodAnnotationsHandler.put(actionKey, methodAns);

                        afterMethodAnnoHandler.put(actionKey, buildAnnotation(method, afterMethodRequiredAnnotations));
                    }
                }
                LOGGER.debug("Controller Registered : controller = " + controller + ", Mapping URL = " + controllerKey);
            }
        });

    }

    private List<Annotation> buildAnnotation(Class clz, Class<? extends Annotation>[] requiredAnnotations) {
        List<Annotation> annotations = new ArrayList<>();
        for (Class<? extends Annotation> an : requiredAnnotations) {
            Annotation annotation = clz.getAnnotation(an);
            if (annotation != null && !annotations.contains(annotation)) {
                annotations.add(annotation);
            }
        }
        return annotations;
    }

    private List<Annotation> buildAnnotation(Method method, Class<? extends Annotation>[] requiredAnnotations) {
        List<Annotation> annotations = new ArrayList<>();
        for (Class<? extends Annotation> an : requiredAnnotations) {
            Annotation annotation = method.getAnnotation(an);
            if (annotation != null && !annotations.contains(annotation)) {
                annotations.add(annotation);
            }
        }
        return annotations;
    }

    private String createActionKey(Class<? extends BaseController> controllerClass,
                                   Method method, String controllerKey) {
        String methodName = method.getName();
        String actionKey = "";

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
                        arp.addMapping(Const.DEFAULT_NONE.equals(modelBind.value()) ? modelClass.getName().toLowerCase() : modelBind.value(), StringTool.join(modelBind.pk(), ","), modelClass);

                        Field field = modelClass.getField(Const.DB_DAO);

                        if (field != null && !Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                            field.setAccessible(true);
                            field.set(null, CustomInterceptor.Proxy(field.getType()));
                        }
                    }
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            arp.setShowSql(PropPlugin.getBool(Dict.DEV_MODE, false));
            arp.setDialect(new MysqlDialect());
            arp.setContainerFactory(new CaseInsensitiveContainerFactory(false));
            plugins.add(druidPlugin);
            plugins.add(arp);
        }


        //Init Cache
        if (PropPlugin.getBool(Dict.CACHE_USE, true)) {
            plugins.add(new EhCachePlugin(PropPlugin.getValue(Dict.CACHE_CONFIG, Const.CONFIG_CACHE_FILE)));
        }
        //Init Shiro
        plugins.add(new ShiroPlugin());

        //Init Other Plugin By @PluginHandler
        Classes = classesMap.get(PluginHandler.class);
        for (Class<?> plugin : Classes) {
            try {
                if (com.teddy.jfinal.interfaces.IPlugin.class.isAssignableFrom(plugin)) {
                    //resolve Class implements custom IPlugin
                    aopHandler.get(Const.PLUGIN_INIT_PLUGINS).add(plugin.getMethod(Const.INIT, Plugins.class));
                    aopHandler.get(Const.PLUGIN_INIT_ROUTES).add(plugin.getMethod(Const.INIT, Routes.class));
                    aopHandler.get(Const.PLUGIN_INIT_CONSTANTS).add(plugin.getMethod(Const.INIT, Constants.class));
                    aopHandler.get(Const.PLUGIN_START).add(ReflectTool.getMethodByClassAndName(plugin, Const.START));
                    aopHandler.get(Const.PLUGIN_STOP).add(ReflectTool.getMethodByClassAndName(plugin, Const.STOP));
                } else {
                    //init jfinal plugins
                    plugins.add((IPlugin) plugin.newInstance());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //Init CORE plugin
        plugins.add(new CustomPlugin());

    }

    private void initExceptions() {

        //Init Exception annotation
        Set<Class> Classes = classesMap.get(ExceptionHandlers.class);
        for (Class exceptionClass : Classes) {

            Method[] methods = exceptionClass.getDeclaredMethods();
            for (Method method : methods) {
                //if named be before* after* should be handled.
                switch (method.getName()) {
                    case Const.BEFORE_EXCEPTION:
                        aopHandler.get(Const.BEFORE_EXCEPTION).add(method);
                        break;
                    case Const.AFTER_EXCEPTION:
                        aopHandler.get(Const.AFTER_EXCEPTION).add(method);
                        break;
                    default:
                        // resolve @ExceptionHandler add method into ExceptionMap
                        if (method.isAnnotationPresent(ExceptionHandler.class)) {
                            for (Class<? extends Exception> exception : method.getAnnotation(ExceptionHandler.class).value()) {
                                exceptionsMap.put(exception, method);
                            }
                        }
                }
            }
        }

    }

    private void initInterceptors() {
        Set<Class> Classes = classesMap.get(InterceptorHandler.class);
        try {
            for (Class interceptor : Classes) {
                if (com.teddy.jfinal.interfaces.Interceptor.class.isAssignableFrom(interceptor)) {
                    //custom Interceptor
                    aopHandler.get(Const.BEFORE_EXCEPTION).add(ReflectTool.getMethodByClassAndName(interceptor, Const.BEFORE_EXCEPTION));
                    aopHandler.get(Const.BEFORE_INTERCEPT).add(ReflectTool.getMethodByClassAndName(interceptor, Const.BEFORE_INTERCEPT));
                    aopHandler.get(Const.AFTER_EXCEPTION).add(ReflectTool.getMethodByClassAndName(interceptor, Const.AFTER_EXCEPTION));
                    aopHandler.get(Const.AFTER_INTERCEPT).add(ReflectTool.getMethodByClassAndName(interceptor, Const.AFTER_INTERCEPT));
                } else {
                    //Jfinal Interceptor
                    interceptors.add((Interceptor) interceptor.newInstance());
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Lc4eException e) {
            e.printStackTrace();
        }

        //Init Core Interceptor
        interceptors.add(new GlobalInterceptor());
    }

    private void initHanders() {
        Set<Class> Classes = null;
        Classes = classesMap.get(InterceptorHandler.class);
        try {
            for (Class<?> handler : Classes) {
                if (Handler.class.isAssignableFrom(handler)) {
                    //custom handler with @GlobalHandler
                    aopHandler.get(Const.BEFORE_HANDLER).add(ReflectTool.getMethodByClassAndName(handler, Const.BEFORE_HANDLER));
                    aopHandler.get(Const.AFTER_HANDLER).add(ReflectTool.getMethodByClassAndName(handler, Const.AFTER_HANDLER));
                } else {
                    //Jfinal handler
                    handlers.add((com.jfinal.handler.Handler) handler.newInstance());
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Lc4eException e) {
            e.printStackTrace();
        }

        //Init Core Handler
        handlers.add(new com.teddy.jfinal.handler.GlobalHandler());

    }

    private void resolveMethod(String name, Object me) {
        boolean result = false;
        try {
            for (Method method : aopHandler.get(name)) {
                if (me == null)
                    result = (boolean) method.invoke(method.getDeclaringClass().newInstance());
                else
                    result = (boolean) method.invoke(method.getDeclaringClass().newInstance(), me);
                if (!result) {
                    throw new Lc4eException("Start plugin [" + method.getDeclaringClass().getName() + "] failed");
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Lc4eException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Set<Method>> getAopHandler() {
        return aopHandler;
    }

    public static Map<Class<? extends Exception>, Method> getExceptionsMap() {
        return exceptionsMap;
    }


    public static Map<String, List<Annotation>> getMethodAnnotationsHandler() {
        return methodAnnotationsHandler;
    }

    public static Map<String, List<Annotation>> getAfterMethodAnnoHandler() {
        return afterMethodAnnoHandler;
    }
}
