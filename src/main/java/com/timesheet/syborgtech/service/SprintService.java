package com.timesheet.syborgtech.service;

import com.timesheet.syborgtech.dto.request.SprintRequestDto;
import com.timesheet.syborgtech.dto.response.Response;
import com.timesheet.syborgtech.dto.response.SprintResponse;
import com.timesheet.syborgtech.dto.response.SprintResponseList;
import com.timesheet.syborgtech.dto.response.TaskListResponseDto;
import com.timesheet.syborgtech.dtoCommon.DataResponse;
import com.timesheet.syborgtech.exceptions.ProjectNotFoundException;
import com.timesheet.syborgtech.exceptions.SprintNameAlreadyExists;
import com.timesheet.syborgtech.model.Projects;
import com.timesheet.syborgtech.model.Sprint;
import com.timesheet.syborgtech.repository.ProjectRepository;
import com.timesheet.syborgtech.repository.SprintRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static com.timesheet.syborgtech.searchTerm.SearchTerm.containsUser;

@Service
public class SprintService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public Response createSprint(SprintRequestDto sprintRequestDto) {

        Optional<Projects> projects = projectRepository.findById(sprintRequestDto.getProjectId());
        if (!projects.isPresent()) {
            throw new ProjectNotFoundException("Project Not Found Exception");
        }

        boolean existSprint = sprintRepository.existsByNameAndProjects_Id(sprintRequestDto.getName().trim(), sprintRequestDto.getProjectId());
        if (existSprint) {
            throw new SprintNameAlreadyExists("Sprint Name Already Exists");
        }

        Sprint sprint = new Sprint();
        sprint.setName(sprintRequestDto.getName());
        sprint.setStartDate(sprintRequestDto.getStartDate());
        sprint.setEndDate(sprintRequestDto.getEndDate());
        sprint.setSprintStatus(sprintRequestDto.getSprintStatus());
        sprint.setProjects(projects.get());
        sprintRepository.save(sprint);
        logger.info("Creating sprint with name: {}", sprintRequestDto.getName());
        return Response.builder().message("Sprint Created Successfully").build();
    }
    public SprintResponse fetchSprints(String searchTerm, Integer pageNo, Integer recordsPerPage, Long sprintId) {
        Pageable page = PageRequest.of(pageNo - 1, recordsPerPage, Sort.by(Sort.Direction.ASC, "updatedAt"));

        Page<Sprint> sprints = sprintRepository.findAll(containsSprint(searchTerm, sprintId), page);

        List<SprintResponseList> sprintResponseListList=new ArrayList<>();

        sprints.forEach((sprint)->{
            List<TaskListResponseDto> taskListResponseDto=new ArrayList<>();

            sprint.getTaskList().forEach((task -> {
                TaskListResponseDto taskResponseDto = new TaskListResponseDto();
                taskResponseDto.setTaskId(task.getTaskId());
                //taskResponseDto.setSprint(task.getSprint());
                taskResponseDto.setTaskName(task.getName());
                taskResponseDto.setDescription(task.getDescription());
                taskResponseDto.setStatus(task.getStatus());
                taskResponseDto.setPriority(task.getPriority());
                taskResponseDto.setCreateAt(task.getCreateAt());
                taskResponseDto.setUpdatedAt(task.getUpdatedAt());
                taskResponseDto.setEpic(task.getEpic().getName());
                taskResponseDto.setTaskType(task.getTaskType());
                //taskResponseDto.setUser(task.getUser());
                taskResponseDto.setReporterId(task.getReporterId());
                taskListResponseDto.add(taskResponseDto);
            }));


            SprintResponseList responseList=new SprintResponseList();
            responseList.setId(sprint.getId());
            responseList.setName(sprint.getName());
            responseList.setStartDate(sprint.getStartDate());
            responseList.setEndDate(sprint.getEndDate());
            responseList.setProjectId(sprint.getProjects().getId());
            responseList.setProjectName(sprint.getProjects().getProjectName());
            responseList.setSprintStatus(sprint.getSprintStatus());
            responseList.setTaskList(taskListResponseDto);
            sprintResponseListList.add(responseList);
        });

        SprintResponse sprintResponse=new SprintResponse();
        sprintResponse.setCurrentPage(sprints.getNumber()+1);
        sprintResponse.setTotalPages(sprints.getTotalPages());
        sprintResponse.setPageSize(sprints.getSize());
        sprintResponse.setTotalElements(sprints.getTotalElements());
        sprintResponse.setSprintResponseListList(sprintResponseListList);
        return sprintResponse;
    }

    public static Specification<Sprint> containsSprint(String searchTerm, Long sprintId) {

        boolean hasSearchTerm = StringUtils.hasLength(searchTerm);
        String finalSearchTerm = hasSearchTerm && !searchTerm.contains("%")
                ? "%" + searchTerm.toLowerCase() + "%"
                : searchTerm;

        if (Objects.nonNull(sprintId)) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("id"), sprintId);
        }
        if (hasSearchTerm) {

            Specification<Sprint> searchSpec=null;

            return  ((root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), finalSearchTerm),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("projects").get("projectName")), finalSearchTerm),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("projects").get("description")), finalSearchTerm)
            )));

        }
        return null;
    }
}
