package com.timesheet.syborgtech.service;

import com.timesheet.syborgtech.dto.request.ProjectRequestDto;
import com.timesheet.syborgtech.dto.response.ProjectListResponseDto;
import com.timesheet.syborgtech.dto.response.ProjectResponseDto;
import com.timesheet.syborgtech.dto.response.Response;
import com.timesheet.syborgtech.exceptions.ProjectAlreadyExists;
import com.timesheet.syborgtech.model.Projects;
import com.timesheet.syborgtech.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.timesheet.syborgtech.searchTerm.SearchTerm.containsProject;
import static com.timesheet.syborgtech.searchTerm.SearchTerm.containsText;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ProjectRepository projectRepository;

    public Response createProject(ProjectRequestDto projectRequestDto) {

        boolean existProject = projectRepository.existsByProjectName(projectRequestDto.getProjectName().trim());

        if (existProject) {
            throw new ProjectAlreadyExists("Project Name Already Exists");
        }

        Projects project = new Projects();
        project.setProjectName(projectRequestDto.getProjectName());
        project.setDescription(projectRequestDto.getDescription());
        projectRepository.save(project);
        logger.info("Creating project with name: {}", projectRequestDto.getProjectName());
        return Response.builder().message("project created successfully").build();
    }

    public ProjectResponseDto getProjects(String searchTerm, Integer pageNo, Integer recordsPerPage, Long projectId) {
        Pageable page = PageRequest.of(pageNo - 1, recordsPerPage, Sort.Direction.DESC, "id");
        Page<Projects> projects = null;
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            projects = projectRepository.findAll(containsProject(searchTerm, projectId), page);
        } else if (projectId != null) {
            projects = projectRepository.findAllById(projectId, page);
        } else {
            projects = projectRepository.findAll(page);
        }

        if (projects.isEmpty()) {
            return ProjectResponseDto.builder()
                    .projectResponseDtoList(List.of())
                    .build();
        }

        List<ProjectListResponseDto> projectResponseDtoList = projects.stream()
                .map(project -> ProjectListResponseDto.builder()
                        .id(project.getId())
                        .createAt(project.getCreateAt())
                        .updatedAt(project.getUpdatedAt())
                        .projectName(project.getProjectName())
                        .description(project.getDescription())
                        .build())
                .toList();

        return ProjectResponseDto.builder()
                .projectResponseDtoList(projectResponseDtoList)
                .build();
    }
}