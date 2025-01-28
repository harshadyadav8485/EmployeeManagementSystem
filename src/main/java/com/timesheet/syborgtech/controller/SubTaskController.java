package com.timesheet.syborgtech.controller;

import com.timesheet.syborgtech.dto.response.SubTaskRequestDto;
import com.timesheet.syborgtech.dto.response.TaskRequestDto;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import com.timesheet.syborgtech.dtoCommon.SyborgtechResponse;
import com.timesheet.syborgtech.service.SubTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
                        .statusMessage("SubTask Created Successfully")
                        .build())
                .data(subTaskService.createSubTask(subTaskRequestDto)).build();
    }


    @GetMapping("/v1")
    public SyborgtechResponse getSubTasks(
            @RequestParam(required = false, name = "searchTerm") String searchTerm,
            @RequestParam(required = false, name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(required = false, name = "recordsPerPage", defaultValue = "1000") Integer recordsPerPage,
            @RequestParam(name = "subTaskId",required = false)Long subTaskId,
            @RequestParam(name = "taskId",required = false)Long taskId
            ) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("SubTask Fetched Successfully")
                        .build())
                .data(subTaskService.getSubTasks(searchTerm,pageNo,recordsPerPage,subTaskId,taskId)).build();
    }
}
