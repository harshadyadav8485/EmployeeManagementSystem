package com.timesheet.syborgtech.dto.response;


import com.timesheet.syborgtech.dtoCommon.DataResponse;
import com.timesheet.syborgtech.model.Sprint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SprintResponseDto implements DataResponse {
    private int currentPage;
    private Long totalElements;
    private Integer pageSize;
    private Integer totalPages;
    private List<SprintListResponseDto> sprintListResponseDtos;
   // private List<Sprint> sprints;
}
