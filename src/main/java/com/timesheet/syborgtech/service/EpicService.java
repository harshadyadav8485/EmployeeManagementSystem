package com.timesheet.syborgtech.service;

import com.timesheet.syborgtech.dto.request.EpicRequestDto;
import com.timesheet.syborgtech.dto.response.EpicResponseDto;
import com.timesheet.syborgtech.dto.response.EpicResponseDtoList;
import com.timesheet.syborgtech.dto.response.ProjectListResponseDto;
import com.timesheet.syborgtech.dto.response.Response;
import com.timesheet.syborgtech.exceptions.EpicAlreadyExists;
import com.timesheet.syborgtech.model.Epic;
import com.timesheet.syborgtech.model.Projects;
import com.timesheet.syborgtech.repository.EpicRepository;
import com.timesheet.syborgtech.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EpicService {

    @Autowired
    private EpicRepository epicRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public Response createEpic(EpicRequestDto epicRequestDto) {
        boolean epicIsPrsent = epicRepository.existsByName(epicRequestDto.getName().trim());
        Optional<Projects> projects = projectRepository.findById(epicRequestDto.getProjectId());
        if (epicIsPrsent) {
            throw new EpicAlreadyExists(String.format("Epic '%s' Already Exists", epicRequestDto.getName()));
        }
        Epic epic = new Epic();
        epic.setName(epicRequestDto.getName());
        epic.setProjects(projects.get());
        epicRepository.save(epic);
        return Response.builder().message("Epic created successfully").build();
    }

    public EpicResponseDto getEpics(String searchTerm, Integer pageNo, Integer recordsPerPage, Long epicId) {
        Pageable pageable = PageRequest.of(pageNo - 1, recordsPerPage, Sort.by(Sort.Direction.DESC, "id"));
        Page<Epic> epicList = epicRepository.findAll(containsEpic(searchTerm, epicId), pageable);

        List<EpicResponseDtoList> epicResponseDtoListList = epicList.stream()
                .map(epic -> {
                    Projects project = epic.getProjects();
                    ProjectListResponseDto projectDto = (project != null) ? ProjectListResponseDto.builder()
                            .projectId(project.getId())
                            .projectName(project.getProjectName())
                            .projectDescription(project.getDescription())
                            .createAt(project.getCreateAt())
                            .updatedAt(project.getUpdatedAt())
                            .build() : null;

                    return new EpicResponseDtoList(epic.getId(), epic.getName(), projectDto);
                })
                .toList();

        return EpicResponseDto.builder()
                .currentPage(epicList.getNumber() + 1)
                .pageSize(epicList.getSize())
                .totalPages(epicList.getTotalPages())
                .totalElements(epicList.getTotalElements())
                .epicResponseDtoListList(epicResponseDtoListList)
                .build();
    }


    public static Specification<Epic> containsEpic(String searchTerm, Long epicId) {

        boolean hasSearchTerm = StringUtils.hasLength(searchTerm);
        String finalSearchTerm = hasSearchTerm && !searchTerm.contains("%")
                ? "%" + searchTerm.toLowerCase() + "%"
                : searchTerm;

        if (Objects.nonNull(epicId)) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("id"), epicId);
        }
        if (hasSearchTerm) {

            Specification<Epic> searchSpec=null;

            return  ((root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), finalSearchTerm),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("projects").get("projectName")), finalSearchTerm),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("projects").get("description")), finalSearchTerm)
            )));

        }
        return null;
    }
}
