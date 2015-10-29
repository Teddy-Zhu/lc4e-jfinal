package com.teddy.jfinal.plugin.shiro;

import com.jfinal.plugin.ehcache.CacheKit;
import com.teddy.jfinal.common.Const;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;


public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();
        //retry count + 1
        AtomicInteger retryCount = CacheKit.get(Const.PASSWORD_RETRY_CACHE,username);
        if(retryCount == null) {
            retryCount = new AtomicInteger(0);
            CacheKit.put(Const.PASSWORD_RETRY_CACHE,username, retryCount);
        }
        if(retryCount.incrementAndGet() > 5) {
            //if retry count > 5 throw
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
            //clear retry count
            CacheKit.remove(Const.PASSWORD_RETRY_CACHE,username);
        }
        return matches;
    }
}
