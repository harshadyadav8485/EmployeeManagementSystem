package com.timesheet.syborgtech.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageListResponseDto {
    private Long pageId;
    private String pageName;

}
