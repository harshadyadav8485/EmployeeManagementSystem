package com.timesheet.syborgtech.dto.request;

import com.timesheet.syborgtech.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDtoRequest {

    private Long roleId;

    @NotBlank(message = "roleName is required")
    private String roleName;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "Status is required")
    private Role.RoleStatus Status;
}
