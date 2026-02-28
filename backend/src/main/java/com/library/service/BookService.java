package com.library.service;

import com.library.dto.BookRequest;
import com.library.dto.BookResponse;
import com.library.enums.BookCategory;
import org.springframework.data.domain.Page;

public interface BookService {
    BookResponse create(BookRequest request);
    BookResponse update(Long id, BookRequest request);
    void delete(Long id);
    Page<BookResponse> list(int page, int size, String sortBy, String direction);
    Page<BookResponse> search(String query, BookCategory category, int page, int size);
}
