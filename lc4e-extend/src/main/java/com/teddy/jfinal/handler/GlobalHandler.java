package com.teddy.jfinal.handler;

import com.jfinal.handler.Handler;
import com.teddy.jfinal.handler.gzip.GZIPResponseWrapper;
import com.teddy.jfinal.interfaces.IHandler;
import com.teddy.jfinal.plugin.CustomPlugin;
import com.teddy.jfinal.plugin.ShiroPlugin;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.ExecutionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.subject.WebSubject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by teddy on 2015/7/22.
 */
public class GlobalHandler extends Handler {
    private static final long MAX_AGE = 2764800L;
    private static final Logger logger = Logger.getLogger(GlobalHandler.class);

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        Throwable t = null;
        final Subject subject = createSubject(request, response);
        try {
            subject.execute(() -> {
                resolveBefore(target, request, response, isHandled);
                updateSessionLastAccessTime(request, response);

                long now = 0;
                if (target.indexOf(".") > 0) {
                    // 最后修改时间
                    long ims = request.getDateHeader("If-Modified-Since");
                    now = System.currentTimeMillis();
                    // 如果header头没有过期
                    if (ims + MAX_AGE > now) {
                        isHandled[0] = true;
                        response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                        return null;
                    }
                }
                nextHandler.handle(target, request, response, isHandled);

                if (target.indexOf(".") > 0) {
                    response.setHeader("Cache-Control", "max-age=" + MAX_AGE);
                    response.addDateHeader("Expires", now + MAX_AGE * 1000);
                    response.addDateHeader("Last-Modified", now);
                }

                resolveAfter(target, request, response, isHandled);
                return null;
            });
        } catch (ExecutionException ex) {
            t = ex.getCause();
        } catch (Throwable throwable) {
            t = throwable;
        }

        if (t != null) {
            if (t instanceof Exception) {
                t.printStackTrace();
            }
            //otherwise it's not one of the two exceptions expected by the filter method signature - wrap it in one:
            logger.error("Filtered request failed.");
        }
    }

    private WebSecurityManager getSecurityManager() {
        return ShiroPlugin.defaultWebSecurityManager;
    }

    private boolean isHttpSessions() {
        return getSecurityManager().isHttpSessionMode();
    }

    private WebSubject createSubject(ServletRequest request, ServletResponse response) {
        return new WebSubject.Builder(getSecurityManager(), request, response).buildWebSubject();
    }

    private void updateSessionLastAccessTime(ServletRequest request, ServletResponse response) {
        if (!isHttpSessions()) { //'native' sessions
            Subject subject = SecurityUtils.getSubject();
            //Subject should never _ever_ be null, but just in case:
            if (subject != null) {
                Session session = subject.getSession(false);
                if (session != null) {
                    try {
                        session.touch();
                    } catch (Throwable t) {
                        logger.error("session.touch() method invocation has failed.  Unable to update" +
                                "the corresponding session's last access time based on the incoming request.", t);
                    }
                }
            }
        }
    }

    private void resolveBefore(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        for (IHandler handler : CustomPlugin.getPluginIhanders()) {
            handler.beforeHandler(target, request, response, isHandled);
        }
    }

    private void resolveAfter(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        for (IHandler handler : CustomPlugin.getPluginIhanders()) {
            handler.afterHandler(target, request, response, isHandled);
        }
    }
}