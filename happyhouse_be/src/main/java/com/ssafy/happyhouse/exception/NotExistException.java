package com.ssafy.happyhouse.exception;

public class NotExistException extends RuntimeException{

    public NotExistException() {
        super();
    }

    public NotExistException(String message) {
        super(message);
    }
}
