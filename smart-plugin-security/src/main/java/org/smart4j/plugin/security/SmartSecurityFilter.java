package org.smart4j.plugin.security;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.CachingSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.smart4j.plugin.security.realm.SmartCustomRealm;
import org.smart4j.plugin.security.realm.SmartJdbcRealm;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 安全过滤器
 */
public class SmartSecurityFilter extends ShiroFilter {

    @Override
    public void init() throws Exception {
        super.init();
        final WebSecurityManager webSecurityManager = super.getSecurityManager();
        // 设置 Realm，可用同时支持多个 Realm， 并按照先后顺序用逗号分割
        setRealms(webSecurityManager);
        // 设置 Cache，用与减少数据库查询次数，降低 I/O 访问
        setCache(webSecurityManager);
    }

    private void setRealms(final WebSecurityManager webSecurityManager) {
        final String securityRealms = SecurityConfig.getRealms();
        if (securityRealms != null) {
            final String[] securityRealmArray = securityRealms.split(",");
            if (securityRealmArray.length > 0) {
                final Set<Realm> realms = new LinkedHashSet<Realm>();
                for (final String securityRealm : securityRealmArray) {
                    if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_JDBC)) {
                        addJdbcRealm(realms);
                    } else if (securityRealm.equalsIgnoreCase(SecurityConstant.REALMS_CUSTOM)) {
                        addCustomRealm(realms);
                    }
                }
                final RealmSecurityManager realmSecurityManager =
                        (RealmSecurityManager) webSecurityManager;
                realmSecurityManager.setRealms(realms);
            }
        }
    }

    private void addJdbcRealm(final Set<Realm> realms) {
        final SmartJdbcRealm smartJdbcRealm = new SmartJdbcRealm();
        realms.add(smartJdbcRealm);
    }

    private void addCustomRealm(final Set<Realm> realms) {
        final SmartSecurity smartSecurity = SecurityConfig.getSmartSecurity();
        final SmartCustomRealm smartCustomRealm = new SmartCustomRealm(smartSecurity);
        realms.add(smartCustomRealm);
    }

    private void setCache(final WebSecurityManager webSecurityManager) {
        if (SecurityConfig.isCache()) {
            final CachingSecurityManager cachingSecurityManager =
                    (CachingSecurityManager) webSecurityManager;
            final CacheManager cacheManager = new MemoryConstrainedCacheManager();
            cachingSecurityManager.setCacheManager(cacheManager);
        }
    }
}
