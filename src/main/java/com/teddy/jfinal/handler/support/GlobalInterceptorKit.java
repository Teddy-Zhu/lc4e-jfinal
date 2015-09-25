package com.teddy.jfinal.handler.support;

import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.teddy.jfinal.annotation.Inject;
import com.teddy.jfinal.annotation.Service;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.handler.CustomInterceptor;
import com.teddy.jfinal.interfaces.AnnotationResolver;
import com.teddy.jfinal.plugin.CustomPlugin;
import com.teddy.jfinal.tools.WebTool;
import com.teddy.lc4e.core.entity.Message;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Created by teddy on 2015/7/20.
 */
public class GlobalInterceptorKit {

    private static final Logger LOGGER = Logger.getLogger(GlobalInterceptorKit.class);

    private GlobalInterceptorKit() {
    }

    public static Object[] resolveParameters(Class<?>[] args, Invocation ai, Throwable e) throws Lc4eException {
        Object[] params = new Object[args.length];
        for (int i = 0, len = args.length; i < len; i++) {
            Class<?> type = args[i];
            if (Invocation.class == type) {
                params[i] = ai;
            } else if (Throwable.class.isAssignableFrom(type)) {
                params[i] = e;
            } else if (type == Controller.class) {
                params[i] = ai.getController();
            } else if (type == HttpServletRequest.class) {
                params[i] = ai.getController().getRequest();
            } else if (type == HttpServletResponse.class) {
                params[i] = ai.getController().getResponse();
            } else {
                throw new Lc4eException("No Mapping Parameters");
            }
        }
        return params;
    }

    public static void ExceptionHandle(Invocation ai, Throwable e) throws Exception {
        Method method = CustomPlugin.getExceptionsMap().get(e.getClass());
        if (method == null) {
            resolve(ai, e);
        } else {
            resolveAOPResolver(ai, CustomPlugin.getExceptionMethodHandler().get(method));
            method.invoke(Modifier.isStatic(method.getModifiers()) ? null : method.getDeclaringClass().newInstance(), resolveParameters(method.getParameterTypes(), ai, e));
        }
    }

    private static void resolve(Invocation ai, Throwable e) {
        if (WebTool.isAJAX(ai.getController().getRequest())) {
            ai.getController().renderJson(new Message(e.getMessage() == null ? e.toString() : e.getMessage()));
        } else {
            ai.getController().render("/pages/exception");
        }

    }


    public static void handleAOPMethods(Invocation ai, String name) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        for (Method method : CustomPlugin.getAopHandler().get(name)) {
            method.invoke(method.getDeclaringClass().newInstance(), ai);
        }
    }

    public static void resolveAOPResolver(Invocation ai, List<AnnotationResolver> ans) throws Exception {
        for (int i = 0, len = ans.size(); i < len; i++) {
            ans.get(i).resolve(ai);
        }
    }


    public static void handleInject(Invocation ai) throws IllegalAccessException {
        Controller controller = ai.getController();
        Inject(controller, controller.getClass());
    }

    public static void Inject(Object obj, Class clz) {
        Field[] fields = clz.getFields();
        try {
            for (Field field : fields) {
                Class clzss = field.getType();
                if (clzss.isAnnotationPresent(Service.class) && field.isAnnotationPresent(Inject.class) && !Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    if (field.get(obj) == null) {
                        field.set(obj, CustomInterceptor.Proxy(clzss));
                    }
                }
            }
        } catch (IllegalAccessException e) {
            LOGGER.error("Inject error in Class [" + clz.getName() + "]");
            e.printStackTrace();
        }
    }

}
