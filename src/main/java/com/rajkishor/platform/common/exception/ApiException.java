package com.rajkishor.platform.common.exception;

public abstract class ApiException extends RuntimeException {
    protected ApiException(String message) {
        super(message);
    }
}
