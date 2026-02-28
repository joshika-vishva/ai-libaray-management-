package com.example.library.service.impl;

import com.example.library.dto.IssueRequest;
import com.example.library.dto.ReturnRequest;
import com.example.library.entity.Book;
import com.example.library.entity.BookTransaction;
import com.example.library.entity.TransactionType;
import com.example.library.entity.User;
import com.example.library.exception.BusinessException;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BookTransactionRepository;
import com.example.library.repository.UserRepository;
import com.example.library.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private static final BigDecimal DAILY_FINE = BigDecimal.valueOf(5);

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookTransactionRepository transactionRepository;

    @Override
    public BookTransaction issueBook(IssueRequest request) {
        Book book = bookRepository.findById(request.getBookId()).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new BusinessException("Book out of stock");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        BookTransaction transaction = BookTransaction.builder()
                .book(book)
                .user(user)
                .transactionType(TransactionType.ISSUE)
                .issueDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(14))
                .fineAmount(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .build();

        return transactionRepository.save(transaction);
    }

    @Override
    public BookTransaction returnBook(ReturnRequest request) {
        BookTransaction tx = transactionRepository.findByBookIdAndUserIdAndReturnDateIsNull(request.getBookId(), request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Active issue transaction not found"));

        LocalDate today = LocalDate.now();
        tx.setReturnDate(today);
        tx.setTransactionType(TransactionType.RETURN);

        long overdueDays = Math.max(0, ChronoUnit.DAYS.between(tx.getDueDate(), today));
        tx.setFineAmount(DAILY_FINE.multiply(BigDecimal.valueOf(overdueDays)));

        Book book = tx.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        return transactionRepository.save(tx);
    }

    @Override
    public List<BookTransaction> userHistory(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return transactionRepository.findByUser(user);
    }

    @Override
    public List<BookTransaction> overdueTransactions() {
        return transactionRepository.findByDueDateBeforeAndReturnDateIsNull(LocalDate.now());
    }
}
