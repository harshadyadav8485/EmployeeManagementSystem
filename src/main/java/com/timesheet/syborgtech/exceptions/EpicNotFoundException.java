package com.timesheet.syborgtech.exceptions;

import com.timesheet.syborgtech.Constants.StatusConstants;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import lombok.Getter;

public class EpicNotFoundException extends RuntimeException{

    @Getter
    private ApiStatus apiStatus;
    private String message;

   public EpicNotFoundException (String message){
        super(message);
        this.apiStatus = StatusConstants.EPIC_NOT_FOUND;
        this.message = message;
    }
}
