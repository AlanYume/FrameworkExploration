package org.smart4j.plugin.security.exception;

/**
 * 认证异常（当非法访问时抛出）
 */
public class AuthcException extends Exception {

    public AuthcException() {
        super();
    }

    public AuthcException(final String message) {
        super(message);
    }

    public AuthcException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AuthcException(final Throwable cause) {
        super(cause);
    }
}
