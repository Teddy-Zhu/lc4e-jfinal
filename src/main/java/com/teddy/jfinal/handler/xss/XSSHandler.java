package com.teddy.jfinal.handler.xss;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by teddy on 2015/9/23.
 */
public class XSSHandler extends Handler {

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        request = new XSSHttpServletRequestWrapper(request);
        nextHandler.handle(target, request, response, isHandled);
    }
}
