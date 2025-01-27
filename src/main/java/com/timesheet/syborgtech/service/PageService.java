package com.timesheet.syborgtech.service;

import com.timesheet.syborgtech.dto.request.PageRequestDto;
import com.timesheet.syborgtech.dto.response.Response;
import com.timesheet.syborgtech.dtoCommon.DataResponse;
import com.timesheet.syborgtech.exceptions.PageAlreadyExists;
import com.timesheet.syborgtech.model.Page;
import com.timesheet.syborgtech.repository.PageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PageService {

    private PageRepository pageRepository;

    public Response createPage(PageRequestDto pageRequestDto) {

        Optional<Page> isPageExists= Optional.ofNullable(pageRepository.existsByName(pageRequestDto.getName().trim()).orElseThrow(() -> new PageAlreadyExists("Page Already Exists")));

        Page page=new Page();
        page.setName(pageRequestDto.getName().trim());
        pageRepository.save(page);

        return Response.builder().message("User and project assigned Successfully").build();
    }
}
