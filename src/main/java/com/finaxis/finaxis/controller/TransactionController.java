package com.finaxis.finaxis.controller;

import com.finaxis.finaxis.model.transactions.TransactionDetailsResponseModel;
import com.finaxis.finaxis.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionDetailsResponseModel> transferMoney(
            @RequestParam String senderUsername,
            @RequestParam String receiverUsername,
            @RequestParam String amount,
            @RequestParam String note) {
        TransactionDetailsResponseModel response = transactionService.transferMoney(senderUsername, receiverUsername, Double.valueOf(amount), note);
        return ResponseEntity.ok(response);
    }
}
