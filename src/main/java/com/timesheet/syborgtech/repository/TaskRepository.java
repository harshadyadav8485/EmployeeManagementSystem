package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Sprint;
import com.timesheet.syborgtech.model.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllBySprint_Id(Long SprintId);
    List<Task> findAllBySprint_IdIn(List<Long> SprintIds);

}
