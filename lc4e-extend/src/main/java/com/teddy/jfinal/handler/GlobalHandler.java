package com.teddy.jfinal.handler;

import com.jfinal.handler.Handler;
import com.teddy.jfinal.interfaces.IHandler;
import com.teddy.jfinal.plugin.core.HandlePlugin;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by teddy on 2015/7/22.
 */
public class GlobalHandler extends Handler {

    private static final Logger logger = Logger.getLogger(GlobalHandler.class);

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        resolveBefore(target, request, response, isHandled);
        next.handle(target, request, response, isHandled);
        resolveAfter(target, request, response, isHandled);
    }

    private void resolveBefore(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        for (IHandler handler : HandlePlugin.getLc4eHandler()) {
            handler.beforeHandler(target, request, response, isHandled);
        }
    }

    private void resolveAfter(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        for (IHandler handler : HandlePlugin.getLc4eHandler()) {
            handler.afterHandler(target, request, response, isHandled);
        }
    }
}
