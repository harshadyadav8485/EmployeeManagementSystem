package com.timesheet.syborgtech.service;

import com.timesheet.syborgtech.dto.response.*;
import com.timesheet.syborgtech.dtoCommon.DataResponse;
import com.timesheet.syborgtech.exceptions.ProjectNotFoundException;
import com.timesheet.syborgtech.exceptions.SprintNotFoundException;
import com.timesheet.syborgtech.exceptions.UserNotFoundException;
import com.timesheet.syborgtech.model.*;
import com.timesheet.syborgtech.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timesheet.syborgtech.searchTerm.SearchTerm.containsTask;

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
//        if (!user.isPresent()) {
//            throw new UserNotFoundException("User Not Found Exception");
//        }
        Optional<Sprint> sprint = sprintRepository.findById(taskRequestDto.getSprintId());
        if (!sprint.isPresent()) {
            throw new SprintNotFoundException("Sprint Not Found Exception");
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

    public DataResponse getTasks(String searchTerm, Integer pageNo, Integer recordsPerPage, Long taskId) {

        Pageable page = PageRequest.of(pageNo - 1, recordsPerPage, Sort.Direction.DESC, "taskId");

        Page<Task> taskPage = taskRepository.findAll(containsTask(searchTerm, taskId), page);
        List<TaskListResponseDto> taskListResponseDto = new ArrayList<>();

        taskPage.forEach(task ->{
            TaskListResponseDto taskResponseDto = new TaskListResponseDto();
            taskResponseDto.setTaskId(task.getTaskId());
            //taskResponseDto.setSprint(task.getSprint());
            taskResponseDto.setTaskName(task.getName());
            taskResponseDto.setDescription(task.getDescription());
            taskResponseDto.setStatus(task.getStatus());
            taskResponseDto.setPriority(task.getPriority());
            taskResponseDto.setCreateAt(task.getCreateAt());
            taskResponseDto.setUpdatedAt(task.getUpdatedAt());
//            taskResponseDto.setComments(task.getComments());
            taskResponseDto.setEpic(task.getEpic().getName());
            taskResponseDto.setTaskType(task.getTaskType());
            //taskResponseDto.setUser(task.getUser());
            taskResponseDto.setReporterId(task.getReporterId());
            taskListResponseDto.add(taskResponseDto);

        });
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setTaskListResponseDto(taskListResponseDto);
        taskResponseDto.setTotalPages(taskPage.getTotalPages());
        taskResponseDto.setPageSize(taskPage.getSize());
        taskResponseDto.setTotalElements(taskPage.getTotalElements());
        taskResponseDto.setCurrentPage(taskPage.getNumber()+1);

        return taskResponseDto;
    }

    public Response updateTask(TaskRequestDto taskRequestDto) {

        Optional<Task> task=taskRepository.findById(taskRequestDto.getTaskId());
        if(task.isPresent()){
            if(taskRequestDto.getStatus()!=null) {
                task.get().setStatus(taskRequestDto.getStatus());
            }if(taskRequestDto.getPriority()!=null){
                task.get().setPriority(taskRequestDto.getPriority());
            }if(taskRequestDto.getEpicId()!=null){
                Optional<Epic> epic=epicRepository.findById(taskRequestDto.getEpicId());
                task.get().setEpic(epic.get());
                epicRepository.save(epic.get());
            }
            taskRepository.save(task.get());
        }
        return Response.builder().message("Task Updated Successfully").build();
    }
}
