package com.timesheet.syborgtech.service;

import com.timesheet.syborgtech.dto.request.SprintRequestDto;
import com.timesheet.syborgtech.dto.response.Response;
import com.timesheet.syborgtech.dto.response.SprintListResponseDto;
import com.timesheet.syborgtech.dto.response.SprintResponseDto;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timesheet.syborgtech.searchTerm.SearchTerm.containsSprint;



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

    public SprintResponseDto getSprint(String searchTerm, Integer pageNo, Integer recordsPerPage, Long sprintId) {
        Pageable page = PageRequest.of(pageNo-1,recordsPerPage, Sort.Direction.DESC, "id");

        Page<Sprint> sprints = null;
        if(searchTerm!=null && !searchTerm.isEmpty()) {
            sprints=sprintRepository.findAll(containsSprint(searchTerm, sprintId), page);
        }else if(sprintId !=null){
            sprints=sprintRepository.findAllById(sprintId,page);
        }else{
            sprints=sprintRepository.findAll(page);
        }

         List<SprintListResponseDto> sprintListResponseDtos=new ArrayList<>();

        for(Sprint sprint:sprints){
            SprintListResponseDto listResponseDto=new SprintListResponseDto();
            listResponseDto.setSprintId(sprint.getId());
            listResponseDto.setSprintName(sprint.getName());
            listResponseDto.setCreateAt(sprint.getCreateAt());
            listResponseDto.setUpdatedAt(sprint.getUpdatedAt());
            listResponseDto.setSprintStatus(sprint.getSprintStatus());
            listResponseDto.setStartDate(sprint.getStartDate());
            listResponseDto.setEndDate(sprint.getEndDate());
            sprintListResponseDtos.add(listResponseDto);
        }

        SprintResponseDto sprintResponseDto=new SprintResponseDto();
        sprintResponseDto.setCurrentPage(sprints.getNumber()+1);
        sprintResponseDto.setTotalPages(sprints.getTotalPages());
        sprintResponseDto.setPageSize(sprints.getSize());
        sprintResponseDto.setTotalElements(sprints.getTotalElements());
        sprintResponseDto.setSprintListResponseDtos(sprintListResponseDtos);
       // sprintResponseDto.setSprints(sprints.getContent());
        return sprintResponseDto;
    }
}
