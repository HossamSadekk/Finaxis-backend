package com.finaxis.finaxis.services;

import com.finaxis.finaxis.model.transactions.TransactionDetailsResponseModel;

import java.util.List;

public interface TransactionService {
    TransactionDetailsResponseModel transferMoney(String senderUsername, String receiverUsername, Double amount, String note);

    TransactionDetailsResponseModel requestMoney(String senderUsername, String receiverUsername, Double amount, String note);

    TransactionDetailsResponseModel respondToMoneyRequest(Long transactionId, boolean accept);

    List<TransactionDetailsResponseModel> getPendingRequestTransactions(String username);
}
