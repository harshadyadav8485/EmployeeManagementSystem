package com.timesheet.syborgtech.service;

import com.timesheet.syborgtech.dto.request.PageRequestDto;
import com.timesheet.syborgtech.dto.response.PageListResponseDto;
import com.timesheet.syborgtech.dto.response.PageResponseDto;
import com.timesheet.syborgtech.dto.response.Response;
import com.timesheet.syborgtech.dtoCommon.DataResponse;
import com.timesheet.syborgtech.exceptions.PageAlreadyExists;
import com.timesheet.syborgtech.model.Page;
import com.timesheet.syborgtech.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timesheet.syborgtech.searchTerm.SearchTerm.containsPage;
import static com.timesheet.syborgtech.searchTerm.SearchTerm.containsText;

@Service
public class PageService {

    @Autowired
    private PageRepository pageRepository;

    public Response createPage(PageRequestDto pageRequestDto) {

        boolean isPageExists = pageRepository.existsByName(pageRequestDto.getName().trim());
        if (isPageExists) {
            throw new PageAlreadyExists("Page Already Exists");
        }
        Page page=new Page();
        page.setName(pageRequestDto.getName().trim());
        pageRepository.save(page);

        return Response.builder().message("User and project assigned Successfully").build();
    }

    public DataResponse getPages(String searchTerm, Integer pageNo, Integer recordsPerPage, Long pageId) {

        Pageable page = PageRequest.of(pageNo - 1, recordsPerPage, Sort.Direction.DESC, "id");

        org.springframework.data.domain.Page<Page> pageData = pageRepository.findAll(containsPage(searchTerm, pageId), page);
        List<PageListResponseDto> pageListResponseDto = new ArrayList<>();

        if(!pageData.isEmpty()){
            pageData.forEach(p ->{
                PageListResponseDto responseDto = new PageListResponseDto();
                responseDto.setPageId(p.getId());
                responseDto.setPageName(p.getName());
                pageListResponseDto.add(responseDto);
            });
        }
        PageResponseDto pageResponseDto = new PageResponseDto();
        pageResponseDto.setPageListResponseDto(pageListResponseDto);
        pageResponseDto.setTotalPages(pageData.getTotalPages());
        pageResponseDto.setPageSize(pageData.getSize());
        pageResponseDto.setTotalElements(pageData.getTotalElements());
        pageResponseDto.setCurrentPage(pageData.getNumber()+1);

        return pageResponseDto;
    }
}
