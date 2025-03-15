package com.timesheet.syborgtech.controller;

import com.timesheet.syborgtech.dto.request.RoleDtoRequest;
import com.timesheet.syborgtech.dto.request.UserRegistrationRequest;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import com.timesheet.syborgtech.dtoCommon.SyborgtechResponse;
import com.timesheet.syborgtech.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/role")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RoleController {
    @Autowired
    private RoleService roleService;


    @PostMapping("/v1")
    public SyborgtechResponse createRole(@RequestBody RoleDtoRequest roleDtoRequest) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Role Created Successfully")
                        .build())
                .data(roleService.createRole(roleDtoRequest)).build();
    }

    @GetMapping("/v1")
    public SyborgtechResponse getRoles(
            @RequestParam(required = false, name = "searchTerm") String searchTerm,
            @RequestParam(required = false, name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(required = false, name = "recordsPerPage", defaultValue = "1000") Integer recordsPerPage,
            @RequestParam(name = "role_id",required = false)Long roleId) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Roles Fetched Successfully")
                        .build())
                .data(roleService.getRoles(searchTerm,pageNo,recordsPerPage,roleId)).build();
    }

    @PutMapping("/v1")
    public SyborgtechResponse updateRole(@RequestBody RoleDtoRequest roleDtoRequest) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Role Updated Successfully")
                        .build())
                .data(roleService.updateRole(roleDtoRequest)).build();
    }

    @GetMapping("/v1/pageView")
    public SyborgtechResponse pageDetailsViewList(
            @RequestParam(name = "roleName")String roleName) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Roles Fetched Successfully")
                        .build())
                .data(roleService.pageDetailsViewList(roleName)).build();
    }
}
