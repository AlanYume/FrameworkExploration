package org.smart4j.chapter2.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * add property file
     */
    public static Properties loadProps(final String fileName) {
        Properties props = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + " file is not found");
            }
            props = new Properties();
            props.load(is);
        } catch (final IOException e) {
            LOGGER.error("load properties file failure", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (final IOException e) {
                    LOGGER.error("close input stream failure", e);
                }
            }
        }
        return props;
    }

    /**
     * get string property (default is null)
     */
    public static String getString(final Properties props, final String key) {
        return getString(props, key, "");
    }

    /**
     * get string property
     */
    public static String getString(final Properties props, final String key,
            final String defaultValue) {
        String value = defaultValue;
        if (props.containsKey(key)) {
            value = props.getProperty(key);
        }
        return value;
    }

    /**
     * get int property (default is 0)
     */
    public static int getInt(final Properties props, final String key) {
        return getInt(props, key, 0);
    }

    /**
     * get int property
     */
    public static int getInt(final Properties props, final String key, final int defaultValue) {
        int value = defaultValue;
        if (props.containsKey(key)) {
            value = CastUtil.castInt(props.getProperty(key));
        }
        return value;
    }

    /**
     * get boolean property (default is false)
     */
    public static boolean getBoolean(final Properties props, final String key) {
        return getBoolean(props, key, false);
    }

    /**
     * get boolean property
     */
    public static boolean getBoolean(final Properties props, final String key,
            final boolean defaultValue) {
        boolean value = defaultValue;
        if (props.containsKey(key)) {
            value = CastUtil.castBoolean(props.getProperty(key));
        }
        return value;
    }
}
