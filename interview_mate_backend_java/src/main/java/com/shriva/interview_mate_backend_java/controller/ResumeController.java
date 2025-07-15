package com.shriva.interview_mate_backend_java.controller;

import com.shriva.interview_mate_backend_java.model.Resume;
import com.shriva.interview_mate_backend_java.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Resume resume = resumeService.uploadResume(file, email);
            return ResponseEntity.ok("Resume uploaded and analyzed: " + resume.getExtractedText());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Resume>> getUserResumes() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            List<Resume> resumes = resumeService.getUserResumes(email);
            return ResponseEntity.ok(resumes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}