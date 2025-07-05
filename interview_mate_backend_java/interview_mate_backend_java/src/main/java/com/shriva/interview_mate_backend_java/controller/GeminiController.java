package com.shriva.interview_mate_backend_java.controller;

import com.shriva.interview_mate_backend_java.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/generate")
    public String generateContent(@RequestBody String prompt) throws IOException {
        return geminiService.generateContent(prompt);
    }
}