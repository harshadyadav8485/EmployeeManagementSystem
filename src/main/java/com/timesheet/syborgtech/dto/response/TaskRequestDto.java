package com.timesheet.syborgtech.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timesheet.syborgtech.model.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDto {
    private Long taskId;
    private String name;
    private String description;
    private Long sprintId;
    private Long projectId;
    private Task.TaskStatus status;
    private Task.Priority priority;
    private Task.TaskType taskType;
    private Long userId;
    private Long epicId;
}
