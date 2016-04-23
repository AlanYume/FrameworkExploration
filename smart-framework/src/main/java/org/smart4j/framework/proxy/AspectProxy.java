package org.smart4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理
 */
public abstract class AspectProxy implements Proxy {

    private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public final Object doProxy(final ProxyChain proxyChain) throws Throwable {
        Object result = null;

        final Class<?> cls = proxyChain.getTargetClass();
        final Method method = proxyChain.getTargetMethod();
        final Object[] params = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (final Exception e) {
            logger.error("proxy failure", e);
            error(cls, method, params, e);
            throw e;
        } finally {
            end();
        }

        return result;
    }

    public void begin() {
    }

    public boolean intercept(final Class<?> cls, final Method method, final Object[] params)
            throws Throwable {
        return true;
    }

    public void before(final Class<?> cls, final Method method, final Object[] params)
            throws Throwable {
    }

    public void after(final Class<?> cls, final Method method, final Object[] params,
            final Object result) throws Throwable {
    }

    public void error(final Class<?> cls, final Method method, final Object[] params,
            final Throwable e) {
    }

    public void end() {
    }
}