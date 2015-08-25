package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;
import com.teddy.jfinal.annotation.*;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.entity.FileType;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.exceptions.ValidateException;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.jfinal.tools.StringTool;
import com.teddy.lc4e.core.database.mapping.T_Sys_Common_Variable;
import com.teddy.lc4e.core.database.model.Sys_Common_Variable;
import com.teddy.lc4e.core.web.service.ComVarService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.*;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by teddy on 2015/7/29.
 */
class ValidateKit {

    public static void resolveResponseStaus(ResponseStatus responseStatus, Invocation invocation) {
        if (responseStatus == null) {
            return;
        }
        invocation.getController().getResponse().setStatus(responseStatus.value().toInteger());

    }

    public static void resolveRequestMethod(RequestMethod method, Invocation invocation) throws ValidateException {
        if (method != null && !method.value().toString().equals(invocation.getController().getRequest().getMethod().toUpperCase())) {
            // controller.renderError(404);
            throw new ValidateException("404");
        }
    }

    /**
     * validate request header
     *
     * @param header
     * @param invocation
     * @throws Lc4eException
     * @throws ValidateException
     */
    public static void resolveRequestHeader(RequestHeader header, Invocation invocation) throws Lc4eException, ValidateException {
        if (header == null) {
            return;
        }
        HttpServletRequest request = invocation.getController().getRequest();

        String[] keys = header.key();
        String[] values = header.value();
        if (keys.length == values.length) {
            for (int i = 0, len = keys.length; i < len; i++) {
                String value = request.getHeader(keys[i].trim());
                if (StrKit.isBlank(value) || !value.trim().equals(values[i].trim())) {
                    //controller.renderError(404);
                    throw new ValidateException("Request Header [" + keys[i] + "] must be [" + values[i] + "]");
                }
            }
        } else {
            throw new Lc4eException("@RequestHeader need the same length key and value");
        }
    }

    public static void resolveToken(ValidateToken token, Invocation invocation) throws ValidateException {
        if (token == null) {
            return;
        }
        HttpServletRequest request = invocation.getController().getRequest();
        String suf = String.valueOf(request.getRequestURI().length() - 2), pre = String.valueOf(request.getRequestURI().length());
        String lc4eToken = request.getHeader(Const.LC4E_TOKEN);

        if (lc4eToken == null || lc4eToken.trim().equals("")) {
            throw new ValidateException("Token must be not empty");
        } else {
            String regex = "\\d+", unixTime = "";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(lc4eToken);

            while (m.find()) {
                unixTime = m.group();
            }
            if (!unixTime.equals(lc4eToken)) {
                throw new ValidateException("Token format is error");
            }
            regex = "\\b" + suf + "(.*)" + pre + "\\b";
            p = Pattern.compile(regex);
            m = p.matcher(lc4eToken);

            while (m.find()) {
                unixTime = m.group(1);
            }
            if (unixTime == null || "".equals(unixTime.trim())) {
                throw new ValidateException("Token format is error");
            }
            Long now = new Date().getTime();
            Long diff = now - Long.valueOf(unixTime);
            if (diff < 0) {
                throw new ValidateException("Token is expired");
            }
            Long day = diff / (1000 * 60 * 60 * 24);
            Long hour = (diff / (60 * 60 * 1000) - day * 24);
            Long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            Long second = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

            if (day > 0 || hour > 0 || min > 0 || second > 10) {
                throw new ValidateException("Token is expired");
            }
        }
    }

    public static void resolveComVars(ValidateComVars comVars, Invocation invocation) throws ValidateException {
        if (comVars == null) {
            return;
        }
        for (int i = 0, len = comVars.fields().length; i < len; i++) {
            resolveComVar(comVars.fields()[i], invocation);
        }

    }

    public static void resolveComVar(ValidateComVar comVar, Invocation invocation) throws ValidateException {
        if (comVar == null) {
            return;
        }
        if (StringTool.equalEmpty(comVar.name()) || StringTool.equalEmpty(comVar.value())) {
            throw new ValidateException("Parameter field in invalid!");
        }
        Sys_Common_Variable variable = ComVarService.service.getComVarByName(comVar.name());
        if (variable == null) {
            throw new ValidateException("No ComVar Record Found in Database or Cache");
        } else if (!variable.get(T_Sys_Common_Variable.value).equals(comVar.value())) {
            throw new ValidateException(variable.get(T_Sys_Common_Variable.error));
        }
    }

    public static void resolveParameters(ValidateParams params, Invocation invocation) throws InvocationTargetException, NoSuchMethodException, ValidateException, NoSuchFieldException, IllegalAccessException, ParseException {
        if (params == null) {
            return;
        }
        resolveParams(params.value(), invocation);
        boolean isTrue;
        if (params.select()) {
            try {
                resolveComVar(params.condition(), invocation);
                isTrue = true;
            } catch (Exception e) {
                isTrue = false;
                resolveParams(params.falseFields(), invocation);
            }
            if (isTrue) {
                resolveParams(params.trueFields(), invocation);
            }
        }
    }

    private static void resolveParams(ValidateParam[] params, Invocation invocation) throws InvocationTargetException, NoSuchMethodException, ValidateException, NoSuchFieldException, IllegalAccessException, ParseException {
        if (params == null) {
            return;
        }
        for (ValidateParam param : params) {
            resolveParameter(param, invocation);
        }
    }

