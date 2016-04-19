package com.teddy.jfinal.interfaces;

import com.jfinal.core.Controller;
import com.teddy.jfinal.entity.ReturnData;
import com.teddy.jfinal.plugin.jetbrick.Lc4eCaptchaRender;
import com.teddy.jfinal.tools.WebTool;

/**
 * Created by teddy on 2015/7/20.
 */
public abstract class BaseController extends Controller {

    protected boolean isPJAX() {
        return WebTool.isPJAX(getRequest());
    }

    protected boolean isAJAX() {
        return WebTool.isAJAX(getRequest());
    }

    protected boolean isPOST() {
        return WebTool.isPOST(getRequest());
    }

    protected BaseController setAttr(ReturnData data) {
        setAttr(data.getName(), data.getData());
        return this;
    }

    protected BaseController setAttrs(ReturnData... datas) {
        for (ReturnData data :
                datas) {
            setAttr(data);
        }
        return this;
    }
}
