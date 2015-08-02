package com.teddy.lc4e.core.web.service;

import com.teddy.jfinal.annotation.Inject;
import com.teddy.jfinal.annotation.Service;
import com.teddy.jfinal.annotation.Transaction;

import com.teddy.jfinal.exceptions.Lc4eException;

/**
 * Created by teddy on 2015/7/24.
 */
@Service
public class testService {

    public static testService service;
    @Inject
    public testService2 test1;

    @Transaction
    public void test(String a) throws Lc4eException {

    }
}
