package com.timesheet.syborgtech.dto.response;

import com.timesheet.syborgtech.dtoCommon.DataResponse;
import lombok.Data;

import java.util.List;

@Data
public class SprintResponse implements DataResponse {
    private int currentPage;
    private Long totalElements;
    private Integer pageSize;
    private Integer totalPages;
    private List<SprintResponseList> sprintResponseListList;
}
