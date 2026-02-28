package com.library.dto;

import com.library.enums.BookCategory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private BookCategory category;
    private String description;
    private Integer totalCopies;
    private Integer availableCopies;
}
