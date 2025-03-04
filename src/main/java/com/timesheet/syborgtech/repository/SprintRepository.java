package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Sprint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintRepository extends JpaRepository<Sprint,Long> {

    boolean existsByNameAndProjects_Id(String name, Long projectId);
    List<Sprint> findAllByProjects_Id(Long projectId);


    Page<Sprint> findAllById(Long sprintId, Pageable pageable);

    Page<Sprint> findAll( Pageable pageable);

    Page<Sprint> findAll(Specification<Sprint> containsSprint, Pageable page);
}
