package com.teddy.jfinal.handler.resolve;

import com.jfinal.aop.Invocation;
import com.teddy.jfinal.annotation.SetComVar;
import com.teddy.jfinal.annotation.SetComVars;

/**
 * Created by teddy on 2015/10/25.
 */
public interface AttributeKitI {

    public void setComVar(SetComVar comVar, Invocation ai) throws Exception;

    public void setComVars(SetComVars comVars, Invocation ai) throws Exception;
}
