package com.timesheet.syborgtech.controller;

import com.timesheet.syborgtech.dto.response.SubTaskRequestDto;
import com.timesheet.syborgtech.dto.response.TaskRequestDto;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import com.timesheet.syborgtech.dtoCommon.SyborgtechResponse;
import com.timesheet.syborgtech.service.SubTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/subtask")
public class SubTaskController {

    @Autowired
    private SubTaskService subTaskService;
    @PostMapping("/v1")
    public SyborgtechResponse createSubTask(@RequestBody SubTaskRequestDto subTaskRequestDto) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("Sprint-001")
                        .statusMessage("Task Created Successfully")
                        .build())
                .data(subTaskService.createSubTask(subTaskRequestDto)).build();
    }
}
