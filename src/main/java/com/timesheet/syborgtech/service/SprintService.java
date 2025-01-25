package com.timesheet.syborgtech.service;

import com.timesheet.syborgtech.dto.request.SprintRequestDto;
import com.timesheet.syborgtech.dto.response.Response;
import com.timesheet.syborgtech.exceptions.ProjectNotFoundException;
import com.timesheet.syborgtech.exceptions.SprintNameAlreadyExists;
import com.timesheet.syborgtech.model.Projects;
import com.timesheet.syborgtech.model.Sprint;
import com.timesheet.syborgtech.repository.ProjectRepository;
import com.timesheet.syborgtech.repository.SprintRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
