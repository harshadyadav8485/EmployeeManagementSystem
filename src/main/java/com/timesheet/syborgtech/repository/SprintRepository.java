package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Sprint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends JpaRepository<Sprint,Long> {

    boolean existsByNameAndProjects_Id(String name, Long projectId);

    Page<Sprint> findAll(Specification<Sprint> containsSprint, Pageable page);

    @Query("SELECT S FROM sprints  S WHERE S.ID = :sprintId")
    Page<Sprint> findAllById(@Param("sprintId") Long sprintId,Pageable page);

    Page<Sprint> findAll(Pageable page);
}
