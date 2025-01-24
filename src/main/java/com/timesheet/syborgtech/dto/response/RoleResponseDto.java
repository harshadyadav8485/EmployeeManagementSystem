package com.timesheet.syborgtech.dto.response;

import com.timesheet.syborgtech.dtoCommon.DataResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponseDto implements DataResponse {
    private int currentPage;
    private Long totalElements;
    private Integer pageSize;
    private Integer totalPages;
    private List<RoleListResponseDto> roleListResponseDtos;
}
