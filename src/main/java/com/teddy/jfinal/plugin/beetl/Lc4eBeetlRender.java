package com.teddy.jfinal.plugin.beetl;

import org.apache.log4j.Logger;
import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal.BeetlRender;

public class Lc4eBeetlRender extends BeetlRender {

    private static Logger log = Logger.getLogger(Lc4eBeetlRender.class);

    public Lc4eBeetlRender(GroupTemplate gt, String view) {
        super(gt, view);
    }

    @Override
    public void render() {
        super.render();
    }
}
