package com.library.service;

import com.library.dto.BorrowTransactionResponse;

import java.util.List;

public interface BorrowService {
    BorrowTransactionResponse issue(Long userId, Long bookId);
    BorrowTransactionResponse returnBook(Long transactionId);
    List<BorrowTransactionResponse> userHistory(Long userId);
    List<BorrowTransactionResponse> overdue();
}
