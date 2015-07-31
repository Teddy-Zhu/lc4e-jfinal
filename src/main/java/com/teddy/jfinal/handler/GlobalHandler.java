package com.teddy.jfinal.handler;

import com.jfinal.handler.Handler;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.handler.support.GlobalHandlerKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by teddy on 2015/7/22.
 */
public class GlobalHandler extends Handler {


    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {

        try {
            GlobalHandlerKit.handleAOPMethods(target, request, response, isHandled, Const.BEFORE_HANDLER);
            nextHandler.handle(target, request, response, isHandled);
            GlobalHandlerKit.handleAOPMethods(target, request, response, isHandled, Const.AFTER_HANDLER);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
