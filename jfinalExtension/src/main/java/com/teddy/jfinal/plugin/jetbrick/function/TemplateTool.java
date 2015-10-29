package com.teddy.jfinal.plugin.jetbrick.function;

import com.jfinal.kit.StrKit;
import com.teddy.jfinal.tools.RelativeDate;

import java.util.Date;
import java.util.List;

/**
 * Created by teddy on 2015/9/5.
 */
public class TemplateTool {
    public static Long getTime() {
        return System.currentTimeMillis();
    }

    public static String formatTime(Date date, Date now) {
        return RelativeDate.format(date, now);
    }

    public static boolean isEmpty(String string) {
        return StrKit.isBlank(string);
    }

    public static boolean isEmpty(List<String> string) {
        return string == null || string.size() == 0;
    }
}
