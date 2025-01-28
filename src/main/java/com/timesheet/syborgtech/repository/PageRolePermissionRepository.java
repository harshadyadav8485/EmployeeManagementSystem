package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.PageRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRolePermissionRepository extends JpaRepository<PageRolePermission, Long> {
    boolean existsByPageIdAndRoleId(Long pageId, Long roleId);

}