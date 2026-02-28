package com.example.library.service;

import com.example.library.dto.IssueRequest;
import com.example.library.dto.ReturnRequest;
import com.example.library.entity.BookTransaction;

import java.util.List;

public interface TransactionService {
    BookTransaction issueBook(IssueRequest request);
    BookTransaction returnBook(ReturnRequest request);
    List<BookTransaction> userHistory(Long userId);
    List<BookTransaction> overdueTransactions();
}
