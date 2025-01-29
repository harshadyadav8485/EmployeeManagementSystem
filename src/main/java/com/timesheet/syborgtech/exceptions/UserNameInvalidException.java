package com.timesheet.syborgtech.exceptions;

import com.timesheet.syborgtech.Constants.StatusConstants;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import lombok.Getter;

public class UserNameInvalidException extends RuntimeException{
    @Getter
    private ApiStatus apiStatus;
    private String message;

    public UserNameInvalidException(String message) {
        super(message);
        this.apiStatus = StatusConstants.USER_NAME_INVALID;
        this.message = message;
    }
}
