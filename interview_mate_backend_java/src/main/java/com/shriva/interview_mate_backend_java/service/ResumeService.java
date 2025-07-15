package com.shriva.interview_mate_backend_java.service;

import com.shriva.interview_mate_backend_java.model.Resume;
import com.shriva.interview_mate_backend_java.model.User;
import com.shriva.interview_mate_backend_java.repository.ResumeRepository;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GeminiService geminiService;

    public Resume uploadResume(MultipartFile file, String email) throws Exception {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Extract text from PDF using Tika
        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        parser.parse(new ByteArrayInputStream(file.getBytes()), handler, metadata);

        Resume resume = new Resume();
        resume.setUser(user);
        resume.setFileName(file.getOriginalFilename());
        resume.setFileContent(file.getBytes());
        resume.setExtractedText(handler.toString());

        // Analyze resume with Gemini API
        String prompt = "Analyze this resume and provide feedback on its structure, content, and suitability for a software developer role: " + resume.getExtractedText();
        String feedback = geminiService.generateContent(prompt)
                .getCandidates().get(0).getContent().getParts().get(0).getText();
        resume.setExtractedText(resume.getExtractedText() + "\n\nFeedback: " + feedback);

        return resumeRepository.save(resume);
    }

    public List<Resume> getUserResumes(String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return resumeRepository.findByUser(user);
    }
}