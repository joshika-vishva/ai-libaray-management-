package com.example.library.controller;

import com.example.library.dto.IssueRequest;
import com.example.library.dto.ReturnRequest;
import com.example.library.entity.BookTransaction;
import com.example.library.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/issue")
    public ResponseEntity<BookTransaction> issueBook(@Valid @RequestBody IssueRequest request) {
        return ResponseEntity.ok(transactionService.issueBook(request));
    }

    @PostMapping("/return")
    public ResponseEntity<BookTransaction> returnBook(@Valid @RequestBody ReturnRequest request) {
        return ResponseEntity.ok(transactionService.returnBook(request));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<BookTransaction>> userHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(transactionService.userHistory(userId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/overdue")
    public ResponseEntity<List<BookTransaction>> overdue() {
        return ResponseEntity.ok(transactionService.overdueTransactions());
    }
}
