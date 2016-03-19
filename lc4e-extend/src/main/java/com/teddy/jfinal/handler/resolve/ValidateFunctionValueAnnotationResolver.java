package com.teddy.jfinal.handler.resolve;

import com.teddy.jfinal.annotation.ValidateComVar;
import com.teddy.jfinal.annotation.ValidateFunctionValue;
import com.teddy.jfinal.exceptions.Lc4eApplicationException;
import com.teddy.jfinal.exceptions.Lc4eAutoSetterException;
import com.teddy.jfinal.exceptions.Lc4eValidateException;
import com.teddy.jfinal.interfaces.CustomAnnotationPlugin;
import com.teddy.jfinal.plugin.CustomAnnotationResolve.AnnotationPluginResolver;
import com.teddy.jfinal.plugin.CustomPlugin;
import com.teddy.jfinal.tools.ReflectTool;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by teddyzhu on 16/1/29.
 */
public class ValidateFunctionValueAnnotationResolver extends CustomAnnotationPlugin {
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return ValidateFunctionValue.class;
    }

    @Override
    public Object intercept(Annotation annotation, AnnotationPluginResolver resolver, Object[] objects, Object target, Method method, boolean[] isHandled) throws Throwable {

        ValidateFunctionValue an = (ValidateFunctionValue) annotation;

        Object returnValue;
        Method targetMethod = an.isDeclared() ? ReflectTool.getDeclaredMethodByClassAndName(an.targetClass(), an.methodName()) : ReflectTool.getMethodByClassAndName(an.targetClass(), an.methodName());
        if (method == null) {
            throw new Lc4eApplicationException("The method [" + an.methodName() + "] can not found in Class [" + an.targetClass().getName() + "]");
        }
        try {
            Object obj = CustomPlugin.getInjectObjs().containsKey(an.targetClass()) ? CustomPlugin.getInjectObjs().get(an.targetClass()) : an.targetClass().newInstance();
            returnValue = targetMethod.getParameterCount() > 0 ? targetMethod.invoke(obj, target) : targetMethod.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Lc4eAutoSetterException("Call method [" + an.methodName() + "] failed");
        }
        ValidateComVar comVar = an.value();
        Class type = ReflectTool.wrapper(comVar.type());
        if (comVar.validateEqual()) {
            if (!returnValue.equals(ReflectTool.wrapperObject(type, comVar.value()))) {
                throw new Lc4eValidateException(an.errorInfo());
            }
        } else {
            if (returnValue.equals(ReflectTool.wrapperObject(type, comVar.value()))) {
                throw new Lc4eValidateException(an.errorInfo());
            }
        }
        return null;
    }
}
