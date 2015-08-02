package com.teddy.jfinal.handler.support;

import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.teddy.jfinal.Exceptions.Lc4eException;
import com.teddy.jfinal.Exceptions.ValidateException;
import com.teddy.jfinal.annotation.*;
import com.teddy.jfinal.handler.CustomInterceptor;
import com.teddy.jfinal.plugin.CustomPlugin;
import com.teddy.jfinal.tools.ReflectTool;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;

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

    public static void ExceptionHandle(Invocation ai, Exception e) {
        Method method = CustomPlugin.getExceptionsMap().get(e.getClass());
        if (method == null) {
            resolve(ai, e);
        } else {
            ResponseStatus status = method.getAnnotation(ResponseStatus.class);
            if (status != null)
                ai.getController().getResponse().setStatus(status.value().toInteger());
            try {
                method.invoke(Modifier.isStatic(method.getModifiers()) ? null : method.getDeclaringClass().newInstance(), resolveParameters(method.getParameterTypes(), ai, e));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private static void resolve(Invocation ai, Exception e) {
        ai.getController().renderText(e.getMessage());
    }


    public static void handleAOPMethods(Invocation ai, String name) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        for (Method method : CustomPlugin.getAopHandler().get(name)) {
            method.invoke(method.getDeclaringClass().newInstance(), ai);
        }
    }

    private static void handleRequiredCondition(Invocation ai) throws Lc4eException, ValidateException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, ParseException {
        Method controllerMethod = ai.getMethod();
        HttpServletRequest request = ai.getController().getRequest();
        Annotation annotation = null;
        //resovle require header or method
        annotation = ReflectTool.getAnnotationByMethod(controllerMethod, RequestMethod.class);
        if (annotation != null && !((RequestMethod) annotation).value().equals(request.getMethod())) {
            // controller.renderError(404);
            throw new Lc4eException("404");
        }
        annotation = controllerMethod.getAnnotation(RequestHeader.class);
        ValidateKit.resolveRequestHeader((RequestHeader) annotation, ai);
        annotation = controllerMethod.getAnnotation(ValidateToken.class);
        ValidateKit.resolveToken((ValidateToken) annotation, ai);
        annotation = controllerMethod.getAnnotation(ValidateComVars.class);
        ValidateKit.resolveComVars((ValidateComVars) annotation, ai);
        annotation = controllerMethod.getAnnotation(ValidateComVar.class);
        ValidateKit.resolveComVar((ValidateComVar) annotation, ai);
        annotation = controllerMethod.getAnnotation(ValidateParams.class);
        ValidateKit.resolveParameters((ValidateParams) annotation, ai);
        annotation = controllerMethod.getAnnotation(ValidateParam.class);
        ValidateKit.resolveParameter((ValidateParam) annotation, ai);
        return;
    }

    /**
     * resolve annotation on controller method
     * like RequestMethod ,ResponseStatus,RequestHeader
     * set value in resolve
     *
     * @param ai
     * @throws Lc4eException
     */
    public static void handleAnnotationsOnControllerMethod(Invocation ai) throws InvocationTargetException, NoSuchMethodException, ValidateException, IllegalAccessException, NoSuchFieldException, Lc4eException, ParseException {
        handleRequiredCondition(ai);
    }


    public static void handleInject(Invocation ai) throws IllegalAccessException {
        Controller controller = ai.getController();

        Field[] fields = controller.getClass().getFields();
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


}
