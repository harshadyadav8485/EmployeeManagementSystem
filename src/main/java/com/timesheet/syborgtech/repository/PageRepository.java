package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PageRepository extends JpaRepository<Page, Long> {
    boolean existsByName(String name);
}
