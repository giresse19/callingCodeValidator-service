package com.rimiTest.CallCodeAndNumber.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessage {
    private Date timestamp;
    private String message;
    private Throwable developerMessage;

    public ErrorMessage(Date timestamp, String message, Throwable developerMessage) {
        this.timestamp = timestamp;
        this.message = message;
        this.developerMessage = developerMessage;
    }
}
