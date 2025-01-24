package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Override
    Optional<Role> findById(Long aLong);

    Optional<Role> findByRoleName(String roleName);

    Page<Role> findAll(Specification<Role> containsText, Pageable page);

    boolean existsByRoleNameAndIdNot(String roleName, Long roleId);

}
