package com.timesheet.syborgtech.service;

import com.timesheet.syborgtech.dto.response.Response;
import com.timesheet.syborgtech.dto.response.SubTaskListResponseDto;
import com.timesheet.syborgtech.dto.response.SubTaskRequestDto;
import com.timesheet.syborgtech.dto.response.SubTasksResponseDto;
import com.timesheet.syborgtech.dtoCommon.DataResponse;
import com.timesheet.syborgtech.exceptions.UserNotFoundException;
import com.timesheet.syborgtech.model.Role;
import com.timesheet.syborgtech.model.Subtask;
import com.timesheet.syborgtech.model.User;
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

    public Response createSubTask(SubTaskRequestDto subTaskRequestDto) {

        Optional<User> user = userRepository.findById(subTaskRequestDto.getUserId());
        if (!user.isPresent()) {
            throw new UserNotFoundException("User Not Found Exception");
        }

        Subtask subtask = new Subtask();
        subtask.setName(subTaskRequestDto.getName());
        subtask.setStatus(subTaskRequestDto.getStatus());
        subtask.setTask(taskRepository.findById(subTaskRequestDto.getTaskId()).get());
        subtask.setUser(user.get());
        subtask.setReporterId(subTaskRequestDto.getReporterId());
        subtask.setActualHours(subTaskRequestDto.getActualHours());
        subtask.setBudgetedHours(0L);
        subtask.setStartDate(subTaskRequestDto.getStartDate());
        subtask.setEndDate(subTaskRequestDto.getEndDate());
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
            responseDto.setUser(subTask.getUser());
            responseDto.setReporterId(subTask.getReporterId());
            responseDto.setReporterName(user.isPresent() ? user.get().getFirstName() : "N/A");
            responseDto.setBudgetedHours(subTask.getBudgetedHours() != null ? subTask.getBudgetedHours() : 0L);
            responseDto.setActualHours(subTask.getActualHours() != null ? subTask.getActualHours() : 0L);
            responseDto.setStartDate(subTask.getStartDate());
            responseDto.setEndDate(subTask.getEndDate());
            responseDto.setCreateAt(subTask.getCreateAt());
            responseDto.setUpdatedAt(subTask.getUpdatedAt());

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

}
