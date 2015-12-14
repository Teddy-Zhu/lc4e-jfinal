package com.teddy.jfinal.handler.resolve;

import com.jfinal.core.Controller;
import com.teddy.jfinal.annotation.SetComVar;
import com.teddy.jfinal.annotation.SetComVars;

/**
 * Created by teddy on 2015/10/25.
 */
public interface AttributeKitI {

    void setComVar(SetComVar comVar, Controller ai) throws Exception;

    void setComVars(SetComVars comVars,Controller ai) throws Exception;
}
