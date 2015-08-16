package com.teddy.jfinal.handler.support;

import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.teddy.jfinal.annotation.*;
import com.teddy.jfinal.exceptions.AutoSetterException;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.exceptions.ValidateException;
import com.teddy.jfinal.handler.CustomInterceptor;
import com.teddy.jfinal.plugin.CustomPlugin;
import com.teddy.jfinal.tools.WebTool;
import com.teddy.lc4e.core.entity.Message;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.util.List;

/**
 * Created by teddy on 2015/7/20.
 */
public class GlobalInterceptorKit {

    private static final Logger LOGGER = Logger.getLogger(GlobalInterceptorKit.class);

    private GlobalInterceptorKit() {
    }

    public static Object[] resolveParameters(Class<?>[] args, Invocation ai, Exception e) throws Lc4eException {
        Object[] params = new Object[args.length];
        for (int i = 0, len = args.length; i < len; i++) {
            Class<?> type = args[i];
            if (Invocation.class == type) {
                params[i] = ai;
            } else if (Exception.class.isAssignableFrom(type)) {
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

    public static void ExceptionHandle(Invocation ai, Exception e) throws ValidateException, InvocationTargetException, Lc4eException, IllegalAccessException, ParseException, NoSuchMethodException, NoSuchFieldException, InstantiationException {
        Method method = CustomPlugin.getExceptionsMap().get(e.getClass());
        if (method == null) {
            resolve(ai, e);
        } else {
            ValidateKit.resolve(CustomPlugin.getExceptionMethodHandler().get(method), ai);
            method.invoke(Modifier.isStatic(method.getModifiers()) ? null : method.getDeclaringClass().newInstance(), resolveParameters(method.getParameterTypes(), ai, e));
        }
    }

    private static void resolve(Invocation ai, Exception e) {
        if (WebTool.isAJAX(ai.getController().getRequest())) {
            ai.getController().renderJson(new Message(e.getMessage() == null ? e.toString() : e.getMessage()));
        } else {
            ai.getController().render("pages/exception");
        }

    }


    public static void handleAOPMethods(Invocation ai, String name) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        for (Method method : CustomPlugin.getAopHandler().get(name)) {
            method.invoke(method.getDeclaringClass().newInstance(), ai);
        }
    }

    /**
     * resolve annotation on controller method
     * like RequestMethod ,ResponseStatus,RequestHeader
     * set value in resolve
     *
     * @param ai
     * @throws Lc4eException
     */
    public static void handleAnnotationsOnControllerMethod(Invocation ai, List<Annotation> ans) throws InvocationTargetException, NoSuchMethodException, ValidateException, IllegalAccessException, NoSuchFieldException, Lc4eException, ParseException, ClassNotFoundException {
        ValidateKit.resolve(ans, ai);
    }


    public static void handleInject(Invocation ai) throws IllegalAccessException {
        Controller controller = ai.getController();
        Inject(controller, controller.getClass());
    }

    public static void Inject(Object obj, Class clz) {
        Field[] fields = clz.getFields();
        try {
            for (int i = 0, len = fields.length; i < len; i++) {
                Class clzss = fields[i].getType();
                if (clzss.isAnnotationPresent(Service.class) && fields[i].isAnnotationPresent(Inject.class) && !Modifier.isStatic(fields[i].getModifiers())) {
                    fields[i].setAccessible(true);
                    if (fields[i].get(obj) == null) {
                        fields[i].set(obj, CustomInterceptor.Proxy(clzss));
                    }
                }
            }
        } catch (IllegalAccessException e) {
            LOGGER.error("Inject error in Class [" + clz.getName() + "]");
            e.printStackTrace();
        }
    }

    public static void handleOtherAnnotataion(Invocation ai, List<Annotation> annotations) throws AutoSetterException, Lc4eException {
        for (Annotation annotation : annotations) {
            if (annotation instanceof ResponseStatus) {
                if (annotation != null) {
                    ai.getController().getResponse().setStatus(((ResponseStatus) annotation).value().toInteger());
                }
            } else if (annotation instanceof SetComVar) {
                AttributeKit.setComVar((SetComVar) annotation, ai);
            } else if (annotation instanceof SetComVars) {
                AttributeKit.setComVars((SetComVars) annotation, ai);
            } else if (annotation instanceof SetUIDatas) {
                AttributeKit.setUIDatas((SetUIDatas) annotation, ai);
            } else if (annotation instanceof SetUIData) {
                AttributeKit.setUIData((SetUIData) annotation, ai);
            } else if (annotation instanceof SetAJAX) {
                AttributeKit.setAJAX((SetAJAX) annotation, ai);
            }
        }
    }

}
