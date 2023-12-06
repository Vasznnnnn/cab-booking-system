package com.myapps.cabs.exception;


public class UserException extends RuntimeException {

    UserException() {
        super();
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

}
