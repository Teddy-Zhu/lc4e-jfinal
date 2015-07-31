package com.teddy.jfinal.tools;

import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.handler.DBHelper;
import com.teddy.jfinal.handler.support.TransactionKit;

/**
 * Created by teddy on 2015/7/26.
 */
public class DBTool {

    public static <T> T auto(Class<T> target) {
        if (target.isAnnotationPresent(Model.class)) {
            return DBHelper.Proxy(target);
        } else {
            try {
                return target.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> T auto(T target) {
        if (target.getClass().isAnnotationPresent(Model.class)) {
            return DBHelper.Proxy(target);
        } else {
            return target;
        }
    }

    public static <T> T transaction(T target) {
        return TransactionKit.Proxy(target);
    }

    public static <T> T transaction(Class<T> target) {
        return TransactionKit.Proxy(target);
    }

}
