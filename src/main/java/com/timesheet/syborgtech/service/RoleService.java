package com.timesheet.syborgtech.service;

import com.timesheet.syborgtech.dto.request.RoleDtoRequest;
import com.timesheet.syborgtech.dto.response.*;
import com.timesheet.syborgtech.exceptions.RoleAlreadyExists;
import com.timesheet.syborgtech.exceptions.RoleNotFoundException;
import com.timesheet.syborgtech.model.PageRolePermission;
import com.timesheet.syborgtech.model.Role;
import com.timesheet.syborgtech.repository.PageRepository;
import com.timesheet.syborgtech.repository.PageRolePermissionRepository;
import com.timesheet.syborgtech.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timesheet.syborgtech.searchTerm.SearchTerm.containsText;

@Service
public class RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PageRolePermissionRepository pageRolePermissionRepository;

    public Response createRole(RoleDtoRequest roleDtoRequest) {

        Optional<Role> role = roleRepository.findByRoleName(roleDtoRequest.getRoleName().trim());

        if (role.isPresent()) {
            throw new RoleAlreadyExists(String.format("Role already exists"));
        }
        Role newRole = new Role();
        newRole.setRoleName(roleDtoRequest.getRoleName().trim());
        newRole.setDescription(roleDtoRequest.getDescription());
        newRole.setStatus(roleDtoRequest.getStatus());
        roleRepository.save(newRole);

//        Role role1=Role.builder()
//                .roleName(roleDtoRequest.getRoleName())
//                .id(roleDtoRequest.getRoleId())
//                .description(roleDtoRequest.getDescription())
//                .build();
//      roleRepository.save(role1);

        return Response.builder().message("Role Created Successfully").build();
    }

    public RoleResponseDto getRoles(String searchTerm, Integer pageNo, Integer recordsPerPage, Long roleId) {

        Pageable page = PageRequest.of(pageNo - 1, recordsPerPage, Sort.Direction.DESC, "id");

        Page<Role> rolePage = roleRepository.findAll(containsText(searchTerm, roleId), page);
        List<RoleListResponseDto> roleListResponseDtos = new ArrayList<>();

        if (!rolePage.isEmpty()) {
            rolePage.forEach((role -> {
                RoleListResponseDto responseDto = new RoleListResponseDto();
                responseDto.setRoleId(role.getId());
                responseDto.setRoleName(role.getRoleName().toUpperCase());
                responseDto.setStatus(role.getStatus());
                responseDto.setDescription(role.getDescription());
                roleListResponseDtos.add(responseDto);
            }));
        }
        RoleResponseDto roleResponseDto = new RoleResponseDto();
        roleResponseDto.setRoleListResponseDtos(roleListResponseDtos);
        roleResponseDto.setTotalPages(rolePage.getTotalPages());
        roleResponseDto.setPageSize(rolePage.getSize());
        roleResponseDto.setTotalElements(rolePage.getTotalElements());
        roleResponseDto.setCurrentPage(rolePage.getNumber() + 1);

        return roleResponseDto;
    }


    public Response updateRole(RoleDtoRequest roleDtoRequest) {
        Role existingRole = roleRepository.findById(roleDtoRequest.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + roleDtoRequest.getRoleId()));
        boolean existRole = roleRepository.existsByRoleNameAndIdNot(roleDtoRequest.getRoleName().toUpperCase(), roleDtoRequest.getRoleId());

        if (existRole) {
            throw new RoleAlreadyExists("Role name '" + roleDtoRequest.getRoleName() + "' already exists.");
        }

        existingRole.setRoleName(roleDtoRequest.getRoleName().toUpperCase());
        existingRole.setDescription(roleDtoRequest.getDescription());
        existingRole.setStatus(roleDtoRequest.getStatus());

        Role updatedRole = roleRepository.save(existingRole);
        return Response.builder().message("Role Updated Successfully").build();
    }

    public PageDetailsResponse pageDetailsViewList(String roleName) {
        Optional<Role> role = roleRepository.findByRoleName(roleName);
        List<pageDetailsViewList> pageDetailsViewList = new ArrayList<>();
        if (role.isPresent()) {
            List<PageRolePermission> pageRolePermissions = pageRolePermissionRepository.findAllByRole(role.get());
            pageRolePermissions.forEach((pr) -> {
                pageDetailsViewList list = new pageDetailsViewList();
                list.setRoleId(pr.getRole().getId());
                list.setRoleName(pr.getRole().getRoleName());
                list.setPageId(pr.getPage().getId());
                list.setPageName(pr.getPage().getName());
                list.setCanView(pr.isCanView());
                list.setCanCreate(pr.isCanCreate());
                list.setCanUpdate(pr.isCanUpdate());
                list.setCanDelete(pr.isCanDelete());
                pageDetailsViewList.add(list);
            });
        }
        PageDetailsResponse pageDetailsResponse=new PageDetailsResponse();
        pageDetailsResponse.setPageDetailsViewList(pageDetailsViewList);
        return pageDetailsResponse;
    }
}
