package com.timesheet.syborgtech.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EpicRequestDto {
    private Long id;

    @NotBlank(message = "name is required")
    private String name;
}
