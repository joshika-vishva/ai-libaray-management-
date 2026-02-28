package com.example.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReturnRequest {
    @NotNull
    private Long bookId;
    @NotNull
    private Long userId;
}
