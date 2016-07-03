package com.teddy.jfinal.plugin.support.shiro;

import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.config.ResourceConfigurable;
import org.apache.shiro.util.ClassUtils;
import org.apache.shiro.util.LifecycleUtils;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.util.UnknownClassException;
import org.apache.shiro.web.env.EnvironmentLoader;
import org.apache.shiro.web.env.IniWebEnvironment;
import org.apache.shiro.web.env.MutableWebEnvironment;
import org.apache.shiro.web.env.WebEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;

/**
 * Created by teddy on 2015/8/8.
 */
public class ShiroLoader {

    /**
     * Servlet Context config param for specifying the {@link WebEnvironment} implementation class to use:
     * {@code shiroEnvironmentClass}
     */
    public static final String ENVIRONMENT_CLASS_PARAM = "shiroEnvironmentClass";

    /**
     * Servlet Context config param for the resource path to use for configuring the {@link WebEnvironment} instance:
     * {@code shiroConfigLocations}
     */
    public static final String CONFIG_LOCATIONS_PARAM = "shiroConfigLocations";

    public static final String ENVIRONMENT_ATTRIBUTE_KEY = EnvironmentLoader.class.getName() + ".ENVIRONMENT_ATTRIBUTE_KEY";

    private static final Logger log = LoggerFactory.getLogger(EnvironmentLoader.class);


    public WebEnvironment initEnvironment(ServletContext servletContext) throws IllegalStateException {

        if (servletContext.getAttribute(ENVIRONMENT_ATTRIBUTE_KEY) != null) {
            String msg = "There is already a Shiro environment associated with the current ServletContext.  " +
                    "Check if you have multiple EnvironmentLoader* definitions in your web.xml!";
            throw new IllegalStateException(msg);
        }

        servletContext.log("Initializing Shiro environment");
        log.info("Starting Shiro environment initialization.");

        long startTime = System.currentTimeMillis();

        try {
            WebEnvironment environment = createEnvironment(servletContext);
            servletContext.setAttribute(ENVIRONMENT_ATTRIBUTE_KEY, environment);

            log.debug("Published WebEnvironment as ServletContext attribute with name [{}]",
                    ENVIRONMENT_ATTRIBUTE_KEY);

            if (log.isInfoEnabled()) {
                long elapsed = System.currentTimeMillis() - startTime;
                log.info("Shiro environment initialized in {} ms.", elapsed);
            }

            return environment;
        } catch (RuntimeException ex) {
            log.error("Shiro environment initialization failed", ex);
            servletContext.setAttribute(ENVIRONMENT_ATTRIBUTE_KEY, ex);
            throw ex;
        } catch (Error err) {
            log.error("Shiro environment initialization failed", err);
            servletContext.setAttribute(ENVIRONMENT_ATTRIBUTE_KEY, err);
            throw err;
        }
    }

    /**
     * Return the WebEnvironment implementation class to use, either the default
     * {@link IniWebEnvironment} or a custom class if specified.
     *
     * @param servletContext current servlet context
     * @return the WebEnvironment implementation class to use
     * @see #ENVIRONMENT_CLASS_PARAM
     * @see IniWebEnvironment
     */
    protected Class<?> determineWebEnvironmentClass(ServletContext servletContext) {
        String className = servletContext.getInitParameter(ENVIRONMENT_CLASS_PARAM);
        if (className != null) {
            try {
                return ClassUtils.forName(className);
            } catch (UnknownClassException ex) {
                throw new ConfigurationException(
                        "Failed to load custom WebEnvironment class [" + className + "]", ex);
            }
        } else {
            return IniWebEnvironment.class;
        }
    }

    /**
     * Instantiates a {@link WebEnvironment} based on the specified ServletContext.
     * <p/>
     * This implementation {@link #determineWebEnvironmentClass(javax.servlet.ServletContext) determines} a
     * {@link WebEnvironment} implementation class to use.  That class is instantiated, configured, and returned.
     * <p/>
     * This allows custom {@code WebEnvironment} implementations to be specified via a ServletContext init-param if
     * desired.  If not specified, the default {@link IniWebEnvironment} implementation will be used.
     *
     * @param sc current servlet context
     * @return the constructed Shiro WebEnvironment instance
     * @see MutableWebEnvironment
     * @see ResourceConfigurable
     */
    protected WebEnvironment createEnvironment(ServletContext sc) {

        Class<?> clazz = determineWebEnvironmentClass(sc);
        if (!MutableWebEnvironment.class.isAssignableFrom(clazz)) {
            throw new ConfigurationException("Custom WebEnvironment class [" + clazz.getName() +
                    "] is not of required type [" + WebEnvironment.class.getName() + "]");
        }

        String configLocations = sc.getInitParameter(CONFIG_LOCATIONS_PARAM);
        boolean configSpecified = StringUtils.hasText(configLocations);

        if (configSpecified && !(ResourceConfigurable.class.isAssignableFrom(clazz))) {
            String msg = "WebEnvironment class [" + clazz.getName() + "] does not implement the " +
                    ResourceConfigurable.class.getName() + "interface.  This is required to accept any " +
                    "configured " + CONFIG_LOCATIONS_PARAM + "value(s).";
            throw new ConfigurationException(msg);
        }

        MutableWebEnvironment environment = (MutableWebEnvironment) ClassUtils.newInstance(clazz);

        environment.setServletContext(sc);

        if (configSpecified && (environment instanceof ResourceConfigurable)) {
            ((ResourceConfigurable) environment).setConfigLocations(configLocations);
        }

        customizeEnvironment(environment);

        //environment.setWebSecurityManager(defaultWebSecurityManager);


        //LifecycleUtils.init(environment);

        return environment;
    }

    protected void customizeEnvironment(WebEnvironment environment) {
    }

    /**
     * Destroys the {@link WebEnvironment} for the given servlet context.
     *
     * @param servletContext the ServletContext attributed to the WebSecurityManager
     */
    public void destroyEnvironment(ServletContext servletContext) {
        servletContext.log("Cleaning up Shiro Environment");
        try {
            Object environment = servletContext.getAttribute(ENVIRONMENT_ATTRIBUTE_KEY);
            LifecycleUtils.destroy(environment);
        } finally {
            servletContext.removeAttribute(ENVIRONMENT_ATTRIBUTE_KEY);
        }
    }
}
