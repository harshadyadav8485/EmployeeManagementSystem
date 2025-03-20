package com.timesheet.syborgtech.controller;

import com.timesheet.syborgtech.dto.request.SprintRequestDto;
import com.timesheet.syborgtech.dto.response.TaskRequestDto;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import com.timesheet.syborgtech.dtoCommon.SyborgtechResponse;
import com.timesheet.syborgtech.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @PostMapping("/v1")
    public SyborgtechResponse createTask(@RequestBody TaskRequestDto taskRequestDto) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("Sprint-001")
                        .statusMessage("Task Created Successfully")
                        .build())
                .data(taskService.createTask(taskRequestDto)).build();
    }

    @GetMapping("/v1")
    public SyborgtechResponse getTask(
            @RequestParam(required = false, name = "searchTerm") String searchTerm,
            @RequestParam(required = false, name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(required = false, name = "recordsPerPage", defaultValue = "1000") Integer recordsPerPage,
            @RequestParam(name = "task_id",required = false)Long taskId) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Task Fetched Successfully")
                        .build())
                .data(taskService.getTasks(searchTerm,pageNo,recordsPerPage,taskId)).build();
    }

    @PutMapping("/v1")
    public SyborgtechResponse updateTask(@RequestBody TaskRequestDto taskRequestDto) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("Sprint-001")
                        .statusMessage("Task Created Successfully")
                        .build())
                .data(taskService.updateTask(taskRequestDto)).build();
    }

}
