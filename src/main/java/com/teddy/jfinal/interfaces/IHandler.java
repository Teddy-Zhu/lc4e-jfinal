package com.teddy.jfinal.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by teddy on 2015/7/21.
 */
public interface IHandler {
    void beforeHandler(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled);

    void afterHandler(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled);
}
