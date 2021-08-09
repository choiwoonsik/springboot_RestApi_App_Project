package com.restApi.restApiSpringBootApp.advice.exception;

public class CEmailLoginFailedException extends RuntimeException {
    public CEmailLoginFailedException() {
        super();
    }

    public CEmailLoginFailedException(String message) {
        super(message);
    }

    public CEmailLoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
