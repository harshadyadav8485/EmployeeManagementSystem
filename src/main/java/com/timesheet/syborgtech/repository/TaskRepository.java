package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllBySprint_Id(Long SprintId);

    List<Task> findAllBySprint_IdIn(List<Long> SprintIds);

    Page<Task> findAll(Specification<Task> containsTask, Pageable page);
}
