package com.timesheet.syborgtech.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timesheet.syborgtech.dtoCommon.DataResponse;
import com.timesheet.syborgtech.model.Subtask;
import com.timesheet.syborgtech.model.Task;
import com.timesheet.syborgtech.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SubTasksResponseDto implements DataResponse {
    private int currentPage;
    private Long totalElements;
    private Integer pageSize;
    private Integer totalPages;
    private List<SubTaskListResponseDto> subTaskListResponseDtos;
}
