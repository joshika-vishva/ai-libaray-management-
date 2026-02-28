package com.library.service.impl;

import com.library.dto.BorrowTransactionResponse;
import com.library.entity.Book;
import com.library.entity.BorrowTransaction;
import com.library.entity.TransactionLog;
import com.library.enums.TransactionType;
import com.library.exception.BadRequestException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;
import com.library.repository.BorrowTransactionRepository;
import com.library.repository.TransactionLogRepository;
import com.library.repository.UserRepository;
import com.library.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {

    private static final BigDecimal DAILY_FINE = new BigDecimal("2.00");

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BorrowTransactionRepository borrowTransactionRepository;
    private final TransactionLogRepository transactionLogRepository;

    @Override
    @Transactional
    public BorrowTransactionResponse issue(Long userId, Long bookId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        if (book.getAvailableCopies() <= 0) {
            throw new BadRequestException("Book out of stock");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        BorrowTransaction tx = BorrowTransaction.builder()
                .user(user)
                .book(book)
                .issueDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(14))
                .fineAmount(BigDecimal.ZERO)
                .returned(false)
                .build();

        tx = borrowTransactionRepository.save(tx);
        log(tx, TransactionType.ISSUE, "Book issued successfully");
        return map(tx);
    }

    @Override
    @Transactional
    public BorrowTransactionResponse returnBook(Long transactionId) {
        BorrowTransaction tx = borrowTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        if (tx.getReturned()) throw new BadRequestException("Already returned");

        tx.setReturnDate(LocalDate.now());
        tx.setReturned(true);
        long overdueDays = Math.max(0, ChronoUnit.DAYS.between(tx.getDueDate(), tx.getReturnDate()));
        tx.setFineAmount(DAILY_FINE.multiply(BigDecimal.valueOf(overdueDays)));

        Book book = tx.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        BorrowTransaction saved = borrowTransactionRepository.save(tx);
        log(saved, TransactionType.RETURN, "Book returned");
        if (saved.getFineAmount().compareTo(BigDecimal.ZERO) > 0) {
            log(saved, TransactionType.FINE_APPLIED, "Fine applied: " + saved.getFineAmount());
        }
        return map(saved);
    }

    @Override
    public List<BorrowTransactionResponse> userHistory(Long userId) {
        return borrowTransactionRepository.findByUserId(userId).stream().map(this::map).toList();
    }

    @Override
    public List<BorrowTransactionResponse> overdue() {
        return borrowTransactionRepository.findByReturnedFalseAndDueDateBefore(LocalDate.now()).stream().map(this::map).toList();
    }

    private BorrowTransactionResponse map(BorrowTransaction tx) {
        return BorrowTransactionResponse.builder()
                .id(tx.getId())
                .userId(tx.getUser().getId())
                .bookId(tx.getBook().getId())
                .bookTitle(tx.getBook().getTitle())
                .issueDate(tx.getIssueDate())
                .dueDate(tx.getDueDate())
                .returnDate(tx.getReturnDate())
                .fineAmount(tx.getFineAmount())
                .returned(tx.getReturned())
                .build();
    }

    private void log(BorrowTransaction tx, TransactionType type, String details) {
        transactionLogRepository.save(TransactionLog.builder()
                .borrowTransaction(tx)
                .type(type)
                .details(details)
                .createdAt(LocalDateTime.now())
                .build());
    }
}
