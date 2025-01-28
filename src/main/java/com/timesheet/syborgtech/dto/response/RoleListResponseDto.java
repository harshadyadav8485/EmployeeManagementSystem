package com.timesheet.syborgtech.dto.response;

import com.timesheet.syborgtech.dto.request.PageRequestDto;
import com.timesheet.syborgtech.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleListResponseDto {
    private Long roleId;
    private String roleName;
    private String description;
    private Role.RoleStatus Status;
}
