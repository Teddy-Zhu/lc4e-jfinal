package com.teddy.jfinal.plugin.beetl;

import com.teddy.jfinal.tools.RelativeDate;

import java.util.Date;

/**
 * Created by teddy on 2015/9/5.
 */
public class BeetlTool {
    public Long getTime() {
        return System.currentTimeMillis();
    }

    public String formatTime(Date date, Date now) {
        return RelativeDate.format(date, now);
    }
}
