package com.timesheet.syborgtech.exceptions;

import com.timesheet.syborgtech.Constants.StatusConstants;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import lombok.Getter;

public class PageAlreadyExists extends RuntimeException{

    @Getter
    private ApiStatus apiStatus;
    private String message;

    public PageAlreadyExists(String message) {
        super(message);
        this.apiStatus = StatusConstants.PAGE_ALREADY_EXISTS;
        this.message = message;
    }
}
