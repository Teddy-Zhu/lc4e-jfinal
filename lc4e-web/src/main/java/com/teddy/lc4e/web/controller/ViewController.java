package com.teddy.lc4e.web.controller;

import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.StrKit;
import com.teddy.jfinal.annotation.*;
import com.teddy.jfinal.entity.ReturnData;
import com.teddy.jfinal.interfaces.BaseController;
import com.teddy.jfinal.tools.RelativeDate;
import com.teddy.lc4e.config.Key;
import com.teddy.lc4e.database.model.SysCommonVariable;
import com.teddy.lc4e.entity.Article;
import com.teddy.lc4e.entity.Message;
import com.teddy.lc4e.entity.Popup;
import com.teddy.lc4e.web.service.ComVarService;
import org.apache.log4j.Logger;
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

    private static final Logger log = Logger.getLogger(ViewController.class);

    @ValidateParams(value = {
            @ValidateParam(index = 1, type = int.class, defaultValue = "1"),
            @ValidateParam(value = "art", type = boolean.class, defaultValue = "false"),
            @ValidateParam(index = 0, type = Integer.class, defaultValue = "2")
    })
    public void index() {
        setAttr("page", getPara(1));
        render("index.html");
    }

    @ValidateParams({
            @ValidateParam(index = 2, type = int.class, defaultValue = "1"), //page
            @ValidateParam(index = 0, type = String.class, defaultValue = "all"),
            @ValidateParam(index = 1, type = Integer.class, defaultValue = "1") //order
    })
    public void a() {
        if (isPOST()) {
            renderJson(new Message(true, new ReturnData("curArea", getPara(0)), new ReturnData("topics", getArticle(getParaToInt(2), getParaToInt(1), getPara(0)))));
        } else {
            setAttr("curArea", getPara(0)).setAttr("topics", getArticle(getParaToInt(2), getParaToInt(1), getPara(0)));
            render("index.html");
        }
    }


    public void t() {
        render("index.html");
    }


    public void captcha() {
        renderCaptcha();
    }

    public void TopHots() {
        render("topHotTest.html");
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
    public void SignIn() {
        ReturnData data = new ReturnData(Key.CAPTCHA, ComVarService.service.getComVarByName(Key.CAPTCHA).getToBoolean(SysCommonVariable.VALUE));
        if (isPOST()) {
            renderJson(new Message(true, data));
        } else {
            render("index.html");
        }
    }

    @RequiresGuest
    public void SignUp() {
        ReturnData[] datas ={
              new ReturnData(Key.CAPTCHA, ComVarService.service.getComVarByName(Key.CAPTCHA).getToBoolean(SysCommonVariable.VALUE)),
              new ReturnData(Key.SIMPLE_REGISTER, ComVarService.service.getComVarByName(Key.SIMPLE_REGISTER).getToBoolean(SysCommonVariable.VALUE))};
        if (isPOST()) {
            renderJson(new Message(true, datas));
        } else {
            render("index.html");
        }
    }

    //test
    public void Error() {
        setAttr("message", new Message("test error"));
        render("pages/exception.html");
    }

    public List<Article> getArticle(int page, int order, String area) {
        Integer size = 10;
        if (StrKit.isBlank(area) || "all".equals(area)) {
            area = "";
            size = Integer.valueOf(ComVarService.service.getComVarValueByName("IndexPageSize"));
        } else {
            size = Integer.valueOf(ComVarService.service.getComVarValueByName("AreaPageSize"));
        }

        String[] cate = new String[]{"Java", "Obj-C", "C", "C++", "IOS", "Android"};
        String[] high = new String[]{"TOP", "NOTICE", "OTHER", "SYSTEM", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
        String[] users = new String[]{"Admin", "Test", "Myas", "Liakx", "Google", "vsss"};
        Date now = new Date();
        List<Article> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new Article("/themes/" + Key.kvs.get("Theme") + "/images/wireframe/image.png", new Popup("Matt", "Matt has been a member since July 2014"), "The friction between your thoughts and your code" + page, "/t/hello" + new Random().nextInt(1000), cate[new Random().nextInt(cate.length - 1)], users[new Random().nextInt(users.length - 1)], new Random().nextInt(100),
                    RelativeDate.format(RelativeDate.randomDate("2015-05-11 13:00:00", now), now), users[new Random().nextInt(users.length - 1)], page, high[new Random().nextInt(high.length - 1)]));
        }
        return list;
    }
}
