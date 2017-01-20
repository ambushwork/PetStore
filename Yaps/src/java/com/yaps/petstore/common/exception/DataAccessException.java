package com.yaps.petstore.common.exception;

/**
 * This exception is throw when a general problem occurs in the persistent layer.
 */
public final class DataAccessException extends RuntimeException {

    public DataAccessException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
