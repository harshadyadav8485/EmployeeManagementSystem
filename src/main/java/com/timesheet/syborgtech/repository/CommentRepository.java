package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Page<Comment> findById(Long commentId, Pageable page);

    Page<Comment> findAll(Specification<Comment> commentSpecification, Pageable page);
}
