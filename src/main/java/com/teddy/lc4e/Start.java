package com.teddy.lc4e;

import com.jfinal.core.JFinal;

/**
 * Created by teddy on 2015/7/18.
 */
public class Start {
    public static void main(String[] args) {
        JFinal.start("src/main/webapp", 8080, "/", 10);
    }
}
