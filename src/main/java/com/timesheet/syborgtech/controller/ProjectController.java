package com.timesheet.syborgtech.controller;

import com.timesheet.syborgtech.dto.request.ProjectRequestDto;
import com.timesheet.syborgtech.dto.request.UserRegistrationRequest;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import com.timesheet.syborgtech.dtoCommon.SyborgtechResponse;
import com.timesheet.syborgtech.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "*", allowedHeaders = "*") // Allow all origins
public class ProjectController {

    @Autowired
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/v1")
    public SyborgtechResponse createProject(@RequestBody ProjectRequestDto projectRequestDto) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Project Created Successfully")
                        .build())
                .data(projectService.createProject(projectRequestDto)).build();
    }

    @GetMapping("/v1")
    public SyborgtechResponse getProjects(
            @RequestParam(required = false, name = "searchTerm") String searchTerm,
            @RequestParam(required = false, name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(required = false, name = "recordsPerPage", defaultValue = "1000") Integer recordsPerPage,
            @RequestParam(name = "project_id",required = false)Long projectId,
            @RequestParam(name = "userId",required = false)Long userId) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Project Fetched Successfully")
                        .build())
                .data(projectService.getProjects(searchTerm,pageNo,recordsPerPage,projectId,userId)).build();
    }
}
