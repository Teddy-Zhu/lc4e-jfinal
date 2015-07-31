package com.teddy.jfinal.tools.beetl.func;

import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.beetl.core.Context;
import org.beetl.core.Function;

/**
 * 国际化输出
 *
 * @author 董华健
 *         <p>
 *         说明：beetl本身自带了strutil.format ${ strutil.format (“hello,{0}, my age is {1}”,”joeli”,15),输出是hello,joelli, my age is 15. 具体请参考
 *         http://docs.oracle.com/javase/6/docs/api/java/text/MessageFormat.html
 */
public class I18nFormat implements Function {

    private static Logger LOGGER = Logger.getLogger(I18nFormat.class);

    /**
     * 过滤xml文档函数实现
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object call(Object[] arg, Context context) {
        if (arg.length == 0) {
            return null;
        }

        Map<String, String> i18nMap = (Map<String, String>) context.getGlobal("i18nMap");
        String formatTemplateKey = (String) arg[0];
        String formatTemplate = i18nMap.get(formatTemplateKey);


        LinkedList<Object> paramValue = new LinkedList<Object>();
        for (int i = 1, length = arg.length; i < length; i++) {
            paramValue.add(arg[i]);
        }

        String value = String.format(formatTemplate, paramValue.toArray());

        return value;
    }

}
