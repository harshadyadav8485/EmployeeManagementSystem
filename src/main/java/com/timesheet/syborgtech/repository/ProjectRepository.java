package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Projects;
import com.timesheet.syborgtech.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Projects, Long> {

    @Override
    Optional<Projects> findById(Long aLong);
    boolean existsByProjectName(String name);
    Page<Projects> findAll(Specification<Projects> containsProject, Pageable page);

    Page<Projects> findAllById(Long projectId,Pageable pageable);
    Page<Projects> findAll(Pageable pageable);

    Page<Projects> findByUsersId(Long userId, Pageable pageable);

}
