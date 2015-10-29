package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.ValidateComVar;
import com.teddy.jfinal.annotation.ValidateComVars;

/**
 * Created by teddy on 2015/10/25.
 */
public interface ValidateKitI {

    public void resolveComVars(ValidateComVars comVars, Invocation invocation) throws Exception;

    public void resolveComVar(ValidateComVar comVars, Invocation invocation) throws Exception;

}
