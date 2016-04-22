package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Request;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 */
public final class ControllerHelper {

    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        final Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtil.isNotEmpty(controllerClassSet)) {
            for (final Class<?> controllerClass : controllerClassSet) {
                final Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(methods)) {
                    for (final Method method : methods) {
                        if (method.isAnnotationPresent(Action.class)) {
                            final Action action = method.getAnnotation(Action.class);
                            final String mapping = action.value();
                            if (mapping.matches("\\w+:/\\w*")) {
                                final String[] array = mapping.split(":");
                                if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                                    final String requestMethod = array[0];
                                    final String requestPath = array[1];
                                    final Request request = new Request(requestMethod, requestPath);
                                    final Handler handler = new Handler(controllerClass, method);
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取 Handler
     */
    public static Handler getHandler(final String requestMethod, final String requestPath) {
        final Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
