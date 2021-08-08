package com.restApi.restApiSpringBootApp.advice.exception;

public class EmailSignupFailedCException extends RuntimeException{
    public EmailSignupFailedCException() {
    }

    public EmailSignupFailedCException(String message) {
        super(message);
    }

    public EmailSignupFailedCException(String message, Throwable cause) {
        super(message, cause);
    }
}
