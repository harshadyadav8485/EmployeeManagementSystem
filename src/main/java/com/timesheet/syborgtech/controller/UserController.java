package com.timesheet.syborgtech.controller;


import com.google.zxing.WriterException;
import com.timesheet.syborgtech.dto.request.AssignProjectDto;
import com.timesheet.syborgtech.dto.request.UserLoginRequestDto;
import com.timesheet.syborgtech.dto.request.UserRegistrationRequest;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import com.timesheet.syborgtech.dtoCommon.SyborgtechResponse;
import com.timesheet.syborgtech.model.User;
import com.timesheet.syborgtech.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.zxing.WriterException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/v1")
    public SyborgtechResponse createUser(@RequestBody UserRegistrationRequest userRegistrationRequest) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("User Created Successfully")
                        .build())
                .data(userService.createUser(userRegistrationRequest)).build();
    }

    @PostMapping("/v1/assignProject")
    public SyborgtechResponse assignProjects(@RequestBody AssignProjectDto assignProjectDto) throws IOException {
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
            @RequestParam(name = "user_id", required = false) Long userId) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("User Fetched Successfully")
                        .build())
                .data(userService.getUsers(searchTerm, pageNo, recordsPerPage, userId)).build();
    }

    @PostMapping("/login")
    public SyborgtechResponse loginUser(@RequestBody UserLoginRequestDto userLoginRequestDto) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Userlogin Successfully")
                        .build())
                .data(userService.loginUser(userLoginRequestDto)).build();
    }

    @GetMapping("/v1/getWithPayload")
    public SyborgtechResponse getWithPayload(@RequestBody AssignProjectDto assignProjectDto) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("User and projects Successfully")
                        .build())
                .data(userService.getWithPayload(assignProjectDto)).build();
    }

    @PostMapping("/generate")
    public ResponseEntity<User> generateQRCode(@RequestParam String name, @RequestParam String email)
            throws WriterException, IOException {
        User user = userService.createUser(name, email);
        return ResponseEntity.ok(user);
    }

    // Get QR Code API
    @GetMapping("/{id}/qrcode")
    public ResponseEntity<byte[]> getQRCode(@PathVariable Long id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent() && user.get().getQrCode() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(user.get().getQrCode(), headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
}