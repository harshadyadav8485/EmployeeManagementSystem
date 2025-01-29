package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintRepository extends JpaRepository<Sprint,Long> {

    boolean existsByNameAndProjects_Id(String name, Long projectId);

    List<Sprint> findAllByProjects_Id(Long projectId);

}
