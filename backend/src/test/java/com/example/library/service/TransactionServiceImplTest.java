package com.example.library.service;

import com.example.library.dto.ReturnRequest;
import com.example.library.entity.Book;
import com.example.library.entity.BookTransaction;
import com.example.library.entity.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BookTransactionRepository;
import com.example.library.repository.UserRepository;
import com.example.library.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookTransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void shouldCalculateFineOnLateReturn() {
        Book book = Book.builder().id(1L).availableCopies(0).build();
        User user = User.builder().id(2L).build();
        BookTransaction tx = BookTransaction.builder()
                .book(book)
                .user(user)
                .issueDate(LocalDate.now().minusDays(20))
                .dueDate(LocalDate.now().minusDays(3))
                .fineAmount(BigDecimal.ZERO)
                .build();

        when(transactionRepository.findByBookIdAndUserIdAndReturnDateIsNull(1L, 2L)).thenReturn(Optional.of(tx));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(transactionRepository.save(any(BookTransaction.class))).thenAnswer(inv -> inv.getArgument(0));

        ReturnRequest req = new ReturnRequest();
        req.setBookId(1L);
        req.setUserId(2L);

        BookTransaction result = transactionService.returnBook(req);

        assertEquals(BigDecimal.valueOf(15), result.getFineAmount());
    }
}
