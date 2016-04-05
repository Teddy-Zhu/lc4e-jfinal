package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;
import com.teddy.jfinal.annotation.*;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.entity.FileType;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.exceptions.Lc4eValidateException;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.jfinal.tools.StringTool;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.*;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by teddy on 2015/7/29.
 */
class ValidateKit {
    static Map<String, Consumer<Annotation>> validateFunctions;

    private static final Logger log = Logger.getLogger(ValidateKit.class);

    public static void resolveResponseStatus(ResponseStatus responseStatus, Invocation invocation) {
        if (responseStatus == null) {
            return;
        }
        invocation.getController().getResponse().setStatus(responseStatus.value().toInteger());

    }

    public static void resolveRequestMethod(RequestMethod method, Controller controller) throws Lc4eValidateException {
        if (method != null && !method.value().toString().equals(controller.getRequest().getMethod().toUpperCase())) {
            // controller.renderError(404);
            throw new Lc4eValidateException("404");
        }
    }

    /**
     * validate request header
     *
     * @param header
     * @param controller
     * @throws Lc4eException
     * @throws Lc4eValidateException
     */
    public static void resolveRequestHeader(RequestHeader header, Controller controller) throws Lc4eException, Lc4eValidateException {
        if (header == null) {
            return;
        }
        HttpServletRequest request = controller.getRequest();

        String[] keys = header.key();
        String[] values = header.value();
        if (keys.length == values.length) {
            for (int i = 0, len = keys.length; i < len; i++) {
                String value = request.getHeader(keys[i].trim());
                if (StrKit.isBlank(value) || !value.trim().equals(values[i].trim())) {
                    //controller.renderError(404);
                    throw new Lc4eValidateException("Request Header [" + keys[i] + "] must be [" + values[i] + "]");
                }
            }
        } else {
            throw new Lc4eException("@RequestHeader need the same length key and value");
        }
    }

    public static void resolveToken(ValidateToken token, Controller controller) throws Lc4eValidateException {
        if (token == null) {
            return;
        }
        HttpServletRequest request = controller.getRequest();
        String suf = String.valueOf(request.getRequestURI().length() - 2), pre = String.valueOf(request.getRequestURI().length());
        String lc4eToken = request.getHeader(Const.LC4E_TOKEN);

        if (lc4eToken == null || lc4eToken.trim().equals("")) {
            throw new Lc4eValidateException("Token must be not empty");
        } else {
            String regex = "\\d+", unixTime = "";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(lc4eToken);

            while (m.find()) {
                unixTime = m.group();
            }
            if (!unixTime.equals(lc4eToken)) {
                throw new Lc4eValidateException("Token format is error");
            }
            regex = "\\b" + suf + "(.*)" + pre + "\\b";
            p = Pattern.compile(regex);
            m = p.matcher(lc4eToken);

            while (m.find()) {
                unixTime = m.group(1);
            }
            if (unixTime == null || "".equals(unixTime.trim())) {
                throw new Lc4eValidateException("Token format is error");
            }
            Long now = new Date().getTime();
            Long diff = now - Long.valueOf(unixTime);
            if (diff < 0) {
                throw new Lc4eValidateException("Token is expired");
            }
            Long day = diff / (1000 * 60 * 60 * 24);
            Long hour = (diff / (60 * 60 * 1000) - day * 24);
            Long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            Long second = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

            if (day > 0 || hour > 0 || min > 0 || second > 10) {
                throw new Lc4eValidateException("Token is expired");
            }
        }
    }

    public static void resolveParameters(ValidateKitI validateKit, ValidateParams params, Controller controller) throws InvocationTargetException, NoSuchMethodException, Lc4eValidateException, NoSuchFieldException, IllegalAccessException, ParseException {
        if (params == null) {
            return;
        }
        resolveParams(params.value(), controller);
        boolean isTrue;
        if (params.select()) {
            try {
                validateKit.resolveComVar(params.condition(), controller);
                isTrue = true;
            } catch (Exception e) {
                isTrue = false;
                resolveParams(params.falseFields(), controller);
            }
            if (isTrue) {
                resolveParams(params.trueFields(), controller);
            }
        }
    }

    private static void resolveParams(ValidateParam[] params, Controller controller) throws InvocationTargetException, NoSuchMethodException, Lc4eValidateException, NoSuchFieldException, IllegalAccessException, ParseException {
        if (params == null) {
            return;
        }
        for (ValidateParam param : params) {
            resolveParameter(param, controller);
        }
    }

