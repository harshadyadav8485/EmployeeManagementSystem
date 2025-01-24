package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Sprint;
import com.timesheet.syborgtech.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
