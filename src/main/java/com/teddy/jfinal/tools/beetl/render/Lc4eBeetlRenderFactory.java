package com.teddy.jfinal.tools.beetl.render;

import com.jfinal.render.Render;
import org.apache.log4j.Logger;
import org.beetl.ext.jfinal.BeetlRender;
import org.beetl.ext.jfinal.BeetlRenderFactory;

public class Lc4eBeetlRenderFactory extends BeetlRenderFactory {

    private static Logger log = Logger.getLogger(Lc4eBeetlRenderFactory.class);

    @Override
    public Render getRender(String view) {
        log.debug("Lc4eBeetlRenderFactory start");
        BeetlRender render = new Lc4eBeetlRender(groupTemplate, view);
        log.debug("Lc4eBeetlRenderFactory end");
        return render;
    }
}
