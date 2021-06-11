package com.rimiTest.CallCodeAndNumber.exceptions;

public class MyBadRequestException extends RuntimeException{
    public MyBadRequestException(String message) {
        super(message);
    }
}
