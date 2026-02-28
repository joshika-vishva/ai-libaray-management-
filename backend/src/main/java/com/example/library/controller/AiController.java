package com.example.library.controller;

import com.example.library.ai.ChatbotService;
import com.example.library.ai.LateReturnPredictionService;
import com.example.library.ai.RecommendationService;
import com.example.library.dto.RecommendationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final RecommendationService recommendationService;
    private final ChatbotService chatbotService;
    private final LateReturnPredictionService predictionService;

    @GetMapping("/recommendations/{userId}")
    public ResponseEntity<List<RecommendationResponse>> recommendations(@PathVariable Long userId) {
        return ResponseEntity.ok(recommendationService.topRecommendations(userId, 5));
    }

    @PostMapping("/chatbot")
    public ResponseEntity<Map<String, String>> chatbot(@RequestParam Long userId, @RequestParam String query) {
        return ResponseEntity.ok(Map.of("response", chatbotService.respond(userId, query)));
    }

    @GetMapping("/risk-score")
    public ResponseEntity<Map<String, Double>> riskScore(@RequestParam Long userId, @RequestParam Long bookId) {
        return ResponseEntity.ok(Map.of("riskScore", predictionService.predictRiskScore(userId, bookId)));
    }
}
