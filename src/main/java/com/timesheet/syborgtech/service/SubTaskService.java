package com.timesheet.syborgtech.service;

import com.timesheet.syborgtech.dto.response.Response;
import com.timesheet.syborgtech.dto.response.SubTaskRequestDto;
import com.timesheet.syborgtech.dtoCommon.DataResponse;
import com.timesheet.syborgtech.exceptions.UserNotFoundException;
import com.timesheet.syborgtech.model.Subtask;
import com.timesheet.syborgtech.model.User;
import com.timesheet.syborgtech.repository.SubtaskRepository;
import com.timesheet.syborgtech.repository.TaskRepository;
import com.timesheet.syborgtech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class SubTaskService {

    @Autowired
    private SubtaskRepository subtaskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public Response createSubTask(SubTaskRequestDto subTaskRequestDto) {

        Optional<User> user=userRepository.findById(subTaskRequestDto.getUserId());
        if(!user.isPresent()){
            throw new UserNotFoundException("User Not Found Exception");
        }

        Subtask subtask=new Subtask();
        subtask.setName(subTaskRequestDto.getName());
        subtask.setStatus(subTaskRequestDto.getStatus());
        subtask.setTask(taskRepository.findById(subTaskRequestDto.getTaskId()).get());
        subtask.setUser(user.get());
        subtask.setReporterId(subTaskRequestDto.getReporterId());
        subtask.setActualHours(subTaskRequestDto.getActualHours());
        subtask.setBudgetedHours(subTaskRequestDto.getBudgetedHours());
        subtask.setStartDate(subTaskRequestDto.getStartDate());
        subtask.setEndDate(subTaskRequestDto.getEndDate());
        subtaskRepository.save(subtask);

        return Response.builder().message("SubTask Created Successfully").build();
    }
}
