package org.smart4j.framework.bean;

import java.lang.reflect.Method;

/**
 * 封装 Action 信息
 */
public class Handler {

    /**
     * Controller 类
     */
    private final Class<?> controllerClass;

    /**
     * Action 方法
     */
    private final Method actionMethod;

    public Handler(final Class<?> controllerClass, final Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
