package com.teddy.jfinal.handler.support;

import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;
import com.teddy.jfinal.Exceptions.Lc4eException;
import com.teddy.jfinal.Exceptions.ValidateException;
import com.teddy.jfinal.annotation.*;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.entity.FileType;
import com.teddy.jfinal.tools.ReflectTool;
import com.teddy.lc4e.core.database.mapping.T_Sys_Common_Variable;
import com.teddy.lc4e.core.database.model.Sys_Common_Variable;
import com.teddy.lc4e.core.web.service.ComVarService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by teddy on 2015/7/29.
 */
class ValidateKit {

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

        ValidateComVar[] validateComVars = comVars.fields();
        for (int i = 0, len = validateComVars.length; i < len; i++) {
            resolveComVar(validateComVars[i], invocation);
        }

    }

    public static void resolveComVar(ValidateComVar comVar, Invocation invocation) throws ValidateException {
        if (comVar == null && !comVar.name().equals(Const.DEFAULT_NONE) && !comVar.value().equals(Const.DEFAULT_NONE)) {
            return;
        }
        Sys_Common_Variable variable = ComVarService.service.getComVarByName(comVar.name()).get(T_Sys_Common_Variable.VALUE);
        if (variable == null) {
            throw new ValidateException("No ComVar Record Found in Database or Cache");
        } else if (!variable.get(T_Sys_Common_Variable.VALUE).equals(comVar.value())) {
            throw new ValidateException(variable.get(T_Sys_Common_Variable.ERROR));
        }
    }

    public static void resolveParameter(ValidateParam param, Invocation invocation) throws ValidateException {
        Controller controller = invocation.getController();
        Object object = null;
        Class type = param.type();
        type = ReflectTool.wrapper(type);
        Object field = param.index() != -1 ? param.index() : param.value();
        if (field.equals(-1) || field.equals(Const.DEFAULT_NONE)) {
            throw new ValidateException("Parameter field is  invalid");
        }
        if (Boolean.class == type) {
            object = getParam(param.index(), field, controller);

        } else if (Integer.class == type) {
            object = getParam(param.index(), field, controller);

        } else if (Long.class == type) {

        } else if (Double.class == type) {

        } else if (Float.class == type) {

        } else if (File.class == type) {
            UploadFile file = controller.getFile(param.value());
            if (param.required()) {
                if (file == null) {
                    throw new ValidateException("Upload File must be not empty!");
                } else if (param.maxSize() != -1 && file.getFile().length() >= param.maxSize()) {
                    throw new ValidateException("Upload File is too large!");
                } else if (param.minSize() != -1 && file.getFile().length() < param.maxSize()) {
                    throw new ValidateException("Upload File is too small!");
                } else if (!param.fileType().equals(FileType.NONE) && !file.getContentType().contains(param.fileType().toString())) {
                    throw new ValidateException("Upload File Type must be " + param.fileType().toString() + "!");
                }
            }
        } else {
            //POJO model
        }
    }

    private static Object getParam(Integer index, Object field, Controller controller) {
        if (index == -1) {
            return controller.getPara((String) field);
        } else {
            return controller.getPara((Integer) field);
        }
    }
}
