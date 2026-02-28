package com.library.service.impl;

import com.library.ai.AiClient;
import com.library.dto.AiRecommendationResponse;
import com.library.dto.ChatResponse;
import com.library.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {
    private final AiClient aiClient;

    @Override
    public AiRecommendationResponse recommend(Long userId) {
        return aiClient.recommend(userId);
    }

    @Override
    public ChatResponse chat(Long userId, String query) {
        return aiClient.chat(userId, query);
    }

    @Override
    public Map<String, Double> lateReturnRisk(Long userId) {
        return aiClient.risk(userId);
    }

    @Override
    public List<String> semanticSearch(String query) {
        return aiClient.semanticSearch(query);
    }
}
