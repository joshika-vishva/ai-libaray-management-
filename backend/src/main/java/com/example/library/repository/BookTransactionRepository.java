package com.example.library.repository;

import com.example.library.entity.BookTransaction;
import com.example.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookTransactionRepository extends JpaRepository<BookTransaction, Long> {
    List<BookTransaction> findByUser(User user);
    List<BookTransaction> findByDueDateBeforeAndReturnDateIsNull(LocalDate date);
    Optional<BookTransaction> findByBookIdAndUserIdAndReturnDateIsNull(Long bookId, Long userId);
}
