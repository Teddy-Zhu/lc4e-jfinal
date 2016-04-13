package com.teddy.jfinal.plugin;

import com.jfinal.core.JFinal;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.ehcache.CacheKit;
import com.teddy.jfinal.common.Dict;
import com.teddy.jfinal.plugin.shiro.QuartzSessionValidationScheduler;
import com.teddy.jfinal.plugin.shiro.RetryLimitHashedCredentialsMatcher;
import com.teddy.jfinal.plugin.shiro.ShiroLoader;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.web.env.MutableWebEnvironment;
import org.apache.shiro.web.env.WebEnvironment;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletContext;

/**
 * Created by teddy on 2015/8/7.
 */
public class ShiroPlugin implements IPlugin {

    public static DefaultWebSecurityManager defaultWebSecurityManager;

    private static ServletContext servletContext;

    private static AuthorizingRealm realm;

    @Override
    public boolean start() {
        RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher();
        retryLimitHashedCredentialsMatcher.setHashAlgorithmName(PropPlugin.getValue(Dict.SHIRO_CREDENTIALS_MATCHER_HASHALGORITHMNAME, "md5"));
        retryLimitHashedCredentialsMatcher.setHashIterations(PropPlugin.getInt(Dict.SHIRO_CREDENTIALS_MATCHER_HASHITERATIONS, 2));
        retryLimitHashedCredentialsMatcher.setStoredCredentialsHexEncoded(PropPlugin.getBool(Dict.SHIRO_CREDENTIALS_MATCHER_STOREDCREDENTIALSHEXENCODED, true));
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(CacheKit.getCacheManager());

        JavaUuidSessionIdGenerator sessionIdGenerator = new JavaUuidSessionIdGenerator();

        SimpleCookie sessionIdCookie = new SimpleCookie(PropPlugin.getValue(Dict.SHIRO_SESSION_COOKIENAME, "lc4e"));
        sessionIdCookie.setMaxAge(PropPlugin.getInt(Dict.SHIRO_SESSION_IDCOOKIE_MAXAGE, -1));
        sessionIdCookie.setHttpOnly(PropPlugin.getBool(Dict.SHIRO_SESSION_IDCOOKIE_HTTPONLY, true));

        SimpleCookie rememberMeCookie = new SimpleCookie(PropPlugin.getValue(Dict.SHIRO_SESSION_REMEMBER_COOKIENAME, "rlc4e"));
        rememberMeCookie.setHttpOnly(PropPlugin.getBool(Dict.SHIRO_SESSION_REMEMBER_ME_HTTPONLY, true));
        rememberMeCookie.setMaxAge(PropPlugin.getInt(Dict.SHIRO_SESSION_REMEMBER_ME_MAXAGE, 2592000));

        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCipherKey(Base64.decode(PropPlugin.getValue(Dict.SECURITY_KEY, "4AvVhmFLUs0KTA3Kprsdag==")));
        rememberMeManager.setCookie(rememberMeCookie);

        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        // sessionDAO.setActiveSessionsCacheName(Const.SHIRO_ACTIVE_SESSION_CACHE);
        sessionDAO.setSessionIdGenerator(sessionIdGenerator);
        sessionDAO.setCacheManager(ehCacheManager);

        QuartzSessionValidationScheduler sessionValidationScheduler = new QuartzSessionValidationScheduler();
        sessionValidationScheduler.setSessionValidationInterval(PropPlugin.getLong(Dict.SHIRO_SESSION_VALIDATIONINTERVAL, 1800000L));

        realm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher);
        realm.setCachingEnabled(false);
        realm.setCacheManager(ehCacheManager);

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(PropPlugin.getLong(Dict.SHIRO_SESSION_GLOBALSESSIONTIMEOUT, 1800000L));
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionValidationInterval(PropPlugin.getLong(Dict.SHIRO_SESSION_VALIDATIONINTERVAL, 360000L));
        sessionManager.setSessionValidationScheduler(sessionValidationScheduler);
        sessionManager.setSessionIdCookieEnabled(PropPlugin.getBool(Dict.SHIRO_SESSION_IDCOOKIEENABLED, true));
        sessionManager.setSessionIdCookie(sessionIdCookie);


        sessionValidationScheduler.setSessionManager(sessionManager);

        defaultWebSecurityManager = new DefaultWebSecurityManager(realm);
        defaultWebSecurityManager.setCacheManager(ehCacheManager);
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager);
        defaultWebSecurityManager.setSessionManager(sessionManager);


        servletContext = JFinal.me().getServletContext();

        ShiroLoader shiroLoader = new ShiroLoader();

        shiroLoader.initEnvironment(servletContext);
        WebEnvironment environment = (WebEnvironment) servletContext.getAttribute(ShiroLoader.ENVIRONMENT_ATTRIBUTE_KEY);
        ((MutableWebEnvironment) environment).setWebSecurityManager(defaultWebSecurityManager);
        servletContext.setAttribute(ShiroLoader.ENVIRONMENT_ATTRIBUTE_KEY, environment);

        return true;
    }


    @Override
    public boolean stop() {


        return true;
    }

    public static void setRealm(AuthorizingRealm realm) {
        ShiroPlugin.realm = realm;
    }
}

