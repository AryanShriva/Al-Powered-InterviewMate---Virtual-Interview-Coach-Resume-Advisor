package com.shriva.interview_mate_backend_java.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shriva.interview_mate_backend_java.model.GeminiRequest;
import com.shriva.interview_mate_backend_java.model.GeminiResponse;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GeminiService {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    public String generateContent(String prompt) throws IOException {
        // Create request payload
        GeminiRequest requestPayload = new GeminiRequest(
                List.of(new GeminiRequest.Content(
                        List.of(new GeminiRequest.Content.Part(prompt))
                ))
        );

        // Convert to JSON
        String jsonBody = objectMapper.writeValueAsString(requestPayload);

        // Build HTTP request
        Request request = new Request.Builder()
                .url(apiUrl)
                .header("Content-Type", "application/json")
                .header("X-goog-api-key", apiKey)
                .post(RequestBody.create(jsonBody, MediaType.parse("application/json")))
                .build();

        // Execute request
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Parse response
            GeminiResponse geminiResponse = objectMapper.readValue(response.body().string(), GeminiResponse.class);
            return geminiResponse.getCandidates().get(0).getContent().getParts().get(0).getText();
        }
    }
}