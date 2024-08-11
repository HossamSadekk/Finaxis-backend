package com.finaxis.finaxis.controller;

import com.finaxis.finaxis.model.transactions.TransactionDetailsResponseModel;
import com.finaxis.finaxis.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/request")
    public ResponseEntity<TransactionDetailsResponseModel> requestMoney(
            @RequestParam String senderUsername,
            @RequestParam String receiverUsername,
            @RequestParam String amount,
            @RequestParam String note) {
        TransactionDetailsResponseModel response = transactionService.requestMoney(senderUsername, receiverUsername, Double.valueOf(amount), note);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/respond")
    public ResponseEntity<TransactionDetailsResponseModel> respondToMoneyRequest(
            @RequestParam("transactionId") Long transactionId,
            @RequestParam("accept") boolean accept) {

        TransactionDetailsResponseModel response = transactionService.respondToMoneyRequest(transactionId, accept);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/money-requests")
    public ResponseEntity<List<TransactionDetailsResponseModel>> getPendingRequestTransactions(@RequestParam("username") String username) {
        List<TransactionDetailsResponseModel> pendingTransactions = transactionService.getPendingRequestTransactions(username);
        return ResponseEntity.ok(pendingTransactions);
    }
}
