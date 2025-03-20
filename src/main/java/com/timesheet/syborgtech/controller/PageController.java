package com.timesheet.syborgtech.controller;

import com.timesheet.syborgtech.dto.request.CommentRequestDto;
import com.timesheet.syborgtech.dto.request.PageRequestDto;
import com.timesheet.syborgtech.dtoCommon.ApiStatus;
import com.timesheet.syborgtech.dtoCommon.SyborgtechResponse;
import com.timesheet.syborgtech.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
    @RequestMapping("/page")
public class PageController {
@Autowired
private PageService pageService;
    @PostMapping("/v1")
    public SyborgtechResponse createPage(@RequestBody PageRequestDto pageRequestDto) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Comment Created Successfully")
                        .build())
                .data(pageService.createPage(pageRequestDto)).build();
    }

    @GetMapping("/v1")
    public SyborgtechResponse getPages(
            @RequestParam(required = false, name = "searchTerm") String searchTerm,
            @RequestParam(required = false, name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(required = false, name = "recordsPerPage", defaultValue = "1000") Integer recordsPerPage,
            @RequestParam(name = "page_id",required = false)Long pageId) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Page Fetched Successfully")
                        .build())
                .data(pageService.getPages(searchTerm,pageNo,recordsPerPage,pageId)).build();
    }

    @PutMapping("/v1")
    public SyborgtechResponse updatePage(@RequestBody PageRequestDto pageRequestDto) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Page Updated Successfully")
                        .build())
                .data(pageService.updatePage(pageRequestDto)).build();
    }

    @DeleteMapping("/v1/{pageId}")
    public SyborgtechResponse deletePageById(@PathVariable(name = "pageId") Long pageId) throws IOException {
        return SyborgtechResponse.builder()
                .status(ApiStatus.builder()
                        .status("SUCCESS")
                        .statusCode("ORD-0001")
                        .statusMessage("Page Deleted Successfully")
                        .build())
                .data(pageService.deletePageById(pageId)).build();
    }

}
