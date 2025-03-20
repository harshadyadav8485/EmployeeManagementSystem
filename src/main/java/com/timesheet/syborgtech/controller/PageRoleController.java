package com.timesheet.syborgtech.controller;

import com.timesheet.syborgtech.dto.request.PageRequestDto;
import com.timesheet.syborgtech.dto.request.Page_RoleRequestDto;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import com.timesheet.syborgtech.dtoCommon.SyborgtechResponse;
import com.timesheet.syborgtech.model.PageRolePermission;
import com.timesheet.syborgtech.service.PageRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/pagerole")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PageRoleController {
    @Autowired
    private PageRoleService pageRoleService;
    @PostMapping("/v1")
    public SyborgtechResponse pageActionMapping(@RequestBody Page_RoleRequestDto page_RoleRequestDto) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Page Role Mapping Created Successfully")
                        .build())
                .data(pageRoleService.pageActionMapping(page_RoleRequestDto)).build();
    }
}
