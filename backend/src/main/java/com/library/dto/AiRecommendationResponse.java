package com.library.dto;

import lombok.Data;

import java.util.List;

@Data
public class AiRecommendationResponse {
    private List<String> recommendations;
}
