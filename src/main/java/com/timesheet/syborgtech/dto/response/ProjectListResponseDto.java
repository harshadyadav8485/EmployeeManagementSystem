package com.timesheet.syborgtech.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timesheet.syborgtech.dtoCommon.DataResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectListResponseDto {

    private Long projectId;
    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createAt;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    private String projectName;
    private String projectDescription;
}
