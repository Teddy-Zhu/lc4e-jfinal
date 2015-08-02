package com.teddy.lc4e.core.web.controller;

import com.jfinal.core.ActionKey;
import com.teddy.jfinal.exceptions.Lc4eException;
import com.teddy.jfinal.annotation.Controller;
import com.teddy.jfinal.annotation.Inject;
import com.teddy.jfinal.annotation.ValidateParam;
import com.teddy.jfinal.interfaces.BaseController;
import com.teddy.lc4e.core.web.service.testService;


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

    public void index() {
        renderText("It's demo");
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
}
