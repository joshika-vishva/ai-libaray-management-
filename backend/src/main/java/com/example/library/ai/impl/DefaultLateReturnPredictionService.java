package com.example.library.ai.impl;

import com.example.library.ai.LateReturnPredictionService;
import org.springframework.stereotype.Service;

@Service
public class DefaultLateReturnPredictionService implements LateReturnPredictionService {
    @Override
    public double predictRiskScore(Long userId, Long bookId) {
        return (userId % 10 + bookId % 10) / 20.0;
    }
}
