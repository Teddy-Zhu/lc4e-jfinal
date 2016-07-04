package com.teddy.jfinal.handler;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by teddyzhu on 16/6/29.
 */
public class CacheControlHandler extends Handler {
    private static final long MAX_AGE = 2764800L;
    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        long now = 0L;
        if (target.indexOf(".") > 0) {
            long ims = request.getDateHeader("If-Modified-Since");
            now = System.currentTimeMillis();
            if (ims + MAX_AGE > now) {
                isHandled[0] = true;
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                return;
            }
            response.setHeader("Cache-Control", "max-age=" + MAX_AGE);
            response.addDateHeader("Expires", now + MAX_AGE * 1000);
            response.addDateHeader("Last-Modified", now);
        }
        next.handle(target, request, response, isHandled);

    }
}
