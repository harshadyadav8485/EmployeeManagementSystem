package com.timesheet.syborgtech.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timesheet.syborgtech.model.Subtask;
import com.timesheet.syborgtech.model.Task;
import com.timesheet.syborgtech.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubTaskListResponseDto {
    private Long id;
    private String taskName;
    private String subTaskName;
    private Subtask.SubtaskStatus status;
    private String userName;
    private Long userId;
    private Long reporterId;
    private String reporterName;
    private Long budgetedHours;
    private Long actualHours;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Kolkata")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Kolkata")
    private Date endDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Kolkata")
    private Date createAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Kolkata")
    private Date updatedAt;


}
