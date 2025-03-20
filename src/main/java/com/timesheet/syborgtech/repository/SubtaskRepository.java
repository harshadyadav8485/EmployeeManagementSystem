package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Subtask;
import com.timesheet.syborgtech.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SubtaskRepository extends JpaRepository<Subtask,Long> {
    Page<Subtask> findAll(Specification<Subtask> containsSubTask, Pageable page);

    Page<Subtask> findAll( Pageable page);
    Page<Subtask> findAllById(Long subTaskId, Pageable page);
    Page<Subtask> findAllByTask_TaskId(Long taskId, Pageable pageable);
    List<Subtask> findAllByUser_Id(Long userId, Sort sort);

    List<Subtask> findAllByTaskIn(List<Task> tasks,Sort sort);
    List<Subtask> findAllById(Long subTaskId,Sort sort);
    List<Subtask> findAllByTask_TaskId(Long taskId,Sort sort);

    @Query("SELECT st FROM Subtask st WHERE st.task IN (:tasks)")
    List<Subtask> findByTask(@Param("tasks")List<Task> tasks);
}
