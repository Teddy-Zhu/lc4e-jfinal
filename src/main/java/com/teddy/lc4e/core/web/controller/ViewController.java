package com.teddy.lc4e.core.web.controller;

import com.jfinal.core.ActionKey;
import com.teddy.jfinal.annotation.*;
import com.teddy.jfinal.entity.Method;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.interfaces.BaseController;
import com.teddy.jfinal.tools.WebTool;
import com.teddy.lc4e.core.entity.Article;
import com.teddy.lc4e.core.entity.Popup;
import com.teddy.lc4e.core.web.service.ComVarService;
import com.teddy.lc4e.core.web.service.MenuService;
import com.teddy.lc4e.core.web.service.testService;
import com.teddy.lc4e.util.tools.RelativeDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * Created by teddy on 2015/7/18.
 */
@Controller("/")
public class ViewController extends BaseController {


    /**
     *
     */
    @Inject
    public testService service;

    @SetComVar("SiteName")
    @SetUIData(methodClass = MenuService.class, methodName = "getMenuTree", attrName = "menulist")
    @ValidateParam(value = "p", type = int.class, defaultValue = "1")
    public void index() {
        setAttr("page", getPara("p"));
        if (WebTool.isPJAX(getRequest())) {
            forwardAction("/Articles");
        } else {
            render("index.html");
        }
    }

    @ActionKey("nulltest")
    @ValidateParam(value = "a", type = int.class, maxInt = 10, minInt = 3)
    public void nulltest() {
        if (getParaToInt("a") == 1) {
            throw new NullPointerException("test null ");
        }
        renderText("It's demo");
    }

    @ActionKey("test")
    public void test() throws Lc4eException {
        String a = getPara("a");


        service.test(a);

        renderText("asdasd");
    }

    @RequestMethod(Method.GET)
    @ActionKey("/Articles")
    @ValidateParam(value = "p", type = int.class, defaultValue = "1")
    public void articletest() {
        Integer size = Integer.valueOf(ComVarService.service.getComVarValueByName("IndexPageSize"));
        String[] cate = new String[]{"Java", "Obj-C", "C", "C++", "IOS", "Android"};
        String[] users = new String[]{"Admin", "Test", "Myas", "Liakx", "Google", "vsss"};
        Date now = new Date();
        List<Article> list = new ArrayList<Article>();
        for (int i = 0; i < size; i++) {
            list.add(new Article("/images/wireframe/image.png", new Popup("Matt", "Matt has been a member since July 2014"), "The friction between your thoughts and your code", cate[new Random().nextInt(cate.length - 1)], users[new Random().nextInt(users.length - 1)], new Random().nextInt(100),
                    RelativeDate.format(randomDate("2015-05-11 13:00:00", now), now), users[new Random().nextInt(users.length - 1)]));
        }
        setAttr("lists", list);
        render("template/article.html");
    }

    @ActionKey(value = "/TopHots")
    public void tophostsTest() {
        render("topHotTest.html");
    }

    private static Date randomDate(String beginDate, Date endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = format.parse(beginDate);
            Date end = endDate;
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());

            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = format.parse(beginDate);
            Date end = format.parse(endDate);
            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());

            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin, long end) {
        long rtnn = begin + (long) (Math.random() * (end - begin));
        if (rtnn == begin || rtnn == end) {
            return random(begin, end);
        }
        return rtnn;
    }
}
