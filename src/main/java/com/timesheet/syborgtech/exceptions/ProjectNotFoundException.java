package com.timesheet.syborgtech.exceptions;

import com.timesheet.syborgtech.Constants.StatusConstants;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import lombok.Getter;

public class ProjectNotFoundException extends RuntimeException{
    @Getter
    private ApiStatus apiStatus;
    private String message;

    public ProjectNotFoundException(String message) {
        super(message);
        this.apiStatus = StatusConstants.PROJECT_NOT_FOUND;
        this.message = message;
    }
}
