package com.timesheet.syborgtech.dto.response;

import com.timesheet.syborgtech.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleListResponseDto {
    private Long roleId;
    private String roleName;
    private String description;
    private Role.RoleStatus Status;
}
