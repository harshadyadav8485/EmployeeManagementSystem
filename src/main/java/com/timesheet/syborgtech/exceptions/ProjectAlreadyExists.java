package com.timesheet.syborgtech.exceptions;

import com.timesheet.syborgtech.Constants.StatusConstants;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import lombok.Getter;

public class ProjectAlreadyExists extends RuntimeException{
    @Getter
    private ApiStatus apiStatus;
    private String message;

    public ProjectAlreadyExists(String message) {
        super(message);
        this.apiStatus = StatusConstants.PROJECT_NAME_ALREADY_EXISTS;
        this.message = message;
    }
}
