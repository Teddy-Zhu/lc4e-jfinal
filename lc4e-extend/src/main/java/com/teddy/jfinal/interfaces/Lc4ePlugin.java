package com.teddy.jfinal.interfaces;

import com.jfinal.aop.Invocation;

import java.util.Set;

/**
 * Created by teddy on 2015/9/29.
 */
public abstract class Lc4ePlugin {

    private Set<String> actionKeys;

    public abstract void init();

    public boolean beforeController(Invocation invocation) throws Exception {
        return true;
    }

    public void afterController(Invocation invocation) throws Exception {

    }


}
