package com.finaxis.finaxis.services;

import com.finaxis.finaxis.model.transactions.TransactionDetailsResponseModel;

public interface TransactionService {
    TransactionDetailsResponseModel transferMoney(String senderUsername, String receiverUsername, Double amount, String note);

}
