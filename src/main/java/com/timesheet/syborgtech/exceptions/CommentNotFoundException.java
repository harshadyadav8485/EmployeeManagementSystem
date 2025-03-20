package com.timesheet.syborgtech.exceptions;

import com.timesheet.syborgtech.Constants.StatusConstants;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import lombok.Getter;

public class CommentNotFoundException extends RuntimeException{

    @Getter
    private ApiStatus apiStatus;
    private String message;

    public CommentNotFoundException(String message){
        super(message);
        this.apiStatus = StatusConstants.COMMENT_NOT_FOUND;
        this.message = message;
    }
}
