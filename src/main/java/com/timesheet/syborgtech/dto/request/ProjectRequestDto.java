package com.timesheet.syborgtech.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequestDto {
    @NotBlank(message = "projectName is required")
    private String projectName;

    @NotBlank(message = "description is required")
    private String description;
}
