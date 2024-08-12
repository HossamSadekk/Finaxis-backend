package com.finaxis.finaxis.controller;

import com.finaxis.finaxis.entity.Transaction;
import com.finaxis.finaxis.entity.User;
import com.finaxis.finaxis.model.moenyRequest.MoneyRequestResponseModel;
import com.finaxis.finaxis.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/check-username")
    public ResponseEntity<Boolean> isUsernameTaken(@RequestParam String username) {
        boolean isTaken = accountService.isUsernameTaken(username);
        return ResponseEntity.ok(isTaken);
    }

    @GetMapping("/request-details")
    public ResponseEntity<MoneyRequestResponseModel> getRequestDetails(@RequestParam String username, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.getRequestDetails(user.getId(), username));
    }

    @GetMapping("/completed-transaction")
    public ResponseEntity<List<Transaction>> getCompletedTransactions(@RequestParam("accountId") Long accountId) {
        List<Transaction> transactions = accountService.getTransactions(accountId);
        return ResponseEntity.ok(transactions);
    }

}
