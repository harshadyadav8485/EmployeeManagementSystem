package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtaskRepository extends JpaRepository<Subtask,Long> {
}
