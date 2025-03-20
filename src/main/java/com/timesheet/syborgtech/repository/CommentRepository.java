package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Comment;
import com.timesheet.syborgtech.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Page<Comment> findById(Long commentId, Pageable page);

    Page<Comment> findAll(Specification<Comment> commentSpecification, Pageable page);

    @Query("SELECT c FROM Comment c WHERE c.task IN (:tasks)")
    List<Comment> findByTask(@Param("tasks") List<Task> tasks);
}
