package com.timesheet.syborgtech.service;

import com.timesheet.syborgtech.dto.response.Response;
import com.timesheet.syborgtech.dto.response.SubTaskListResponseDto;
import com.timesheet.syborgtech.dto.response.SubTaskRequestDto;
import com.timesheet.syborgtech.dto.response.SubTasksResponseDto;
import com.timesheet.syborgtech.dtoCommon.DataResponse;
import com.timesheet.syborgtech.exceptions.TaskNotFoundException;
import com.timesheet.syborgtech.exceptions.UserNotFoundException;
import com.timesheet.syborgtech.model.*;
import com.timesheet.syborgtech.repository.SprintRepository;
import com.timesheet.syborgtech.repository.SubtaskRepository;
import com.timesheet.syborgtech.repository.TaskRepository;
import com.timesheet.syborgtech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.timesheet.syborgtech.searchTerm.SearchTerm.containsSubTask;
import static com.timesheet.syborgtech.searchTerm.SearchTerm.containsText;

@Service
public class SubTaskService {

    @Autowired
    private SubtaskRepository subtaskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SprintRepository sprintRepository;

    public Response createSubTask(SubTaskRequestDto subTaskRequestDto) {

        Optional<User> user = userRepository.findById(subTaskRequestDto.getUserId());
        if (!user.isPresent()) {
            throw new UserNotFoundException("User Not Found Exception");
        }
        Optional<Task> task = taskRepository.findById(subTaskRequestDto.getTaskId());
        if (!task.isPresent()) {
            throw new TaskNotFoundException("Task not found with ID: " + subTaskRequestDto.getTaskId());
        }
        Subtask subtask = new Subtask();
        subtask.setName(subTaskRequestDto.getName()!= null ? subTaskRequestDto.getName():null);
        subtask.setStatus(subTaskRequestDto.getStatus());
        subtask.setTask(task.get());
        subtask.setUser(user.get());
        subtask.setReporterId(subTaskRequestDto.getReporterId());
        subtask.setActualHours(subTaskRequestDto.getActualHours() != null ? subTaskRequestDto.getActualHours() : 0L);
        subtask.setBudgetedHours(subTaskRequestDto.getBudgetedHours());
        subtask.setStartDate(subTaskRequestDto.getStartDate() != null ? subTaskRequestDto.getStartDate() : null);
        subtask.setEndDate(subTaskRequestDto.getEndDate() != null ? subTaskRequestDto.getEndDate() : null);
        subtaskRepository.save(subtask);

        return Response.builder().message("SubTask Created Successfully").build();
    }

    public SubTasksResponseDto getSubTasks(String searchTerm, Integer pageNo, Integer recordsPerPage, Long subTaskId, Long taskId) {

        Pageable page = PageRequest.of(pageNo - 1, recordsPerPage, Sort.Direction.DESC, "id");
        Page<Subtask> subtasks;

        if (searchTerm != null && !searchTerm.isEmpty()) {
            subtasks = subtaskRepository.findAll(containsSubTask(searchTerm, subTaskId, taskId), page);
        } else if (subTaskId != null) {
            subtasks = subtaskRepository.findAllById(subTaskId, page);
        } else if (taskId != null) {
            subtasks = subtaskRepository.findAllByTask_TaskId(taskId, page);
        } else {
            subtasks = subtaskRepository.findAll(page);
        }

        if (subtasks == null || subtasks.isEmpty()) {
            return new SubTasksResponseDto(0, 0L, 0, 0, Collections.emptyList());
        }

        List<SubTaskListResponseDto> subTaskListResponseDtos = new ArrayList<>();

        subtasks.forEach(subTask -> {
            Optional<User> user = userRepository.findById(subTask.getReporterId());

            SubTaskListResponseDto responseDto = new SubTaskListResponseDto();
            responseDto.setId(subTask.getId());
            responseDto.setTaskName(
                    subTask.getTask() != null ? subTask.getTask().getName() : "N/A"
            );
            responseDto.setSubTaskName(subTask.getName() != null ? subTask.getName() : "N/A");
            responseDto.setStatus(subTask.getStatus() != null ? subTask.getStatus() : Subtask.SubtaskStatus.TO_DO);
            responseDto.setUserName(subTask.getUser().getUserName());
            responseDto.setUserId(subTask.getUser().getId());
            responseDto.setReporterId(subTask.getReporterId());
            responseDto.setReporterName(user.isPresent() ? user.get().getFirstName() : "N/A");
            responseDto.setBudgetedHours(subTask.getBudgetedHours() != null ? subTask.getBudgetedHours() : 0L);
            responseDto.setActualHours(subTask.getActualHours() != null ? subTask.getActualHours() : 0L);
            responseDto.setStartDate(subTask.getStartDate());
            responseDto.setEndDate(subTask.getEndDate());
            responseDto.setCreateAt(subTask.getCreateAt());
            responseDto.setUpdatedAt(subTask.getUpdatedAt());
            responseDto.setSprintName(subTask.getTask().getSprint().getName());
            responseDto.setSprintId(subTask.getTask().getSprint().getId());
            responseDto.setProjectName(subTask.getTask().getSprint().getProjects().getProjectName());
            responseDto.setProjectId(subTask.getTask().getSprint().getProjects().getId());
            subTaskListResponseDtos.add(responseDto);
        });

        SubTasksResponseDto subTasksResponseDto = new SubTasksResponseDto();
        subTasksResponseDto.setCurrentPage(subtasks.getNumber() + 1);
        subTasksResponseDto.setTotalElements(subtasks.getTotalElements());
        subTasksResponseDto.setPageSize(subtasks.getSize());
        subTasksResponseDto.setTotalPages(subtasks.getTotalPages());
        subTasksResponseDto.setSubTaskListResponseDtos(subTaskListResponseDtos);

        return subTasksResponseDto;
    }

