package com.yaps.petstore.common.locator;

/**
 * This unchecked exception is throw when a location problem occurs.
 */
public class ServiceLocatorException extends RuntimeException {

    public ServiceLocatorException(Throwable cause) {
        super(cause);
    }

}