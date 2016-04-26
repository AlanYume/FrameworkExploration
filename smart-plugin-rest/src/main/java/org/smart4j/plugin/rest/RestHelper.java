package org.smart4j.plugin.rest;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpInInterceptor;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpPostStreamInterceptor;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpPreStreamInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharingFilter;
import org.smart4j.framework.helper.BeanHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * REST 助手类
 */
public class RestHelper {

    private static final List<Object> providerList = new ArrayList<Object>();
    private static final List<Interceptor<? extends Message>> inInterceptorList =
            new ArrayList<Interceptor<? extends Message>>();
    private static final List<Interceptor<? extends Message>> outInterceptorList =
            new ArrayList<Interceptor<? extends Message>>();

    static {
        // 添加 JSON Provider
        final JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
        providerList.add(jsonProvider);
        // 添加 Logging Interceptor
        if (RestConfig.isLog()) {
            final LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
            inInterceptorList.add(loggingInInterceptor);
            final LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
            outInterceptorList.add(loggingOutInterceptor);
        }
        // 添加 JSONP Interceptor
        if (RestConfig.isJsonp()) {
            final JsonpInInterceptor jsonpInInterceptor = new JsonpInInterceptor();
            jsonpInInterceptor.setCallbackParam(RestConfig.getJsonpFunction());
            inInterceptorList.add(jsonpInInterceptor);
            final JsonpPreStreamInterceptor jsonpPreStreamInterceptor =
                    new JsonpPreStreamInterceptor();
            outInterceptorList.add(jsonpPreStreamInterceptor);
            final JsonpPostStreamInterceptor jsonpPostStreamInterceptor =
                    new JsonpPostStreamInterceptor();
            outInterceptorList.add(jsonpPostStreamInterceptor);
        }
        // 添加 CORS Provider
        if (RestConfig.isCors()) {
            final CrossOriginResourceSharingFilter corsProvider =
                    new CrossOriginResourceSharingFilter();
            corsProvider.setAllowOrigins(RestConfig.getCorsOriginList());
            providerList.add(corsProvider);
        }
    }

    // 发布 REST 服务
    public static void publishService(final String wadl, final Class<?> resourceClass) {
        final JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        factory.setAddress(wadl);
        factory.setResourceClasses(resourceClass);
        factory.setResourceProvider(resourceClass,
                new SingletonResourceProvider(BeanHelper.getBean(resourceClass)));
        factory.setProviders(providerList);
        factory.setInInterceptors(inInterceptorList);
        factory.setOutInterceptors(outInterceptorList);
        factory.create();
    }

    // 创建 REST 客户端
    public static <T> T createClient(final String wadl, final Class<? extends T> resourceClass) {
        return JAXRSClientFactory.create(wadl, resourceClass, providerList);
    }
}
