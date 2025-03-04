package com.timesheet.syborgtech.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EpicResponseDtoList {
    private Long epicId;
    private String epicName;
    private ProjectListResponseDto projectListResponseDto;
}
