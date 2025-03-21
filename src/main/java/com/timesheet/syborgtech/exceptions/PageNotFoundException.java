package com.timesheet.syborgtech.exceptions;

import com.timesheet.syborgtech.Constants.StatusConstants;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import lombok.Getter;

public class PageNotFoundException extends RuntimeException{

    @Getter
    private ApiStatus apiStatus;
    private String message;

    public PageNotFoundException (String message){
        super(message);
        this.apiStatus = StatusConstants.PAGE_NOT_FOUND;
        this.message = message;
    }
}
