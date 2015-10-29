package com.teddy.jfinal.handler.httpCache;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by teddy on 2015/9/25.
 */
public class HttpCacheHandler extends Handler{
    private static final long MAX_AGE = 2764800L;

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {

        // 最后修改时间
        long ims = request.getDateHeader("If-Modified-Since");
        long now = System.currentTimeMillis();
        // 如果header头没有过期
        if(ims + MAX_AGE > now){
            isHandled[0] = true;
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return;
        }
        nextHandler.handle(target, request, response, isHandled);

        if(target.indexOf(".") > 0){
            response.setHeader("Cache-Control", "max-age=" + MAX_AGE);
            response.addDateHeader("Expires", now + MAX_AGE);
            response.addDateHeader("Last-Modified", now);
        }
    }
}
