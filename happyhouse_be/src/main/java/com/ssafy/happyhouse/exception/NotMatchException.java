package com.ssafy.happyhouse.exception;

public class NotMatchException extends RuntimeException {

    public NotMatchException() {
        super();
    }

    public NotMatchException(String message) {
        super(message);
    }
}
