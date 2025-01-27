package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Epic;
import com.timesheet.syborgtech.model.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpicRepository extends JpaRepository<Epic, Long> {

    boolean existsByName(String name);
}
