package com.timesheet.syborgtech.service;

import com.timesheet.syborgtech.dto.request.EpicRequestDto;
import com.timesheet.syborgtech.dto.response.Response;
import com.timesheet.syborgtech.dtoCommon.DataResponse;
import com.timesheet.syborgtech.exceptions.EpicAlreadyExists;
import com.timesheet.syborgtech.model.Epic;
import com.timesheet.syborgtech.model.Projects;
import com.timesheet.syborgtech.repository.EpicRepository;
import com.timesheet.syborgtech.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
