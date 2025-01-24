package com.timesheet.syborgtech.Constants;

import com.timesheet.syborgtech.dtoCommon.ApiStatus;

public class StatusConstants {
    public static final ApiStatus OTP_NOT_CORRECT = ApiStatus.builder()
            .statusMessage("Otp not correct!")
            .statusCode("OTP-1001")
            .status("OTP_NOT_CORRECT")
            .build();

    public static final ApiStatus EMAIL_ALREADY_EXISTS = ApiStatus.builder()
            .statusMessage("Email ALREADY EXISTS!")
            .statusCode("EMAIL-001")
            .status("Email ALREADY EXISTS")
            .build();


    public static final ApiStatus ROLE_ALREADY_EXISTS = ApiStatus.builder()
            .statusMessage("ROLE ALREADY EXISTS!")
            .statusCode("ROLE-001")
            .status("ROLE ALREADY EXISTS")
            .build();

    public static final ApiStatus ROLE_NOT_FOUND = ApiStatus.builder()
            .statusMessage("ROLE NOT FOUND!")
            .statusCode("ROLE-001")
            .status("ROLE NOT FOUND")
            .build();

    public static final ApiStatus USER_NAME_ALREADY_EXISTS = ApiStatus.builder()
            .statusMessage("USER NAME ALREADY EXISTS!")
            .statusCode("USER-001")
            .status("USER_NAME_ALREADY_EXISTS")
            .build();

    public static final ApiStatus PROJECT_NAME_ALREADY_EXISTS = ApiStatus.builder()
            .statusMessage("PROJECT NAME ALREADY EXISTS!")
            .statusCode("USER-001")
            .status("PROJECT_NAME_ALREADY_EXISTS")
            .build();
}
