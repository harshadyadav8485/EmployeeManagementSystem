package com.timesheet.syborgtech.exceptions;

import com.timesheet.syborgtech.Constants.StatusConstants;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import lombok.Getter;

public class InvalidOTPError extends RuntimeException{

    @Getter
    private ApiStatus apiStatus;

    private String message;

    public InvalidOTPError(String message) {
        super(message);
        this.apiStatus = StatusConstants.OTP_NOT_CORRECT;
        this.message = message;
    }
}

