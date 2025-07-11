package com.shriva.interview_mate_backend_java.service;

import com.shriva.interview_mate_backend_java.model.GeminiRequest;
import com.shriva.interview_mate_backend_java.model.GeminiResponse;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeminiResponse generateContent(String prompt) throws Exception {
        GeminiRequest requestBody = new GeminiRequest();
        GeminiRequest.Content content = new GeminiRequest.Content();
        GeminiRequest.Part part = new GeminiRequest.Part();
        part.setText(prompt);
        content.setParts(List.of(part));
        requestBody.setContents(List.of(content));

        RequestBody body = RequestBody.create(
                objectMapper.writeValueAsString(requestBody),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(apiUrl + "?key=" + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to call Gemini API: " + response);
            }
            return objectMapper.readValue(response.body().string(), GeminiResponse.class);
        }
    }
}