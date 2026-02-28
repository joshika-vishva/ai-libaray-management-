package com.example.library.ai;

public interface LateReturnPredictionService {
    double predictRiskScore(Long userId, Long bookId);
}
