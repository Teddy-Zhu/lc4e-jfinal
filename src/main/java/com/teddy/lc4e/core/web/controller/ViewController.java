package com.teddy.lc4e.core.web.controller;

import com.teddy.jfinal.annotation.*;
import com.teddy.jfinal.entity.Method;
import com.teddy.jfinal.interfaces.BaseController;
import com.teddy.jfinal.tools.RelativeDate;
import com.teddy.lc4e.core.config.Key;
import com.teddy.lc4e.core.entity.Article;
import com.teddy.lc4e.core.entity.Message;
import com.teddy.lc4e.core.entity.Popup;
import com.teddy.lc4e.core.web.service.ComVarService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresGuest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * Created by teddy on 2015/7/18.
 */
@Controller("/")
public class ViewController extends BaseController {

    @ValidateParams(value = {
            @ValidateParam(value = "p", type = int.class, defaultValue = "1"),
            @ValidateParam(value = "art", type = boolean.class, defaultValue = "false")
    })
    @SetPJAX
    public void index() {
        setAttr("page", getPara("p"));
        if (getParaToBoolean("art")) {
            forwardAction("/Articles");
        } else {
            render("pages/index");
        }
    }

    @RequestMethod(Method.GET)
    @ValidateParam(value = "p", type = int.class, defaultValue = "1")
    public void Articles() {
        Integer size = Integer.valueOf(ComVarService.service.getComVarValueByName("IndexPageSize"));
        String[] cate = new String[]{"Java", "Obj-C", "C", "C++", "IOS", "Android"};
        String[] users = new String[]{"Admin", "Test", "Myas", "Liakx", "Google", "vsss"};
        Date now = new Date();
        List<Article> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new Article("/images/wireframe/image.png", new Popup("Matt", "Matt has been a member since July 2014"), "The friction between your thoughts and your code", cate[new Random().nextInt(cate.length - 1)], users[new Random().nextInt(users.length - 1)], new Random().nextInt(100),
                    RelativeDate.format(RelativeDate.randomDate("2015-05-11 13:00:00", now), now), users[new Random().nextInt(users.length - 1)]));
        }
        setAttr("lists", list);
        render("ajax/_article");
    }

    public void captcha() {
        renderCaptcha();
    }

    public void TopHots() {
        render("topHotTest");
    }

    public void SignOut() {
        SecurityUtils.getSubject().logout();
        if (isAJAX()) {
            renderJson(new Message(true, "SignOut Successfully"));
        } else {
            forwardAction("/");
        }
    }

    @RequiresGuest
    @SetComVar(value = Key.CAPTCHA, type = Boolean.class)
    @SetPJAX
    public void SignIn() {
        render("pages/signin");
    }

    @SetPJAX
    @RequiresGuest
    @SetComVar(value = Key.CAPTCHA, type = Boolean.class)
    public void SignUp() {
        render("pages/signup");
    }

    @SetPJAX
    public void test() {
        render("pages/test");
    }

    //test
    public void Error() {
        setAttr("message", new Message("test error"));
        render("pages/exception");
    }
}
