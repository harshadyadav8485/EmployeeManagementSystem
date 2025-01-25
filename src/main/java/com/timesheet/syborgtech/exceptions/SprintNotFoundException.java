package com.timesheet.syborgtech.exceptions;

import com.timesheet.syborgtech.Constants.StatusConstants;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import lombok.Getter;

public class SprintNotFoundException extends RuntimeException{

    @Getter
    private ApiStatus apiStatus;
    private String message;

    public SprintNotFoundException(String message) {
        super(message);
        this.apiStatus = StatusConstants.USER_NOT_FOUND;
        this.message = message;
    }
}
