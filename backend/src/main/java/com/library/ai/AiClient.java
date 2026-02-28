package com.library.ai;

import com.library.dto.AiRecommendationResponse;
import com.library.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AiClient {

    @Value("${ai.service.base-url}")
    private String baseUrl;

    private final RestClient restClient = RestClient.create();

    public AiRecommendationResponse recommend(Long userId) {
        return restClient.get().uri(baseUrl + "/recommend/" + userId).retrieve().body(AiRecommendationResponse.class);
    }

    public ChatResponse chat(Long userId, String query) {
        return restClient.post().uri(baseUrl + "/chat")
                .body(Map.of("user_id", userId, "query", query))
                .retrieve().body(ChatResponse.class);
    }

    public Map<String, Double> risk(Long userId) {
        return restClient.get().uri(baseUrl + "/predict-late-return/" + userId).retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public List<String> semanticSearch(String query) {
        return restClient.post().uri(baseUrl + "/semantic-search")
                .body(Map.of("query", query))
                .retrieve().body(new ParameterizedTypeReference<>() {});
    }
}
