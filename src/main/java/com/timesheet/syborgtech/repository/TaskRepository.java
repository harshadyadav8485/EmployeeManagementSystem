package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Epic;
import com.timesheet.syborgtech.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllBySprint_Id(Long SprintId);

    List<Task> findAllBySprint_IdIn(List<Long> SprintIds);

    Page<Task> findAll(Specification<Task> containsTask, Pageable page);

    @Query("SELECT t FROM Task t WHERE t.epic =:epic")
    List<Task> findByEpic(@Param("epic") Epic epic);
}
