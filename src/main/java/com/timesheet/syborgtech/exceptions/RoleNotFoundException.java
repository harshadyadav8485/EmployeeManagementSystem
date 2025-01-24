package com.timesheet.syborgtech.exceptions;

import com.timesheet.syborgtech.Constants.StatusConstants;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import lombok.Getter;

public class RoleNotFoundException  extends RuntimeException{

    @Getter
    private ApiStatus apiStatus;

    private String message;

    public RoleNotFoundException(String message) {
        super(message);
        this.apiStatus = StatusConstants.ROLE_NOT_FOUND;
        this.message = message;
    }
}
