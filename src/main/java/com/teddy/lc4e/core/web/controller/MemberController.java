package com.teddy.lc4e.core.web.controller;

import com.jfinal.kit.StrKit;
import com.teddy.jfinal.annotation.*;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.entity.Method;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.interfaces.BaseController;
import com.teddy.jfinal.plugin.beetl.Lc4eCaptchaRender;
import com.teddy.jfinal.tools.StringTool;
import com.teddy.lc4e.core.config.Key;
import com.teddy.lc4e.core.database.mapping.T_Sys_Common_Variable;
import com.teddy.lc4e.core.database.mapping.T_User;
import com.teddy.lc4e.core.database.model.Sys_Common_Variable;
import com.teddy.lc4e.core.database.model.User;
import com.teddy.lc4e.core.database.model.User_Basicinfo;
import com.teddy.lc4e.core.entity.Message;
import com.teddy.lc4e.core.web.service.ComVarService;
import com.teddy.lc4e.core.web.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;

import java.util.Date;

/**
 * Created by teddy on 2015/8/10.
 */
@Controller("/member")
public class MemberController extends BaseController {


    @RequestMethod(Method.POST)
    @ValidateParams({
            @ValidateParam(value = "user.name", minLen = 4, maxLen = 12),
            @ValidateParam(value = "user.password", minLen = 6, maxLen = 20),
            @ValidateParam(value = "user.nick", minLen = 4, maxLen = 12),
            @ValidateParam(value = "user.mail", minLen = 5, maxLen = 30, regex = StringTool.regExp_MAIL),
            @ValidateParam(value = "extend.phoneNumber", defaultValue = "", regex = StringTool.regExp_PhoneNumber),
            @ValidateParam(value = "extend.sign", defaultValue = "", maxLen = 50),
            @ValidateParam(value = "extend.birth", required = false, type = Date.class),
            @ValidateParam(value = "captcha", defaultValue = "@@@@", maxLen = 4, minLen = 4)
    })
    @ValidateComVar(name = Key.REGISTER, value = "false")
    public void signup() throws Lc4eException {
        User user = getModel(User.class, "user").enhancer();
        User_Basicinfo basicInfo = getModel(User_Basicinfo.class, "extend").enhancer();
        UserService.service.createUser(user, basicInfo);
        if (StrKit.notBlank(user.getStr(T_User.id))) {
            renderJson(new Message(true, "register successfully"));
        } else {
            renderJson(new Message("register failed"));
        }
    }

    @ValidateParams({
            @ValidateParam(value = "user.name", minLen = 4, maxLen = 12),
            @ValidateParam(value = "user.password", minLen = 6, maxLen = 20),
            @ValidateParam(value = "captcha", defaultValue = "@@@@", maxLen = 4, minLen = 4),
            @ValidateParam(value = "rememberMe", type = Boolean.class)
    })
    @RequestMethod(Method.POST)
    @RequiresGuest
    public void signin() {
        User user = getModel(User.class, "user");
        Sys_Common_Variable captcha = ComVarService.service.getComVarByName(Key.CAPTCHA);
        if (captcha != null && captcha.getToBoolean(T_Sys_Common_Variable.value) && !Lc4eCaptchaRender.validate(getPara("captcha"))) {
            renderJson(new Message(captcha.getStr(T_Sys_Common_Variable.error)));
            return;
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getStr(T_User.name), user.getStr(T_User.password));
        token.setRememberMe(getParaToBoolean("rememberMe"));
        subject.login(token);
        if (subject.isAuthenticated()) {
            subject.getSession().setAttribute(Lc4eCaptchaRender.captcha_code, Const.DEFAULT_NONE);
            renderJson(new Message(true, "Login Success"));
        } else {
            renderJson(new Message("Login failed"));
        }
    }

}
