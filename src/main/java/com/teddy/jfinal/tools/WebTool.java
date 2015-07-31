package com.teddy.jfinal.tools;

import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class WebTool {

    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(WebTool.class);


    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    public static String getContextPath(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort()).append(request.getContextPath());
        String path = sb.toString();
        sb = null;
        return path;
    }

    public static String getRequestURIWithParam(HttpServletRequest request) {
        return request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
    }


    public static Map<String, String> getParamMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> enume = request.getParameterNames();
        while (enume.hasMoreElements()) {
            String name = (String) enume.nextElement();
            map.put(name, request.getParameter(name));
        }
        return map;
    }

    public static void addCookie(HttpServletResponse response,
                                 String domain, String path, boolean isHttpOnly,
                                 String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);

        if (null != domain && !domain.isEmpty()) {
            cookie.setDomain(domain);
        }

        cookie.setPath("/");
        if (null != path && !path.isEmpty()) {
            cookie.setPath(path);
        }

        cookie.setHttpOnly(isHttpOnly);

        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }

        response.addCookie(cookie);
    }


    public static String getCookieValueByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = WebTool.readCookieMap(request);

        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie.getValue();
        } else {
            return null;
        }
    }


    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);

        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }


    public static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();

        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (int i = 0; i < cookies.length; i++) {
                cookieMap.put(cookies[i].getName(), cookies[i]);
            }
        }
        return cookieMap;
    }

    public static String HtmltoText(String inputString) {
        String htmlStr = inputString;
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        java.util.regex.Pattern p_ba;
        java.util.regex.Matcher m_ba;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String patternStr = "\\s+";

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_ba = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
            m_ba = p_ba.matcher(htmlStr);
            htmlStr = m_ba.replaceAll(""); // 过滤空格

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
        return textStr;
    }


    public static String getHTMLToString(String sourcestr) {
        if (sourcestr == null) {
            return "";
        }
        sourcestr = sourcestr.replaceAll("\\x26", "&amp;");// &
        sourcestr = sourcestr.replaceAll("\\x3c", "&lt;");// <
        sourcestr = sourcestr.replaceAll("\\x3e", "&gt;");// >
        sourcestr = sourcestr.replaceAll("\\x09", "&nbsp;&nbsp;&nbsp;&nbsp;");// tab键
        sourcestr = sourcestr.replaceAll("\\x20", "&nbsp;");// 空格
        sourcestr = sourcestr.replaceAll("\\x22", "&quot;");// "

        sourcestr = sourcestr.replaceAll("\r\n", "<br>");// 回车换行
        sourcestr = sourcestr.replaceAll("\r", "<br>");// 回车
        sourcestr = sourcestr.replaceAll("\n", "<br>");// 换行
        return sourcestr;
    }

    public static String getStringToHTML(String sourcestr) {
        if (sourcestr == null) {
            return "";
        }
        sourcestr = sourcestr.replaceAll("&amp;", "\\x26");// &
        sourcestr = sourcestr.replaceAll("&lt;", "\\x3c");// <
        sourcestr = sourcestr.replaceAll("&gt;", "\\x3e");// >
        sourcestr = sourcestr.replaceAll("&nbsp;&nbsp;&nbsp;&nbsp;", "\\x09");// tab键
        sourcestr = sourcestr.replaceAll("&nbsp;", "\\x20");// 空格
        sourcestr = sourcestr.replaceAll("&quot;", "\\x22");// "

        sourcestr = sourcestr.replaceAll("<br>", "\r\n");// 回车换行
        sourcestr = sourcestr.replaceAll("<br>", "\r");// 回车
        sourcestr = sourcestr.replaceAll("<br>", "\n");// 换行
        return sourcestr;
    }
}