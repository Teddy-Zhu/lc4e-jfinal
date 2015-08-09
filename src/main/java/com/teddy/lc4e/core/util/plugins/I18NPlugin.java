package com.teddy.lc4e.core.util.plugins;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.jfinal.log.Logger;
import com.jfinal.plugin.IPlugin;


public class I18NPlugin implements IPlugin {

    protected final Logger log = Logger.getLogger(getClass());

    private static final Map<String, Map<String, String>> resourceBundleMap = new HashMap<String, Map<String, String>>();


    public static String i18n(String i18n) {
        String val = "_zhcn";

        if (i18n.equals("zh") || i18n.equals("zh_cn")) {
            val = "_zhcn";

        } else if (i18n.equals("en") || i18n.equals("en_us")) {
            val = "_enus";

        } else if (i18n.equals("ja") || i18n.equals("ja_jp")) {
            val = "_ja";

        } else if (i18n.equals("zh_hk")) {
            val = "_zhhk";

        } else if (i18n.equals("zh_tw")) {
            val = "_zhtw";

        }

        return val;
    }


    public static Map<String, String> get(String localePramKey) {
        if (localePramKey.equals("zh")) {
            localePramKey = "zh_cn";

        } else if (localePramKey.equals("en")) {
            localePramKey = "en_us";

        }

        Map<String, String> map = resourceBundleMap.get(localePramKey);
        if (map != null) {
            return map;

        } else {
            return resourceBundleMap.get("zh_cn");

        }
    }

    public static String get(String i18n, String key) {
        Map<String, String> map = get(i18n);
        return map.get(key);
    }

    @Override
    public boolean start() {
        String[] languages = {
                "zh_CN",
                "zh_HK",
                "zh_TW",
                "en_US",
                "ja"
        };

        String fileName = null;

        for (String language : languages) {
            fileName = "/message_" + language + ".properties";
            InputStream inputStream = null;
            try {

                inputStream = I18NPlugin.class.getResourceAsStream(fileName);

                Properties properties = new Properties();
                properties.load(inputStream);

                Map<String, String> i18nMap = new HashMap<String, String>();
                Enumeration<Object> keys = properties.keys();
                while (keys.hasMoreElements()) {
                    String key = (String) keys.nextElement();
                    String value = properties.getProperty(key);
                    i18nMap.put(key, value);
                }
                resourceBundleMap.put(language.toLowerCase(), i18nMap);
            } catch (Exception exception) {
                log.info("get properties error...");
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    log.info("close properties error...");
                }
            }
        }
        return true;
    }

    @Override
    public boolean stop() {
        resourceBundleMap.clear();
        return true;
    }

}
