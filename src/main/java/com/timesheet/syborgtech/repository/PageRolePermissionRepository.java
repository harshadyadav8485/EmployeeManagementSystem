package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.PageRolePermission;
import com.timesheet.syborgtech.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageRolePermissionRepository extends JpaRepository<PageRolePermission, Long> {
    boolean existsByPageIdAndRoleId(Long pageId, Long roleId);

    List<PageRolePermission> findAllByRole(Role role);
}