package com.timesheet.syborgtech.repository;

import com.timesheet.syborgtech.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserName(String userName);

    Optional<User> findByEmail(String email);

    Page<User> findAll(Specification<User> containsUser, Pageable page);
}
