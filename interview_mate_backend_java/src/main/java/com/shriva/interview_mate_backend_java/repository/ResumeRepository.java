package com.shriva.interview_mate_backend_java.repository;

import com.shriva.interview_mate_backend_java.model.Resume;
import com.shriva.interview_mate_backend_java.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByUser(User user);
}