package com.library.service;

import com.library.dto.AiRecommendationResponse;
import com.library.dto.ChatResponse;

import java.util.List;
import java.util.Map;

public interface AiService {
    AiRecommendationResponse recommend(Long userId);
    ChatResponse chat(Long userId, String query);
    Map<String, Double> lateReturnRisk(Long userId);
    List<String> semanticSearch(String query);
}
