package com.timesheet.syborgtech.controller;

import com.timesheet.syborgtech.dto.request.ProjectRequestDto;
import com.timesheet.syborgtech.dto.request.SprintRequestDto;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import com.timesheet.syborgtech.dtoCommon.SyborgtechResponse;
import com.timesheet.syborgtech.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/sprint")
public class SprintController {

    @Autowired
    private SprintService sprintService;
    @PostMapping("/v1")
    public SyborgtechResponse createSprint(@RequestBody SprintRequestDto sprintRequestDto) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("Sprint-001")
                        .statusMessage("Sprint Created Successfully")
                        .build())
                .data(sprintService.createSprint(sprintRequestDto)).build();
    }
}
