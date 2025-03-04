package com.timesheet.syborgtech.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timesheet.syborgtech.model.Projects;
import com.timesheet.syborgtech.model.Sprint;
import com.timesheet.syborgtech.model.Task;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SprintResponseList {
    private Long id;
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;
    private Sprint.SprintStatus sprintStatus;
    private Long projectId;
    private String projectName;
    private List<TaskListResponseDto> taskList;
}
