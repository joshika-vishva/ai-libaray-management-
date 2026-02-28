package com.library.controller;

import com.library.dto.BorrowTransactionResponse;
import com.library.dto.IssueRequest;
import com.library.dto.ReturnRequest;
import com.library.service.BorrowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
@RequiredArgsConstructor
public class BorrowController {
    private final BorrowService borrowService;

    @PostMapping("/issue")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BorrowTransactionResponse> issue(@Valid @RequestBody IssueRequest request) {
        return ResponseEntity.ok(borrowService.issue(request.getUserId(), request.getBookId()));
    }

    @PostMapping("/return")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<BorrowTransactionResponse> returnBook(@Valid @RequestBody ReturnRequest request) {
        return ResponseEntity.ok(borrowService.returnBook(request.getTransactionId()));
    }

    @GetMapping("/history/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<BorrowTransactionResponse>> history(@PathVariable Long userId) {
        return ResponseEntity.ok(borrowService.userHistory(userId));
    }

    @GetMapping("/overdue")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BorrowTransactionResponse>> overdue() {
        return ResponseEntity.ok(borrowService.overdue());
    }
}
