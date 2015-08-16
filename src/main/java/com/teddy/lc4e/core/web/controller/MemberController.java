package com.teddy.lc4e.core.web.controller;

import com.teddy.jfinal.annotation.Controller;
import com.teddy.jfinal.annotation.RequestMethod;
import com.teddy.jfinal.annotation.ValidateParam;
import com.teddy.jfinal.annotation.ValidateParams;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.entity.Method;
import com.teddy.jfinal.interfaces.BaseController;
import com.teddy.jfinal.plugin.beetl.Lc4eCaptchaRender;
import com.teddy.lc4e.core.config.Key;
import com.teddy.lc4e.core.database.mapping.T_Sys_Common_Variable;
import com.teddy.lc4e.core.database.mapping.T_User;
import com.teddy.lc4e.core.database.model.Sys_Common_Variable;
import com.teddy.lc4e.core.database.model.User;
import com.teddy.lc4e.core.entity.Message;
import com.teddy.lc4e.core.web.service.ComVarService;
import com.teddy.lc4e.core.web.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;

/**
 * Created by teddy on 2015/8/10.
 */
@Controller("/member")
public class MemberController extends BaseController {


    public void signup() {
        User user = getModel(User.class, "user").enhancer();
        UserService.service.createUser(user);

        renderJson(user);
    }

    @ValidateParams(fields = {@ValidateParam(value = "user.name", minLen = 4, maxLen = 12),
            @ValidateParam(value = "user.password", minLen = 6, maxLen = 20),
            @ValidateParam(value = "captcha", defaultValue = "@@@@", maxLen = 4, minLen = 4),
            @ValidateParam(value = "rememberMe", type = Boolean.class)
    })
    @RequestMethod(Method.POST)
    @RequiresGuest
    public void signin() {
        User user = getModel(User.class, "user");
        Sys_Common_Variable captcha = ComVarService.service.getComVarByName(Key.REGISTER_CAPTCHA);
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
