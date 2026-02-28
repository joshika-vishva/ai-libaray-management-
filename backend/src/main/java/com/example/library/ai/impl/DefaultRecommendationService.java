package com.example.library.ai.impl;

import com.example.library.ai.RecommendationService;
import com.example.library.dto.RecommendationResponse;
import com.example.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultRecommendationService implements RecommendationService {

    private final BookRepository bookRepository;

    @Override
    public List<RecommendationResponse> topRecommendations(Long userId, int limit) {
        return bookRepository.findAll().stream()
                .limit(limit)
                .map(b -> new RecommendationResponse(b.getId(), b.getTitle(), Math.random()))
                .toList();
    }
}
