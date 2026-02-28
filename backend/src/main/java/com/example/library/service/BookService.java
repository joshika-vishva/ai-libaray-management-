package com.example.library.service;

import com.example.library.dto.BookRequest;
import com.example.library.entity.Book;
import org.springframework.data.domain.Page;

public interface BookService {
    Book addBook(BookRequest request);
    Book updateBook(Long id, BookRequest request);
    void deleteBook(Long id);
    Page<Book> searchBooks(String query, int page, int size, String sortBy, String direction);
}
