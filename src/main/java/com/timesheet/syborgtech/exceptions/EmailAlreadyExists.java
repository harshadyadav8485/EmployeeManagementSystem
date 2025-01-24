package com.timesheet.syborgtech.exceptions;

import com.timesheet.syborgtech.Constants.StatusConstants;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import lombok.Getter;

public class EmailAlreadyExists extends RuntimeException{

    @Getter
    private ApiStatus apiStatus;

    private String message;

    public EmailAlreadyExists(String message) {
        super(message);
        this.apiStatus = StatusConstants.EMAIL_ALREADY_EXISTS;
        this.message = message;
    }
}
