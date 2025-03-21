package com.timesheet.syborgtech.dto.response;

import com.timesheet.syborgtech.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskListResponseDto {
    private Long taskId;
    private Sprint sprint;
    private String taskName;
    private String description;
    private Task.TaskStatus status;
    private Task.Priority priority;
    private Date createAt;
    private Date updatedAt;
    //private List<SubTaskListResponseDto> subtaskList;
   // private List<CommentListResponseDto> comments;
    private String epic;
    private Long epicId;
    private Task.TaskType taskType;
    //private User user;
    private Long reporterId;
}
