package com.example.library.ai.impl;

import com.example.library.ai.ChatbotService;
import org.springframework.stereotype.Service;

@Service
public class DefaultChatbotService implements ChatbotService {
    @Override
    public String respond(Long userId, String query) {
        String normalized = query.toLowerCase();
        if (normalized.contains("availability")) {
            return "Please use /api/books/search to check availability.";
        }
        if (normalized.contains("due")) {
            return "Please check your latest transaction due date under user history.";
        }
        if (normalized.contains("fine")) {
            return "Fine is calculated as 5 units per overdue day.";
        }
        return "I can help with availability, due dates, and fines.";
    }
}
