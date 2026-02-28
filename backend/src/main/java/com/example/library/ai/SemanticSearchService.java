package com.example.library.ai;

import com.example.library.entity.Book;
import org.springframework.data.domain.Page;

public interface SemanticSearchService {
    Page<Book> semanticSearch(String query, int page, int size);
}