    public SubTasksResponseDto getTimeSheet(Long subTaskId, Long taskId, Long sprintId, Long userId, Long projectId, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.DESC, "startDate");
        if ("asc".equalsIgnoreCase(sortOrder)) {
            sort = Sort.by(Sort.Direction.ASC, "startDate");
        }
        List<Subtask> subtasks = null;
        if (userId != null) {
            subtasks = subtaskRepository.findAllByUser_Id(userId, sort);
        } else if (sprintId != null) {
            List<Task> tasks = taskRepository.findAllBySprint_Id(sprintId);
            subtasks = subtaskRepository.findAllByTaskIn(tasks, sort);
        } else if (projectId != null) {
            List<Sprint> sprints = sprintRepository.findAllByProjects_Id(projectId);
            List<Task> tasks = taskRepository.findAllBySprint_IdIn(sprints.stream().map(Sprint::getId).collect(Collectors.toList()));
            subtasks = subtaskRepository.findAllByTaskIn(tasks, sort);
        } else if (subTaskId != null) {
            subtasks = subtaskRepository.findAllById(subTaskId, sort);
        } else if (taskId != null) {
            subtasks = subtaskRepository.findAllByTask_TaskId(taskId, sort);
        }
        if (subtasks == null || subtasks.isEmpty()) {
            return new SubTasksResponseDto(0, 0L, 0, 0, Collections.emptyList());
        }

        List<SubTaskListResponseDto> subTaskListResponseDtos = new ArrayList<>();

        subtasks.forEach(subTask -> {
            Optional<User> user = userRepository.findById(subTask.getReporterId());

            SubTaskListResponseDto responseDto = new SubTaskListResponseDto();
            responseDto.setId(subTask.getId());
            responseDto.setTaskName(
                    subTask.getTask() != null ? subTask.getTask().getName() : "N/A"
            );
            responseDto.setSubTaskName(subTask.getName() != null ? subTask.getName() : "N/A");
            responseDto.setStatus(subTask.getStatus() != null ? subTask.getStatus() : Subtask.SubtaskStatus.TO_DO);
            responseDto.setUserName(subTask.getUser().getUserName());
            responseDto.setUserId(subTask.getUser().getId());
            responseDto.setReporterId(subTask.getReporterId());
            responseDto.setReporterName(user.isPresent() ? user.get().getFirstName() : "N/A");
            responseDto.setBudgetedHours(subTask.getBudgetedHours() != null ? subTask.getBudgetedHours() : 0L);
            responseDto.setActualHours(subTask.getActualHours() != null ? subTask.getActualHours() : 0L);
            responseDto.setStartDate(subTask.getStartDate());
            responseDto.setEndDate(subTask.getEndDate());
            responseDto.setCreateAt(subTask.getCreateAt());
            responseDto.setUpdatedAt(subTask.getUpdatedAt());
            responseDto.setSprintName(subTask.getTask().getSprint().getName());
            responseDto.setSprintId(subTask.getTask().getSprint().getId());
            responseDto.setProjectName(subTask.getTask().getSprint().getProjects().getProjectName());
            responseDto.setProjectId(subTask.getTask().getSprint().getProjects().getId());
            subTaskListResponseDtos.add(responseDto);
        });

        SubTasksResponseDto subTasksResponseDto = new SubTasksResponseDto();
        subTasksResponseDto.setSubTaskListResponseDtos(subTaskListResponseDtos);

        return subTasksResponseDto;
    }
}
