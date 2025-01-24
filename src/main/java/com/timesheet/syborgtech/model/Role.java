package com.timesheet.syborgtech.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name",nullable = false,unique = true)
    private String roleName;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RoleStatus Status;

    public enum RoleStatus {
        ACTIVE, INACTIVE
    }
}



