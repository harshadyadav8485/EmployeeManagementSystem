package com.timesheet.syborgtech.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timesheet.syborgtech.model.Sprint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SprintRequestDto {
    private Long id;

    @NotBlank(message = "Sprint name is required")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
    private Date endDate;

    @NotBlank(message = "SprintStatus name is required")
    private Sprint.SprintStatus sprintStatus;

    @NotBlank(message = "ProjectId is required")
    private Long projectId;
}
