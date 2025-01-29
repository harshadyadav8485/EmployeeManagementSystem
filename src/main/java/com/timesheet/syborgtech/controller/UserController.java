package com.timesheet.syborgtech.controller;


import com.timesheet.syborgtech.dto.request.AssignProjectDto;
import com.timesheet.syborgtech.dto.request.UserLoginRequestDto;
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

    @GetMapping("/v1")
    public SyborgtechResponse getUsers(
            @RequestParam(required = false, name = "searchTerm") String searchTerm,
            @RequestParam(required = false, name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(required = false, name = "recordsPerPage", defaultValue = "1000") Integer recordsPerPage,
            @RequestParam(name = "user_id",required = false)Long userId) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("User Fetched Successfully")
                        .build())
                .data(userService.getUsers(searchTerm,pageNo,recordsPerPage,userId)).build();
    }
  
  @PostMapping("/login")public SyborgtechResponse loginUser(@RequestBody UserLoginRequestDto userLoginRequestDto) throws IOException{  
    return SyborgtechResponse.builder()        
      .status(ApiStatus.builder()          
              .status("SUCCESS")               
              .statusCode("ORD-0001")         
              .statusMessage("Userlogin Successfully")
              .build())        
      .data(userService.loginUser(userLoginRequestDto)).build();}
}