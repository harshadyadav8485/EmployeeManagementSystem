package com.timesheet.syborgtech.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timesheet.syborgtech.model.Subtask;
import com.timesheet.syborgtech.model.Task;
import com.timesheet.syborgtech.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubTaskRequestDto {
    private Long id;
    private Long taskId;
    private String name;
    private Subtask.SubtaskStatus status;
    private Long userId;
    private Long reporterId;
   // private Long budgetedHours;
    private Long actualHours;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
    private Date endDate;
}
