package com.timesheet.syborgtech.exceptions;

import com.timesheet.syborgtech.Constants.StatusConstants;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import lombok.Getter;

public class SprintNameAlreadyExists extends RuntimeException{

    @Getter
    private ApiStatus apiStatus;

    private String message;

    public SprintNameAlreadyExists(String message) {
        super(message);
        this.apiStatus = StatusConstants.SPRINT_NAME_ALREADY_EXISTS;
        this.message = message;
    }
}
