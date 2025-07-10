package com.shriva.interview_mate_backend_java.repository;

import com.shriva.interview_mate_backend_java.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}