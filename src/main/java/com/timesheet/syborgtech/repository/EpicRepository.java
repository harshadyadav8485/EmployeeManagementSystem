package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Epic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpicRepository extends JpaRepository<Epic, Long> {

    boolean existsByName(String name);
    Page<Epic> findAll(Specification<Epic> containsPage, Pageable page);

    Page<Epic> findByProjectsId(Long projectId,Pageable page);
}
