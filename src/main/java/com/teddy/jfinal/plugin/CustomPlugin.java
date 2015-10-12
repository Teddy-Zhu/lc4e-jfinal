package com.teddy.jfinal.plugin;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.aop.Interceptor;
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
import com.teddy.jfinal.handler.httpCache.HttpCacheHandler;
import com.teddy.jfinal.handler.resolve.*;
import com.teddy.jfinal.handler.xss.XSSHandler;
import com.teddy.jfinal.interfaces.*;
import com.teddy.jfinal.tools.ClassSearcherTool;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.jfinal.tools.StringTool;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.*;

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

    private static Map<Method, List<AnnotationResolver>> exceptionMethodHandler;

    private static Map<String, Set<Method>> aopHandler;

    private static Map<String, List<Lc4ePlugin>> pluginAOPHandler;

    private static List<IHandler> pluginIhanders;

    private static List<IInterceptor> pluginIinterceptors;

    private static List<com.teddy.jfinal.interfaces.IPlugin> pluginIplugins;

    private static Class<?> clazz;

    private static List<Route> routes;

    private static List<IPlugin> plugins;

    private static List<Interceptor> interceptors;

    private static List<com.jfinal.handler.Handler> handlers;

    private static Set<String> actionKeys;

    //should release
    private static Map<Class<? extends Annotation>, Function<Annotation, AnnotationResolver>> annotationFunctionMap;

    static {
        annotationFunctionMap = new HashMap<>();
        annotationFunctionMap.put(RequestMethod.class, (x) -> new RequestMethodResolver((RequestMethod) x));
        annotationFunctionMap.put(RequestHeader.class, (x) -> new RequestHeaderResolver((RequestHeader) x));
        annotationFunctionMap.put(ValidateToken.class, (x) -> new ValidateTokenResolver((ValidateToken) x));
        annotationFunctionMap.put(RequiresAuthentication.class, (x) -> new RequiresAuthenticationResolver((RequiresAuthentication) x));
        annotationFunctionMap.put(RequiresUser.class, (x) -> new RequiresUserResolver((RequiresUser) x));
        annotationFunctionMap.put(RequiresGuest.class, (x) -> new RequiresGuestResolver((RequiresGuest) x));
        annotationFunctionMap.put(RequiresRoles.class, (x) -> new RequiresRolesResolver((RequiresRoles) x));
        annotationFunctionMap.put(RequiresPermissions.class, (x) -> new RequiresPermissionsResovler((RequiresPermissions) x));
        annotationFunctionMap.put(ValidateComVars.class, (x) -> new ValidateComVarsResolver((ValidateComVars) x));
        annotationFunctionMap.put(ValidateComVar.class, (x) -> new ValidateComVarResolver((ValidateComVar) x));
        annotationFunctionMap.put(ValidateParams.class, (x) -> new ValidateParamsResolver((ValidateParams) x));
        annotationFunctionMap.put(ValidateParam.class, (x) -> new ValidateParamResolver((ValidateParam) x));
        annotationFunctionMap.put(ResponseStatus.class, (x) -> new ResponseStatusResolver((ResponseStatus) x));
        annotationFunctionMap.put(SetComVar.class, (x) -> new SetComVarResolver((SetComVar) x));
        annotationFunctionMap.put(SetComVars.class, (x) -> new SetComVarsResolver((SetComVars) x));
        annotationFunctionMap.put(SetUIDatas.class, (x) -> new SetUIDatasResolver((SetUIDatas) x));
        annotationFunctionMap.put(SetUIData.class, (x) -> new SetUIDataResolver((SetUIData) x));
        annotationFunctionMap.put(SetAJAX.class, (x) -> new SetAJAXResolver((SetAJAX) x));
        annotationFunctionMap.put(SetPJAX.class, (x) -> new SetPJAXResolver((SetPJAX) x));

    }

    //the Order is important
    private Class[] methodRequiredAnnotations = new Class[]{RequestMethod.class, RequestHeader.class
            , ValidateToken.class, RequiresAuthentication.class, RequiresPermissions.class, RequiresRoles.class, RequiresUser.class,
            RequiresGuest.class, ValidateComVars.class, ValidateComVar.class, ValidateParams.class, ValidateParam.class};
    private Class[] contrllerRequiredAnnotations = new Class[]{RequestMethod.class, RequestHeader.class
            , ValidateToken.class, RequiresAuthentication.class, RequiresPermissions.class, RequiresRoles.class, RequiresUser.class,
            RequiresGuest.class};
    private Class[] afterMethodRequiredAnnotations = new Class[]{ResponseStatus.class, SetComVars.class, SetComVar.class, SetUIDatas.class, SetUIData.class,
            SetAJAX.class, SetPJAX.class};


    public CustomPlugin() {
    }

    public boolean init(Properties properties) throws Lc4eException, InstantiationException, NoSuchFieldException, IllegalAccessException {
        new PropPlugin(properties).start();
        exceptionsMap = new HashMap<>();

        routes = new ArrayList<>();
        plugins = new ArrayList<>();
        interceptors = new ArrayList<>();
        handlers = new ArrayList<>();
        exceptionMethodHandler = new HashMap<>();
        pluginAOPHandler = new HashMap<>();
        actionKeys = new HashSet<>();
        pluginIhanders = new ArrayList<>();
        pluginIinterceptors = new ArrayList<>();
        pluginIplugins = new ArrayList<>();
        List<String> jars = (List<String>) PropPlugin.getObject(Dict.SCAN_JAR);
        Class[] scanClasses = new Class[]{
                Job.class, Service.class, PluginHandler.class, ConfigHandler.class, Controller.class, Model.class, ExceptionHandlers.class, InterceptorHandler.class
        };
        if (jars.size() > 0) {
            classesMap = ClassSearcherTool.of(scanClasses).includeAllJarsInLib(ClassSearcherTool.isValiJar()).injars(jars).search();
        } else {
            classesMap = ClassSearcherTool.of(scanClasses).search();
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
                        actionKeys.add(actionKey);
                        methodAns.removeAll(controllerAns);
                        methodAns.addAll(controllerAns);

                        setResolvers(pluginAOPHandler, buildAnnotationResolver(methodAns), actionKey, true);
                        setResolvers(pluginAOPHandler, buildAnnotationResolver(buildAnnotation(method, afterMethodRequiredAnnotations)), actionKey, false);

                    }
                }
                LOGGER.debug("Controller Registered : controller = " + controller + ", Mapping URL = " + controllerKey);
            }
        });
    }

    private void setResolvers(Map<String, List<Lc4ePlugin>> handler, List<AnnotationResolver> resolvers, String actionKey, boolean isBefore) {
        List<Lc4ePlugin> plugins = handler.get(actionKey);
        if (plugins == null) {
            plugins = new ArrayList<>();
            plugins.add(new AnnotationPlugin());

            handler.put(actionKey, plugins);
        }
        AnnotationPlugin annotationPlugin = null;
        for (Lc4ePlugin lc4ePlugin : plugins) {
            if (lc4ePlugin instanceof AnnotationPlugin) {
                annotationPlugin = (AnnotationPlugin) lc4ePlugin;
                break;
            }
        }
        if (annotationPlugin == null) {
            annotationPlugin = new AnnotationPlugin();
            plugins.add(annotationPlugin);
        }
        List<AnnotationResolver> annotationResolvers = null;
        if (isBefore) {
            annotationResolvers = annotationPlugin.getBeforeAnnotationResolvers();
        } else {
            annotationResolvers = annotationPlugin.getAfterAnnotationResolvers();
        }

        if (annotationResolvers == null) {
            annotationResolvers = new ArrayList<>();
            if (isBefore) {
                annotationPlugin.setBeforeAnnotationResolvers(annotationResolvers);
            } else {
                annotationPlugin.setAfterAnnotationResolvers(annotationResolvers);
            }
        }
        annotationResolvers.addAll(resolvers);

    }

    private List<Annotation> buildAnnotation(Class clz, Class[] requiredAnnotations) {
        List<Annotation> annotations = new ArrayList<>();
        for (Class an : requiredAnnotations) {
            Annotation annotation = clz.getAnnotation(an);
            if (annotation != null && !annotations.contains(annotation)) {
                annotations.add(annotation);
            }
        }
        return annotations;
    }

    private List<AnnotationResolver> buildAnnotationResolver(List<Annotation> annotations) {
        List<AnnotationResolver> resolvers = new ArrayList<>();
        annotations.forEach(annotation -> {
            resolvers.add(convertToResolver(annotation));
        });
        return resolvers;
    }

    public static AnnotationResolver convertToResolver(Annotation annotation) {
        Class clz = annotation.annotationType();
        if (annotationFunctionMap.containsKey(clz)) {
            return annotationFunctionMap.get(clz).apply(annotation);
        } else {
            throw new RuntimeException("Can not find Annotation[ " + clz.getName() + " ]");
        }
    }

    private List<Annotation> buildAnnotation(Method method, Class[] requiredAnnotations) {
        List<Annotation> annotations = new ArrayList<>();
        for (Class an : requiredAnnotations) {
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


        //Init Cache
        if (PropPlugin.getBool(Dict.USE_CACHE, true)) {
            plugins.add(new EhCachePlugin(PropPlugin.getValue(Dict.CACHE_CONFIG, Const.CONFIG_CACHE_FILE)));
        }
        //Init Shiro
        if (PropPlugin.getBool(Dict.USE_SHIRO, true)) {
            plugins.add(new ShiroPlugin());
        }

        //Init Other Plugin By @PluginHandler
        Classes = classesMap.get(PluginHandler.class);
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
        //Init CORE plugin
        plugins.add(new CustomPlugin());

    }

    private void initExceptions() {
        //Init Exception annotation
        Set<Class> Classes = classesMap.get(ExceptionHandlers.class);
        for (Class exceptionClass : Classes) {

            Method[] methods = exceptionClass.getDeclaredMethods();
            for (Method method : methods) {
                // resolve @ExceptionHandler add method into ExceptionMap
                if (method.isAnnotationPresent(ExceptionHandler.class)) {
                    exceptionMethodHandler.put(method, buildAnnotationResolver(buildAnnotation(method, afterMethodRequiredAnnotations)));
                    for (Class<? extends Throwable> exception : method.getAnnotation(ExceptionHandler.class).value()) {
                        exceptionsMap.put(exception, method);
                    }
                }

            }
        }

    }

    private void initInterceptors() {
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

        //Init Core Interceptor
        interceptors.add(new GlobalInterceptor());
    }

    private void initHanders() {
        Set<Class> Classes = classesMap.get(InterceptorHandler.class);
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

        //Init Core Handler
        handlers.add(new com.teddy.jfinal.handler.GlobalHandler());

        if (PropPlugin.getBool(Dict.XSS)) {
            handlers.add(new XSSHandler());
        }

        if (PropPlugin.getBool(Dict.USE_HTTP_CACHE)) {
            handlers.add(new HttpCacheHandler());
        }

    }

    public static Map<Class<? extends Throwable>, Method> getExceptionsMap() {
        return exceptionsMap;
    }

    public static Map<Method, List<AnnotationResolver>> getExceptionMethodHandler() {
        return exceptionMethodHandler;
    }


    public static Map<String, List<Lc4ePlugin>> getPluginAOPHandler() {
        return pluginAOPHandler;
    }

    public static List<IHandler> getPluginIhanders() {
        return pluginIhanders;
    }

    public static List<IInterceptor> getPluginIinterceptors() {
        return pluginIinterceptors;
    }
}
