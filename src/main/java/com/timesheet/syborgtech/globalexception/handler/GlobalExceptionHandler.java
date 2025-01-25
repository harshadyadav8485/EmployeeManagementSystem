package com.timesheet.syborgtech.globalexception.handler;

import com.timesheet.syborgtech.Constants.StatusConstants;
import com.timesheet.syborgtech.dtoCommon.SyborgtechResponse;
import com.timesheet.syborgtech.exceptions.*;
import com.timesheet.syborgtech.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InvalidOTPError.class)
    public ResponseEntity<SyborgtechResponse> handleShipperNotFoundError(InvalidOTPError e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(
                SyborgtechResponse.builder().status(StatusConstants.OTP_NOT_CORRECT).build(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailAlreadyExists.class)
    public ResponseEntity<SyborgtechResponse> emailAlreadyExists(EmailAlreadyExists e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(
                SyborgtechResponse.builder().status(StatusConstants.EMAIL_ALREADY_EXISTS).build(), HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(RoleAlreadyExists.class)
    public ResponseEntity<SyborgtechResponse> roleAlreadyExists(RoleAlreadyExists e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(
                SyborgtechResponse.builder().status(StatusConstants.ROLE_ALREADY_EXISTS).build(), HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<SyborgtechResponse> roleNotFoundException(RoleNotFoundException e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(
                SyborgtechResponse.builder().status(StatusConstants.ROLE_NOT_FOUND).build(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserNameAlreadyExists.class)
    public ResponseEntity<SyborgtechResponse> userNameAlreadyExists(UserNameAlreadyExists e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(
                SyborgtechResponse.builder().status(StatusConstants.USER_NAME_ALREADY_EXISTS).build(), HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ProjectAlreadyExists.class)
    public ResponseEntity<SyborgtechResponse> projectAlreadyExists(ProjectAlreadyExists e){
        logger.error(e.getMessage());
        return new ResponseEntity<>
                (SyborgtechResponse.builder().status(StatusConstants.PROJECT_NAME_ALREADY_EXISTS).build(),HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SprintNameAlreadyExists.class)
    public ResponseEntity<SyborgtechResponse> sprintAlreadyExists(SprintNameAlreadyExists e){
        logger.error(e.getMessage());
        return new ResponseEntity<>
                (SyborgtechResponse.builder().status(StatusConstants.SPRINT_NAME_ALREADY_EXISTS).build(),HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<SyborgtechResponse> projectNotFoundException(ProjectNotFoundException e) {
        logger.error(e.getMessage());
        return new ResponseEntity<>(
                SyborgtechResponse.builder().status(StatusConstants.PROJECT_NOT_FOUND).build(), HttpStatus.NOT_FOUND);
    }
}
