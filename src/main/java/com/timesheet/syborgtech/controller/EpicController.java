package com.timesheet.syborgtech.controller;

import com.timesheet.syborgtech.dto.request.EpicRequestDto;
import com.timesheet.syborgtech.dto.request.UserRegistrationRequest;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import com.timesheet.syborgtech.dtoCommon.SyborgtechResponse;
import com.timesheet.syborgtech.service.EpicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/epic")
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
}
