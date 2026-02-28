package com.library.service;

import com.library.entity.Book;
import com.library.entity.BorrowTransaction;
import com.library.entity.User;
import com.library.enums.BookCategory;
import com.library.enums.Role;
import com.library.repository.BookRepository;
import com.library.repository.BorrowTransactionRepository;
import com.library.repository.TransactionLogRepository;
import com.library.repository.UserRepository;
import com.library.service.impl.BorrowServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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
class BorrowServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BorrowTransactionRepository borrowTransactionRepository;
    @Mock
    private TransactionLogRepository transactionLogRepository;

    @InjectMocks
    private BorrowServiceImpl borrowService;

    private User user;
    private Book book;

    @BeforeEach
    void setup() {
        user = User.builder().id(1L).email("u@x.com").fullName("U").password("x").role(Role.ROLE_USER).build();
        book = Book.builder().id(1L).title("Book").author("Author").category(BookCategory.TECHNOLOGY).totalCopies(2).availableCopies(2).build();
    }

    @Test
    void shouldIssueBook() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowTransactionRepository.save(any(BorrowTransaction.class))).thenAnswer(a -> {
            BorrowTransaction tx = a.getArgument(0);
            tx.setId(10L);
            return tx;
        });

        var response = borrowService.issue(1L, 1L);

        assertEquals(10L, response.getId());
        assertEquals(LocalDate.now().plusDays(14), response.getDueDate());
    }

    @Test
    void shouldCalculateFineOnReturn() {
        BorrowTransaction tx = BorrowTransaction.builder()
                .id(10L)
                .user(user)
                .book(book)
                .issueDate(LocalDate.now().minusDays(20))
                .dueDate(LocalDate.now().minusDays(3))
                .fineAmount(BigDecimal.ZERO)
                .returned(false)
                .build();

        when(borrowTransactionRepository.findById(10L)).thenReturn(Optional.of(tx));
        when(borrowTransactionRepository.save(any(BorrowTransaction.class))).thenAnswer(a -> a.getArgument(0));

        var response = borrowService.returnBook(10L);

        assertEquals(new BigDecimal("6.00"), response.getFineAmount());
    }
}
