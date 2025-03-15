package com.timesheet.syborgtech.controller;

import com.timesheet.syborgtech.dto.request.CommentRequestDto;
import com.timesheet.syborgtech.dto.request.EpicRequestDto;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import com.timesheet.syborgtech.dtoCommon.SyborgtechResponse;
import com.timesheet.syborgtech.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/comment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @GetMapping("/v1")
    public SyborgtechResponse getComments( @RequestParam(required = false, name = "searchTerm") String searchTerm,
                                           @RequestParam(required = false, name = "pageNo" ,defaultValue = "1") Integer pageNo,
                                           @RequestParam(required = false, name = "recordsPerPage", defaultValue = "1000") Integer recordsPerPage,
                                           @RequestParam(required = false, name = "commentId") Long commentId) throws IOException{
        return SyborgtechResponse.builder()
                        .status(ApiStatus.builder()
                        .status("Success")
                        .statusCode("ORD-0001")
                        .statusMessage("Comment Fetch Successfully")
                        .build())
                .data(commentService.getComments(searchTerm,pageNo,recordsPerPage,commentId)).build();


    }

}
