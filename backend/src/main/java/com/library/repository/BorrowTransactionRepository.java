package com.library.repository;

import com.library.entity.BorrowTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BorrowTransactionRepository extends JpaRepository<BorrowTransaction, Long> {
    List<BorrowTransaction> findByUserId(Long userId);
    List<BorrowTransaction> findByReturnedFalseAndDueDateBefore(LocalDate date);
}
