package com.timesheet.syborgtech.controller;


import com.timesheet.syborgtech.dto.request.AssignProjectDto;
import com.timesheet.syborgtech.dto.request.UserRegistrationRequest;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import com.timesheet.syborgtech.dtoCommon.SyborgtechResponse;
import com.timesheet.syborgtech.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/v1")
    public SyborgtechResponse createUser(@RequestBody UserRegistrationRequest userRegistrationRequest) throws IOException{
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("User Created Successfully")
                        .build())
                .data(userService.createUser(userRegistrationRequest)).build();
    }

    @PostMapping("/v1/assignProject")
    public SyborgtechResponse assignProjects(@RequestBody AssignProjectDto assignProjectDto) throws IOException{
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("User and projects Successfully")
                        .build())
                .data(userService.assignProjects(assignProjectDto)).build();
    }
}