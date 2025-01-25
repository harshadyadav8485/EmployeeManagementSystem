package com.timesheet.syborgtech.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignProjectDto {
    private Long userId;
    private List<Long> projectIds;
}
