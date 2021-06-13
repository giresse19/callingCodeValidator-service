package com.rimiTest.CallCodeAndNumber.persistence.model;

public enum ErrorMessages {
    API_DATA_NOT_FETCHED("Fetching of API data failed from data source, Please try again later."),
    INVALID_INPUT("Please enter a valid call code and phone number.");

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public  String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
