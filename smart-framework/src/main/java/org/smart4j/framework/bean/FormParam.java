package org.smart4j.framework.bean;

/**
 * 封装表单参数
 */
public class FormParam {

    private final String fieldName;
    private final Object fieldValue;

    public FormParam(final String fieldName, final Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
