package com.timesheet.syborgtech.dto.response;

import com.timesheet.syborgtech.model.Sprint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SprintListResponseDto {
    private Long sprintId;
    private String sprintName;
    private Date createAt;
    private Date updatedAt;
    private Date startDate;
    private Date endDate;
    private Sprint.SprintStatus sprintStatus;

}
