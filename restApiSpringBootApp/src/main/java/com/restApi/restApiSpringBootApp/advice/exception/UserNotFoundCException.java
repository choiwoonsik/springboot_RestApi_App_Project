package com.restApi.restApiSpringBootApp.advice.exception;

public class UserNotFoundCException extends RuntimeException {

    public UserNotFoundCException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundCException(String message) {
        super(message);
    }

    public UserNotFoundCException() {
        super();
    }
}
