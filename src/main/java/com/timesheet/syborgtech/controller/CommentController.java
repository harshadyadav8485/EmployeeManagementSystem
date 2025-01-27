package com.timesheet.syborgtech.controller;

import com.timesheet.syborgtech.dto.request.CommentRequestDto;
import com.timesheet.syborgtech.dto.request.EpicRequestDto;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import com.timesheet.syborgtech.dtoCommon.SyborgtechResponse;
import com.timesheet.syborgtech.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/v1")
    public SyborgtechResponse createComment(@RequestBody CommentRequestDto commentRequestDto) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Comment Created Successfully")
                        .build())
                .data(commentService.createComment(commentRequestDto)).build();
    }
}
