package com.teddy.jfinal.tools;

import com.teddy.jfinal.plugin.PropPlugin;
import com.teddy.jfinal.common.Dict;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by teddy on 2015/7/19.
 */
public class AuthTool {


    public static void setAuthCode(HttpServletResponse response, String authCode) {
        byte[] authTokenByte = null;
        try {
            authTokenByte = authCode.getBytes(PropPlugin.getValue(Dict.ENCODING));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String securityKey = PropPlugin.getValue(Dict.SECURITY_KEY);
        byte[] keyByte = Base64.decodeBase64(securityKey);

        byte[] securityByte = null;
        try {
            securityByte = SecurityIDEATool.encrypt(authTokenByte, keyByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String securityCookie = Base64.encodeBase64String(securityByte);

        try {
            securityCookie = StringTool.encode(securityCookie);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int maxAgeTemp = ((Integer) PropPlugin.getInt(Dict.MAX_AGE)).intValue();
        WebTool.addCookie(response, "", "/", true, "authCode", securityCookie, maxAgeTemp);
    }

    /**
     * 获取验证码
     *
     * @param request
     * @return
     */
    public static String getAuthCode(HttpServletRequest request) {
        String authCode = WebTool.getCookieValueByName(request, "authCode");
        if (null != authCode && !authCode.equals("")) {

            try {
                authCode = StringTool.decode(authCode);
            } catch (Exception e) {
                e.printStackTrace();
            }


            byte[] securityByte = Base64.decodeBase64(authCode);

            String securityKey = PropPlugin.getValue(Dict.SECURITY_KEY);
            byte[] keyByte = Base64.decodeBase64(securityKey);

            byte[] dataByte = null;
            try {
                dataByte = SecurityIDEATool.decrypt(securityByte, keyByte);
            } catch (Exception e) {
                e.printStackTrace();
            }
            authCode = new String(dataByte);
        }
        return authCode;
    }

}
