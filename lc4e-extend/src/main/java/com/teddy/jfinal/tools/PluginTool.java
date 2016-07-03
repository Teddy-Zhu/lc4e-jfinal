package com.teddy.jfinal.tools;

import com.teddy.jfinal.config.Config;
import com.teddy.jfinal.interfaces.IPlugin;
import com.teddy.jfinal.plugin.CustomPlugin;
import org.apache.log4j.Logger;

import java.util.List;

import static org.apache.log4j.Logger.getLogger;

/**
 * Created by teddyzhu on 16/7/3.
 */
public class PluginTool {


    private static final Logger logger = getLogger(PluginTool.class);

    public static boolean startJfinalPluin(List<com.jfinal.plugin.IPlugin> plugins) {
        for (com.jfinal.plugin.IPlugin plugin : plugins) {
            if (!plugin.start()) {
                logger.error(String.format("plugin {0} start error", plugin.getClass()));
                return false;
            }
        }
        return true;
    }

    public static boolean startLc4ePluginOrigin(List<IPlugin> plugins) {
        for (IPlugin plugin : plugins) {
            if (!plugin.start()) {
                logger.error(String.format("plugin {0} start error", plugin.getClass()));
                return false;
            }

        }
        return true;
    }

    public static boolean startLc4ePlugin(List<IPlugin> plugins, CustomPlugin customPlugin) {
        for (IPlugin plugin : plugins) {
            if (!plugin.start(customPlugin)) {
                logger.error(String.format("plugin {0} start(CustomPlugin) error", plugin.getClass()));
                return false;
            }

        }
        return true;
    }

    public static boolean stopJifnalPlugin(List<com.jfinal.plugin.IPlugin> plugins) {
        for (com.jfinal.plugin.IPlugin plugin : plugins) {
            if (!plugin.stop()) {
                logger.error(String.format("plugin {0} stop() error", plugin.getClass()));
                return false;
            }

        }
        return true;
    }

    public static boolean stopLc4ePluginOrigin(List<IPlugin> plugins) {
        for (IPlugin plugin : plugins) {
            if (!plugin.stop()) {
                logger.error(String.format("plugin {0} stop() error", plugin.getClass()));
                return false;
            }

        }
        return true;
    }

    public static boolean stopLc4ePlugin(List<IPlugin> plugins, CustomPlugin customPlugin) {
        for (IPlugin plugin : plugins) {
            if (!plugin.stop(customPlugin)) {
                logger.error(String.format("plugin {0} stop(CustomPlugin) error", plugin.getClass()));
                return false;
            }

        }
        return true;
    }
}
