package org.smart4j.framework.bean;

/**
 * 返回数据对象
 */
public class Data {

    /**
     * 模型数据
     */
    private final Object model;

    public Data(final Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
