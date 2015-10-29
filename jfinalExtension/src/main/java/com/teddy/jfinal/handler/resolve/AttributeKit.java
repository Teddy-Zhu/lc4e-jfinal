package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.jfinal.log.Logger;
import com.teddy.jfinal.annotation.*;
import com.teddy.jfinal.exceptions.Lc4eAutoSetterException;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.tools.CustomTool;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.jfinal.tools.WebTool;

import java.lang.reflect.Method;

import static com.jfinal.log.Logger.getLogger;

/**
 * Created by teddy on 2015/8/2.
 */
public class AttributeKit {

    private static final Logger log = getLogger(AttributeKit.class);

    public static void setUIDatas(SetUIDatas uiDatas, Invocation ai) throws Lc4eException, Lc4eAutoSetterException {
        if (uiDatas == null) {
            return;
        }
        for (int i = 0, len = uiDatas.value().length; i < len; i++) {
            setUIData(uiDatas.value()[i], ai);
        }

    }

    public static void setUIData(SetUIData uiData, Invocation ai) throws Lc4eException, Lc4eAutoSetterException {
        if (uiData == null) {
            return;
        }
        Object returnValue;
        Method method = ReflectTool.getMethodByClassAndName(uiData.methodClass(), uiData.methodName());
        if (method == null) {
            throw new Lc4eAutoSetterException("The method [" + uiData.methodName() + "] can not found in Class [" + uiData.methodClass().toString() + "]");
        }
        try {
            Object obj = uiData.methodClass().newInstance();
            if (method.getParameterCount() > 0) {
                returnValue = method.invoke(uiData.methodClass().isAnnotationPresent(Service.class) ? CustomTool.custom(obj) : obj, ai);
            } else {
                returnValue = method.invoke(uiData.methodClass().isAnnotationPresent(Service.class) ? CustomTool.custom(obj) : obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Lc4eAutoSetterException("Call method [" + uiData.methodName() + "] failed");
        }
        if (returnValue != null) {
            ai.getController().setAttr(uiData.attrName(), returnValue);
        } else {
            throw new Lc4eAutoSetterException("Auto Set Attribute [" + uiData.attrName() + "] failed!");
        }
    }


    public static void setPJAX(SetPJAX setPJAX, Invocation ai) {
        if (setPJAX == null) {
            return;
        }
        ai.getController().setAttr(setPJAX.value(), WebTool.isPJAX(ai.getController().getRequest()));
    }

    public static void setAJAX(SetAJAX setAJAX, Invocation ai) {
        if (setAJAX == null) {
            return;
        }
        ai.getController().setAttr(setAJAX.value(), WebTool.isAJAX(ai.getController().getRequest()));
    }
}
