package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PageRepository extends JpaRepository<Page, Long> {
    boolean existsByName(String name);

    org.springframework.data.domain.Page<Page> findAll(Specification<Page> containsPage, Pageable page);
   
    Page findByName(String name);
}
