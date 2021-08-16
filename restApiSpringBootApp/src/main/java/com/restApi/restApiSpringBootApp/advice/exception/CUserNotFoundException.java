package com.restApi.restApiSpringBootApp.advice.exception;

import com.restApi.restApiSpringBootApp.domain.user.User;

public class CUserNotFoundException extends RuntimeException {

    public CUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CUserNotFoundException(String message) {
        super(message);
    }

    public CUserNotFoundException() {
        super();
    }
}
