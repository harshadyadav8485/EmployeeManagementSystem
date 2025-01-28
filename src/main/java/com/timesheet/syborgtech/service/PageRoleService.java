package com.timesheet.syborgtech.service;

import com.timesheet.syborgtech.dto.request.Page_RoleRequestDto;
import com.timesheet.syborgtech.dto.response.Response;
import com.timesheet.syborgtech.dtoCommon.DataResponse;
import com.timesheet.syborgtech.model.Page;
import com.timesheet.syborgtech.model.PageRolePermission;
import com.timesheet.syborgtech.model.Role;
import com.timesheet.syborgtech.repository.PageRepository;
import com.timesheet.syborgtech.repository.PageRolePermissionRepository;
import com.timesheet.syborgtech.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PageRoleService {
    @Autowired
    private PageRolePermissionRepository pageRolePermissionRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Response pageActionMapping(Page_RoleRequestDto page_roleRequestDto) {

        boolean mappingExists = pageRolePermissionRepository.existsByPageIdAndRoleId(
                page_roleRequestDto.getPageId(),
                page_roleRequestDto.getRoleId()
        );
        if (mappingExists) {
            return Response.builder().message("Page Role Mapping already exists").build();
        }

        Optional<Page> pageOptional = pageRepository.findById(page_roleRequestDto.getPageId());
        Optional<Role> roleOptional = roleRepository.findById(page_roleRequestDto.getRoleId());

        if (pageOptional.isEmpty() || roleOptional.isEmpty()) {
            return Response.builder().message("Page or Role not found").build();
        }

        PageRolePermission pageRolePermission = new PageRolePermission();
        pageRolePermission.setPage(pageOptional.get());
        pageRolePermission.setRole(roleOptional.get());
        pageRolePermission.setCanCreate(page_roleRequestDto.isCanCreate());
        pageRolePermission.setCanUpdate(page_roleRequestDto.isCanUpdate());
        pageRolePermission.setCanDelete(page_roleRequestDto.isCanDelete());
        pageRolePermission.setCanView(page_roleRequestDto.isCanView());

        pageRolePermissionRepository.save(pageRolePermission);
        return Response.builder().message("Page Role Mapping Created Successfully").build();
    }
}
