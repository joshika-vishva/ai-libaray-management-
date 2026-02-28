package com.example.library.ai.impl;

import com.example.library.ai.SemanticSearchService;
import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultSemanticSearchService implements SemanticSearchService {

    private final BookRepository bookRepository;

    @Override
    public Page<Book> semanticSearch(String query, int page, int size) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrCategoryContainingIgnoreCase(
                query, query, query, PageRequest.of(page, size));
    }
}
