package com.library.controller;

import com.library.dto.AiRecommendationResponse;
import com.library.dto.ChatRequest;
import com.library.dto.ChatResponse;
import com.library.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {
    private final AiService aiService;

    @GetMapping("/recommend/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<AiRecommendationResponse> recommend(@PathVariable Long userId) {
        return ResponseEntity.ok(aiService.recommend(userId));
    }

    @PostMapping("/chat")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        return ResponseEntity.ok(aiService.chat(request.getUserId(), request.getQuery()));
    }

    @GetMapping("/risk/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Double>> risk(@PathVariable Long userId) {
        return ResponseEntity.ok(aiService.lateReturnRisk(userId));
    }

    @GetMapping("/semantic-search")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<String>> semanticSearch(@RequestParam String query) {
        return ResponseEntity.ok(aiService.semanticSearch(query));
    }
}
