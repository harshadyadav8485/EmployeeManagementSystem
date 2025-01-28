package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Subtask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtaskRepository extends JpaRepository<Subtask,Long> {
    Page<Subtask> findAll(Specification<Subtask> containsSubTask, Pageable page);

    Page<Subtask> findAll( Pageable page);
    Page<Subtask> findAllById(Long subTaskId, Pageable page);
    Page<Subtask> findAllByTask_TaskId(Long taskId, Pageable pageable);

}
