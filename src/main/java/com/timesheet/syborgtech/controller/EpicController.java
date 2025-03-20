package com.timesheet.syborgtech.controller;

import com.timesheet.syborgtech.dto.request.EpicRequestDto;
import com.timesheet.syborgtech.dto.request.UserRegistrationRequest;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import com.timesheet.syborgtech.dtoCommon.SyborgtechResponse;
import com.timesheet.syborgtech.service.EpicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/epic")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EpicController {

    @Autowired
    private EpicService epicService;

    @PostMapping("/v1")
    public SyborgtechResponse createEpic(@RequestBody EpicRequestDto epicRequestDto) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Epic Created Successfully")
                        .build())
                .data(epicService.createEpic(epicRequestDto)).build();
    }

    @GetMapping("/v1")
    public SyborgtechResponse getEpics(
            @RequestParam(required = false, name = "searchTerm") String searchTerm,
            @RequestParam(required = false, name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(required = false, name = "recordsPerPage", defaultValue = "1000") Integer recordsPerPage,
            @RequestParam(name = "epicId",required = false)Long epicId) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Epics Fetched Successfully")
                        .build())
                .data(epicService.getEpics(searchTerm,pageNo,recordsPerPage,epicId)).build();
    }

    @PutMapping("/v1")
    public SyborgtechResponse updateEpic(@RequestBody EpicRequestDto epicRequestDto) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Comment Updated Successfully")
                        .build())
                .data(epicService.updateEpic(epicRequestDto)).build();
    }

    @DeleteMapping("/v1/{epicId}")
    public SyborgtechResponse deleteEpicById(@PathVariable(name = "epicId") Long epicId) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Epic Deleted Successfully")
                        .build())
                .data(epicService.deleteEpicById(epicId)).build();
    }
}
