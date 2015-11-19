package com.teddy.jfinal.handler.filter;

import com.jfinal.config.Constants;
import com.jfinal.config.JFinalConfig;
import com.jfinal.core.JFinal;
import com.jfinal.core.JFinalFilter;
import com.jfinal.core.JFinalPublic;
import com.jfinal.handler.Handler;
import com.jfinal.log.Logger;
import com.teddy.jfinal.handler.gzip.GZIPResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by teddyzhu on 15/11/19.
 */
public class Lc4eFilter implements Filter {
    private Handler handler;
    private String encoding;
    private JFinalConfig jfinalConfig;
    private Constants constants;
    private static final JFinal jfinal = JFinal.me();
    private static Logger log;
    private int contextPathLength;
    private static final JFinalPublic pub = new JFinalPublic();

    public void init(FilterConfig filterConfig) throws ServletException {
        createJFinalConfig(filterConfig.getInitParameter("configClass"));


        if (!pub.init(jfinal, jfinalConfig, filterConfig.getServletContext()))
            throw new RuntimeException("JFinal init error!");

        handler = pub.getHandler(jfinal);
        constants = pub.getConstants();
        encoding = constants.getEncoding();
        jfinalConfig.afterJFinalStart();

        String contextPath = filterConfig.getServletContext().getContextPath();
        contextPathLength = (contextPath == null || "/".equals(contextPath) ? 0 : contextPath.length());
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        request.setCharacterEncoding(encoding);

        String target = request.getRequestURI();
        if (contextPathLength != 0)
            target = target.substring(contextPathLength);

        boolean[] isHandled = {false};
        try {
            handler.handle(target, request, response, isHandled);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                String qs = request.getQueryString();
                log.error(qs == null ? target : target + "?" + qs, e);
            }
        }

        if (!isHandled[0]) {
            String ae = request.getHeader("accept-encoding");
            //check if browser support gzip
            if (ae != null && ae.contains("gzip")) {
                GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(response);
                chain.doFilter(request, wrappedResponse);
                wrappedResponse.finishResponse();
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    public void destroy() {
        jfinalConfig.beforeJFinalStop();
        pub.stopPlugins(jfinal);
    }

    private void createJFinalConfig(String configClass) {
        if (configClass == null)
            throw new RuntimeException("Please set configClass parameter of JFinalFilter in web.xml");

        Object temp = null;
        try {
            temp = Class.forName(configClass).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Can not create instance of class: " + configClass, e);
        }

        if (temp instanceof JFinalConfig)
            jfinalConfig = (JFinalConfig) temp;
        else
            throw new RuntimeException("Can not create instance of class: " + configClass + ". Please check the config in web.xml");
    }

    static void initLogger() {
        log = Logger.getLogger(JFinalFilter.class);
    }
}