    static void resolveParameter(ValidateParam param, Controller controller) throws Lc4eValidateException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, ParseException {
        if (param == null) {
            return;
        }
        if (StringTool.equalEmpty(param.value()) && param.index() == -1) {
            throw new Lc4eValidateException(StringTool.equalEmpty(param.error()) ? "Parameter" : param.error() + " is  invalid");
        }
        Object object;
        Class type = param.type();
        type = ReflectTool.wrapper(type);
        boolean setDefaultFlag = false, isUrlPara = param.index() != -1;
        object = isUrlPara ? controller.getPara(param.index()) : controller.getPara(param.value());
        setDefaultFlag = object == null && param.required();
        object = object != null ? object : Const.DEFAULT_NONE.equals(param.defaultValue()) ? object : param.defaultValue();
        validate(param, controller, type, object, setDefaultFlag, isUrlPara);
    }

    private static void validate(ValidateParam param, Controller controller, Class type, Object object, boolean setDefaultFlag, boolean isUrlPara) throws Lc4eValidateException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, ParseException {
        String error = StringTool.equalEmpty(param.error()) ? "Parameter" : param.error();
        if (String.class == type) {
            validateRequired(param, object, error);
            if (param.required() && object != null) {
                String tmp = String.valueOf(object.toString());
                if (param.minLen() != -1 && param.minLen() > tmp.length()) {
                    throw new Lc4eValidateException(error + " length is too short");
                }
                if (param.maxLen() != -1 && param.maxLen() < tmp.length()) {
                    throw new Lc4eValidateException(error + " length is too long");
                }
            }
            setParam(controller, param, object, setDefaultFlag, isUrlPara);
        } else if (Boolean.class == type) {
            validateRequired(param, object, error);
            if (param.required() && object != null) {
                Boolean.valueOf(object.toString());
            }
            setParam(controller, param, object, setDefaultFlag, isUrlPara);
        } else if (Integer.class == type) {
            validateRequired(param, object, error);
            if (param.required() && object != null) {
                Integer tmp = Integer.valueOf(object.toString());
                if (param.minInt() != -1 && param.minInt() > tmp) {
                    throw new Lc4eValidateException(error + " is too small");
                }
                if (param.maxInt() != -1 && param.maxInt() < tmp) {
                    throw new Lc4eValidateException(error + " is too large");
                }
            }
            setParam(controller, param, object, setDefaultFlag, isUrlPara);
        } else if (Long.class == type) {
            validateRequired(param, object, error);
            if (param.required() && object != null) {
                Long tmp = Long.valueOf(object.toString());
                if (param.minLong() != -1 && param.minLong() > tmp) {
                    throw new Lc4eValidateException(error + " is too small");
                }
                if (param.maxLong() != -1 && param.maxLong() < tmp) {
                    throw new Lc4eValidateException(error + " is too large");
                }
            }
            setParam(controller, param, object, setDefaultFlag, isUrlPara);
        } else if (Double.class == type) {
            validateRequired(param, object, error);
            if (param.required() && object != null) {
                Double tmp = Double.valueOf(object.toString());
                if (param.minDouble() != -1 && param.minDouble() > tmp) {
                    throw new Lc4eValidateException(error + " is too small");
                }
                if (param.maxDouble() != -1 && param.maxDouble() < tmp) {
                    throw new Lc4eValidateException(error + " is too large");
                }
            }
            setParam(controller, param, object, setDefaultFlag, isUrlPara);
        } else if (Float.class == type) {
            validateRequired(param, object, error);
            if (param.required() && object != null) {
                Float tmp = Float.valueOf(object.toString());
                if (param.minDouble() != -1 && param.minDouble() > tmp) {
                    throw new Lc4eValidateException(error + " is too small");
                }
                if (param.maxDouble() != -1 && param.maxDouble() < tmp) {
                    throw new Lc4eValidateException(error + " is too large");
                }
            }
            setParam(controller, param, object, setDefaultFlag, isUrlPara);
        } else if (Date.class == type) {
            validateRequired(param, object, error);
            SimpleDateFormat sdf = new SimpleDateFormat(param.DateFormatter());
            if (param.required() && object != null) {
                Date tmp = sdf.parse(object.toString());
                if (!Const.DEFAULT_NONE.equals(param.minDate()) && sdf.parse(param.minDate()).getTime() > tmp.getTime()) {
                    throw new Lc4eValidateException(error + " is too early");
                }
                if (!Const.DEFAULT_NONE.equals(param.maxDate()) && sdf.parse(param.maxDate()).getTime() < tmp.getTime()) {
                    throw new Lc4eValidateException(error + " is too late");
                }
                object = tmp;
            }
            setParam(controller, param, sdf.format(object), setDefaultFlag, isUrlPara);
        } else if (File.class == type) {
            UploadFile file = controller.getFile(param.value());
            if (param.required()) {
                if (file == null) {
                    throw new Lc4eValidateException("Upload File must be not empty!");
                }
                if (param.maxSize() != -1 && file.getFile().length() > param.maxSize()) {
                    throw new Lc4eValidateException("Upload File size is too large!");
                }
                if (param.minSize() != -1 && file.getFile().length() < param.maxSize()) {
                    throw new Lc4eValidateException("Upload File size is too small!");
                }
                if (!param.fileType().equals(FileType.NONE) && !file.getContentType().contains(param.fileType().toString())) {
                    throw new Lc4eValidateException("Upload File Type must be " + param.fileType().toString() + "!");
                }
            }
        } else {
            throw new Lc4eValidateException("Do not support the type [" + type.toString() + "]");
        }
    }

