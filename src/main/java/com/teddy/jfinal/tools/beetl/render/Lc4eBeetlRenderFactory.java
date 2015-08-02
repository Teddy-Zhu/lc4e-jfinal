package com.teddy.jfinal.tools.beetl.render;

import org.apache.log4j.Logger;
import org.beetl.ext.jfinal.BeetlRender;
import org.beetl.ext.jfinal.BeetlRenderFactory;

import com.jfinal.render.Render;

public class Lc4eBeetlRenderFactory extends BeetlRenderFactory {

    private static Logger log = Logger.getLogger(Lc4eBeetlRenderFactory.class);

    public Render getRender(String view) {
        log.debug("Lc4eBeetlRenderFactory start");
        BeetlRender render = new Lc4eBeetlRender(groupTemplate, view);
        log.debug("Lc4eBeetlRenderFactory end");
        return render;
    }
}
