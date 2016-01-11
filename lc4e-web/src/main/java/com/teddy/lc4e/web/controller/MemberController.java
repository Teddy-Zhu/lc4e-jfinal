package com.teddy.lc4e.web.controller;

import com.jfinal.kit.StrKit;
import com.teddy.jfinal.annotation.*;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.entity.Method;
import com.teddy.jfinal.exceptions.Lc4eApplicationException;
import com.teddy.jfinal.interfaces.BaseController;
import com.teddy.jfinal.plugin.jetbrick.Lc4eCaptchaRender;
import com.teddy.jfinal.tools.StringTool;
import com.teddy.lc4e.config.Key;
import com.teddy.lc4e.database.model.Sys_Common_Variable;
import com.teddy.lc4e.database.model.User;
import com.teddy.lc4e.database.model.User_Basicinfo;
import com.teddy.lc4e.entity.Message;
import com.teddy.lc4e.web.service.ComVarService;
import com.teddy.lc4e.web.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
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
    @ValidateComVar(name = Key.REGISTER, value = "true")
    public void signup() throws Lc4eApplicationException {
        User user = getModel(User.class, "user");
        User_Basicinfo basicInfo = getModel(User_Basicinfo.class, "extend");
        UserService.service.createUser(user, basicInfo);
        if (StrKit.notBlank(user.getStr(User.S_ID))) {
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
    public void signin() {
        User user = getModel(User.class, "user");
        Sys_Common_Variable captcha = ComVarService.service.getComVarByName(Key.CAPTCHA);
        if (captcha != null && captcha.getToBoolean(Sys_Common_Variable.S_VALUE) && !validateCaptcha(getPara("captcha"))) {
            renderJson(new Message(captcha.getStr(Sys_Common_Variable.S_ERROR)));
            return;
        }
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getStr(User.S_NAME), user.getStr(User.S_PASSWORD));
            token.setRememberMe(getParaToBoolean("rememberMe"));
            subject.login(token);
            if (subject.isAuthenticated()) {
                subject.getSession().setAttribute(Lc4eCaptchaRender.captcha_code, Const.DEFAULT_NONE);
            } else {
                renderJson(new Message("Login failed"));
            }
        }
        renderJson(new Message(true, "Login Success"));
    }

}