    private static void setParam(Controller controller, ValidateParam param, Object object, boolean setDefaultFlag, boolean isUrlPara) throws NoSuchFieldException, IllegalAccessException {
        if (setDefaultFlag) {
            if (isUrlPara) {
                String urlPara = controller.getPara();
                urlPara = urlPara == null ? "" : urlPara;
                String[] arrayParams = urlPara.split(com.jfinal.core.Const.DEFAULT_URL_PARA_SEPARATOR);

                if (param.index() >= arrayParams.length) {
                    String[] newUrlPara = StringTool.copyOf(arrayParams, param.index() + 1);
                    newUrlPara[param.index()] = object.toString();
                    arrayParams = newUrlPara;
                } else {
                    arrayParams[param.index()] = object.toString();
                }
                controller.setUrlPara(StringUtils.join(arrayParams, com.jfinal.core.Const.DEFAULT_URL_PARA_SEPARATOR));
            } else {
                ReflectTool.setParameter(param.value(), object.toString(), controller);
            }
        }
    }


    private static void validateRequired(ValidateParam param, Object object, String error) throws Lc4eValidateException {
        if (param.required() && object == null) {
            throw new Lc4eValidateException(error + " is  invalid");
        }
    }

    public static void resolveShiroPermission(RequiresPermissions permissions) {
        if (permissions == null) {
            return;
        }
        String[] perms = permissions.value();
        Subject subject = SecurityUtils.getSubject();

        if (perms.length == 1) {
            subject.checkPermission(perms[0]);
            return;
        }
        if (Logical.AND.equals(permissions.logical())) {
            subject.checkPermissions(perms);
            return;
        }
        if (Logical.OR.equals(permissions.logical())) {
            // Avoid processing exceptions unnecessarily - "delay" throwing the
            // exception by calling hasRole first
            boolean hasAtLeastOnePermission = false;
            for (String permission : perms)
                if (subject.isPermitted(permission))
                    hasAtLeastOnePermission = true;
            // Cause the exception if none of the role match, note that the
            // exception message will be a bit misleading
            if (!hasAtLeastOnePermission)
                subject.checkPermission(perms[0]);
        }
    }

    public static void resolveShiroGeust(RequiresGuest guest) {
        if (SecurityUtils.getSubject().getPrincipal() != null) {
            throw new UnauthenticatedException("Attempting to perform a guest-only operation.  The current Subject is " +
                    "not a guest (they have been authenticated or remembered from a previous login).  Access " +
                    "denied.");
        }
    }

    public static void resolveShiroRole(RequiresRoles requiresRoles) {
        if (requiresRoles == null) {
            return;
        }
        String[] roles = requiresRoles.value();
        Subject subject = SecurityUtils.getSubject();
        if (roles.length == 1) {
            subject.checkRole(roles[0]);
            return;
        }
        if (Logical.AND.equals(requiresRoles.logical())) {
            subject.checkRoles(Arrays.asList(roles));
            return;
        }
        if (Logical.OR.equals(requiresRoles.logical())) {
            // Avoid processing exceptions unnecessarily - "delay" throwing the exception by calling hasRole first
            boolean hasAtLeastOneRole = false;
            for (String role : roles) if (subject.hasRole(role)) hasAtLeastOneRole = true;
            // Cause the exception if none of the role match, note that the exception message will be a bit misleading
            if (!hasAtLeastOneRole) subject.checkRole(roles[0]);
        }
    }

    public static void resolveShiroUser(RequiresUser requiresUser) {
        if (SecurityUtils.getSubject().getPrincipal() == null) {
            throw new UnauthenticatedException("Attempting to perform a user-only operation.  The current Subject is " +
                    "not a user (they haven't been authenticated or remembered from a previous login).  " +
                    "Access denied.");
        }
    }

    public static void resolveShiroAuthentication(RequiresAuthentication requiresAuthentication) {
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            throw new UnauthenticatedException("The current Subject is not authenticated.  Access denied.");
        }
    }

    public static void main(String[] args) {
        String a = "--a";

        String b[] = a.split("-");

        String c[] = StringTool.copyOf(b, 5);
        System.out.println(Arrays.toString(b));
    }
}