    public static void resolveParameter(ValidateParam param, Invocation invocation) throws ValidateException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, ParseException {
        if (param == null) {
            return;
        }
        if (StringTool.equalEmpty(param.value()) && param.index() == -1) {
            throw new ValidateException("Parameter field is  invalid");
        }
        Controller controller = invocation.getController();
        Object object;
        Class type = param.type();
        type = ReflectTool.wrapper(type);
        boolean setDefaultFlag = false;
        if (param.index() != -1) {
            object = controller.getPara(param.index());
        } else {
            object = controller.getPara(param.value());
        }

        if (object == null && param.required()) {
            setDefaultFlag = true;
        }
        object = object != null ? object : Const.DEFAULT_NONE.equals(param.defaultValue()) ? object : param.defaultValue();

        validate(param, controller, type, object, setDefaultFlag);
    }

    public static void validate(ValidateParam param, Controller controller, Class type, Object object, boolean setDefaultFlag) throws ValidateException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, ParseException {
        if (String.class == type) {
            if (param.required() && object == null) {
                throw new ValidateException("Parameter field is  invalid");
            }
            if (object != null) {
                String tmp = String.valueOf(object.toString());
                if (param.minLen() != -1 && param.minLen() > tmp.length()) {
                    throw new ValidateException("Parameter length is too short");
                }
                if (param.maxLen() != -1 && param.maxLen() < tmp.length()) {
                    throw new ValidateException("Parameter length is too long");
                }
                if (setDefaultFlag) {
                    ReflectTool.setParameter(param.value(), object.toString(), controller);
                }
            }

        } else if (Boolean.class == type) {
            if (param.required() && object == null) {
                throw new ValidateException("Parameter field is  invalid");
            }
            if (object != null) {
                Boolean.valueOf(object.toString());
                if (setDefaultFlag) {
                    ReflectTool.setParameter(param.value(), object.toString(), controller);
                }
            }
        } else if (Integer.class == type) {
            if (param.required() && object == null) {
                throw new ValidateException("Parameter field is  invalid");
            }
            if (object != null) {
                Integer tmp = Integer.valueOf(object.toString());
                if (param.minInt() != -1 && param.minInt() > tmp) {
                    throw new ValidateException("Parameter is too small");
                }
                if (param.maxInt() != -1 && param.maxInt() < tmp) {
                    throw new ValidateException("Parameter is too large");
                }
                if (setDefaultFlag) {
                    ReflectTool.setParameter(param.value(), object.toString(), controller);
                }
            }
        } else if (Long.class == type) {
            if (param.required() && object == null) {
                throw new ValidateException("Parameter field is  invalid");
            }
            if (object != null) {
                Long tmp = Long.valueOf(object.toString());
                if (param.minLong() != -1 && param.minLong() > tmp) {
                    throw new ValidateException("Parameter is too small");
                }
                if (param.maxLong() != -1 && param.maxLong() < tmp) {
                    throw new ValidateException("Parameter is too large");
                }
                if (setDefaultFlag) {
                    ReflectTool.setParameter(param.value(), object.toString(), controller);
                }

            }

        } else if (Double.class == type) {
            if (param.required() && object == null) {
                throw new ValidateException("Parameter field is  invalid");
            }
            if (object != null) {
                Double tmp = Double.valueOf(object.toString());
                if (param.minDouble() != -1 && param.minDouble() > tmp) {
                    throw new ValidateException("Parameter is too small");
                }
                if (param.maxDouble() != -1 && param.maxDouble() < tmp) {
                    throw new ValidateException("Parameter is too large");
                }
                if (setDefaultFlag) {
                    ReflectTool.setParameter(param.value(), object.toString(), controller);
                }

            }
        } else if (Float.class == type) {
            if (param.required() && object == null) {
                throw new ValidateException("Parameter field is  invalid");
            }
            if (object != null) {
                Float tmp = Float.valueOf(object.toString());
                if (param.minDouble() != -1 && param.minDouble() > tmp) {
                    throw new ValidateException("Parameter is too small");
                }
                if (param.maxDouble() != -1 && param.maxDouble() < tmp) {
                    throw new ValidateException("Parameter is too large");
                }
                if (setDefaultFlag) {
                    ReflectTool.setParameter(param.value(), object.toString(), controller);
                }
            }

        } else if (Date.class == type) {
            if (param.required() && object == null) {
                throw new ValidateException("Parameter field is  invalid");
            }
            if (object != null) {
                SimpleDateFormat sdf = new SimpleDateFormat(param.DateFormatter());
                Date tmp = sdf.parse(object.toString());

                if (!Const.DEFAULT_NONE.equals(param.minDate()) && sdf.parse(param.minDate()).getTime() > tmp.getTime()) {
                    throw new ValidateException("Parameter time is too early");
                }
                if (!Const.DEFAULT_NONE.equals(param.maxDate()) && sdf.parse(param.maxDate()).getTime() < tmp.getTime()) {
                    throw new ValidateException("Parameter time is too late");
                }
                if (setDefaultFlag) {
                    ReflectTool.setParameter(param.value(), sdf.format(tmp), controller);
                }
            }

        } else if (File.class == type) {
            UploadFile file = controller.getFile(param.value());
            if (param.required()) {
                if (file == null) {
                    throw new ValidateException("Upload File must be not empty!");
                }
                if (param.maxSize() != -1 && file.getFile().length() > param.maxSize()) {
                    throw new ValidateException("Upload File size is too large!");
                }
                if (param.minSize() != -1 && file.getFile().length() < param.maxSize()) {
                    throw new ValidateException("Upload File size is too small!");
                }
                if (!param.fileType().equals(FileType.NONE) && !file.getContentType().contains(param.fileType().toString())) {
                    throw new ValidateException("Upload File Type must be " + param.fileType().toString() + "!");
                }
            }
        } else {
            throw new ValidateException("Do not support the type [" + type.toString() + "]");
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
}
