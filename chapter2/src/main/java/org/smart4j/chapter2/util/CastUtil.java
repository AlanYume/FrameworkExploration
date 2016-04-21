package org.smart4j.chapter2.util;

public final class CastUtil {

    public static String castString(final Object obj) {
        return CastUtil.castString(obj, "");
    }

    public static String castString(final Object obj, final String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    public static double castDouble(final Object obj) {
        return CastUtil.castDouble(obj, 0);
    }

    public static double castDouble(final Object obj, final double defaultValue) {
        double doubleValue = defaultValue;
        if (obj != null) {
            final String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (final NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    public static long castLong(final Object obj) {
        return CastUtil.castLong(obj, 0);
    }

    public static long castLong(final Object obj, final long defaultValue) {
        long longValue = defaultValue;
        if (obj != null) {
            final String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    longValue = Long.parseLong(strValue);
                } catch (final NumberFormatException e) {
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    public static int castInt(final Object obj) {
        return CastUtil.castInt(obj, 0);
    }

    public static int castInt(final Object obj, final int defaultValue) {
        int intValue = defaultValue;
        if (obj != null) {
            final String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (final NumberFormatException e) {
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    public static boolean castBoolean(final Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    public static boolean castBoolean(final Object obj, final boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj != null) {
            booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return booleanValue;
    }
}
