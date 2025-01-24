package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
