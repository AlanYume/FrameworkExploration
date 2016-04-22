package org.smart4j.framework.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSON 工具类
 */
public final class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将 POJO 转为 JSON
     */
    public static <T> String toJson(final T obj) {
        final String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (final Exception e) {
            LOGGER.error("convert POJO to JSON failure", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将 JSON 转为 POJO
     */
    public static <T> T fromJson(final String json, final Class<T> type) {
        final T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(json, type);
        } catch (final Exception e) {
            LOGGER.error("convert JSON to POJO failure", e);
            throw new RuntimeException(e);
        }
        return pojo;
    }
}