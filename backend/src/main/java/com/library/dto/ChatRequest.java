package com.library.dto;

import lombok.Data;

@Data
public class ChatRequest {
    private Long userId;
    private String query;
}
