package com.teddy.jfinal.handler.resolve;

import com.jfinal.core.Controller;
import com.teddy.jfinal.annotation.ValidateComVar;
import com.teddy.jfinal.annotation.ValidateComVars;

/**
 * Created by teddy on 2015/10/25.
 */
public interface ValidateKitI {

    void resolveComVars(ValidateComVars comVars, Controller invocation) throws Exception;

    void resolveComVar(ValidateComVar comVars, Controller invocation) throws Exception;

}
