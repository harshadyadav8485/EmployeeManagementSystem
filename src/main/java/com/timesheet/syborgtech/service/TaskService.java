package com.timesheet.syborgtech.service;

import com.timesheet.syborgtech.dto.response.Response;
import com.timesheet.syborgtech.dto.response.TaskRequestDto;
import com.timesheet.syborgtech.dtoCommon.DataResponse;
import com.timesheet.syborgtech.exceptions.ProjectNotFoundException;
import com.timesheet.syborgtech.exceptions.SprintNotFoundException;
import com.timesheet.syborgtech.exceptions.UserNotFoundException;
import com.timesheet.syborgtech.model.*;
import com.timesheet.syborgtech.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EpicRepository epicRepository;

    public Response createTask(TaskRequestDto taskRequestDto) {
        Optional<Projects> projects = projectRepository.findById(taskRequestDto.getProjectId());
        if (!projects.isPresent()) {
            throw new ProjectNotFoundException("Project Not Found Exception");
        }
        Optional<User> user = userRepository.findById(taskRequestDto.getUserId());
        if (!user.isPresent()) {
            throw new UserNotFoundException("User Not Found Exception");
        }
        Optional<Sprint> sprint = sprintRepository.findById(taskRequestDto.getSprintId());
        if (!sprint.isPresent()) {
            throw new SprintNotFoundException("User Not Found Exception");
        }
        Optional<Epic> epic = epicRepository.findById(taskRequestDto.getEpicId());
        Task task = new Task();
        task.setName(taskRequestDto.getName());
        task.setDescription(taskRequestDto.getDescription());
        task.setSprint(sprint.get());
        task.setStatus(taskRequestDto.getStatus());
        task.setPriority(taskRequestDto.getPriority());
        task.setTaskType(taskRequestDto.getTaskType());
        task.setUser(user.get());
        task.setEpic(epic.isPresent()?epic.get():null);
        taskRepository.save(task);

        return Response.builder().message("Task Created Successfully").build();
    }
}
