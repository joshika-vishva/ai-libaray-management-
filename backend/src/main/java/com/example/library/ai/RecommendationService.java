package com.example.library.ai;

import com.example.library.dto.RecommendationResponse;

import java.util.List;

public interface RecommendationService {
    List<RecommendationResponse> topRecommendations(Long userId, int limit);
}
