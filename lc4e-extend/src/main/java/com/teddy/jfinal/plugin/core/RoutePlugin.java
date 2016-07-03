package com.teddy.jfinal.plugin.core;

import com.jfinal.config.*;
import com.jfinal.core.ActionKey;
import com.teddy.jfinal.annotation.Controller;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.entity.Route;
import com.teddy.jfinal.interfaces.BaseController;
import com.teddy.jfinal.interfaces.IPlugin;
import com.teddy.jfinal.plugin.CustomPlugin;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.jfinal.tools.StringTool;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by teddyzhu on 16/6/6.
 */
public class RoutePlugin implements IPlugin {

    private static final Logger LOGGER = Logger.getLogger(RoutePlugin.class);

    private static List<Route> routes = new ArrayList<>();

    private static Set<String> actionKeys = new HashSet<>();

    @Override
    public void init(Plugins me) {

    }

    @Override
    public void init(Routes me) {
        routes.forEach(route -> {
            if (StringTool.equalEmpty(route.getViewPath()))
                me.add(route.getControllerKey(), route.getControllerClass());
            else
                me.add(route.getControllerKey(), route.getControllerClass(), route.getViewPath());
        });
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
        Set<String> excludedMethodName = ReflectTool.buildExcludedMethodName(com.jfinal.core.Controller.class, BaseController.class);
        configPlugin.getAnnotationClass(Controller.class).forEach(controller -> {
            Controller controllerBind = (Controller) controller.getAnnotation(Controller.class);
            if (controllerBind != null && BaseController.class.isAssignableFrom(controller)) {
                Annotation[] controllerAns = controller.getAnnotations();
                configPlugin.getClassAnnotationMap().put(controller, controllerAns);
                String controllerKey = controllerBind.value();
                String controllerView = controllerBind.views();
                if (controllerKey.equals("") || controllerKey.equals(Const.DEFAULT_NONE)) {
                    controllerKey = controller.getSimpleName();
                }
                routes.add(new Route(controllerKey, controller, controllerView));
                Method[] methods = controller.getMethods();
                for (Method method : methods) {
                    if (!excludedMethodName.contains(method.getName())
                            && method.getParameterTypes().length == 0) {
                        String actionKey = createActionKey(controller, method, controllerKey);
                        Annotation[] methodAns = method.getAnnotations();
                        actionKeys.add(actionKey);
                        configPlugin.getMethodAnnotationMap().put(method, methodAns);
                    }
                }
                LOGGER.debug("Controller Registered : controller = " + controller + ", Mapping URL = " + controllerKey);
            }
        });
        return true;
    }

    @Override
    public boolean stop(CustomPlugin configPlugin) {
        return true;
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

}
