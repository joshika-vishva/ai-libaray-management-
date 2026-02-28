package com.library.service.impl;

import com.library.dto.BookRequest;
import com.library.dto.BookResponse;
import com.library.entity.Book;
import com.library.enums.BookCategory;
import com.library.exception.ResourceNotFoundException;
import com.library.mapper.BookMapper;
import com.library.repository.BookRepository;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public BookResponse create(BookRequest request) {
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .category(request.getCategory())
                .description(request.getDescription())
                .totalCopies(request.getTotalCopies())
                .availableCopies(request.getTotalCopies())
                .build();
        return BookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    public BookResponse update(Long id, BookRequest request) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setCategory(request.getCategory());
        book.setDescription(request.getDescription());
        book.setTotalCopies(request.getTotalCopies());
        if (book.getAvailableCopies() > request.getTotalCopies()) {
            book.setAvailableCopies(request.getTotalCopies());
        }
        return BookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) throw new ResourceNotFoundException("Book not found");
        bookRepository.deleteById(id);
    }

    @Override
    public Page<BookResponse> list(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return bookRepository.findAll(PageRequest.of(page, size, sort)).map(BookMapper::toResponse);
    }

    @Override
    public Page<BookResponse> search(String query, BookCategory category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (category != null) {
            return bookRepository.findByCategory(category, pageable).map(BookMapper::toResponse);
        }
        String q = query == null ? "" : query;
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(q, q, pageable).map(BookMapper::toResponse);
    }
}
