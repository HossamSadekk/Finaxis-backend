package com.finaxis.finaxis.services;

import com.finaxis.finaxis.entity.Transaction;
import com.finaxis.finaxis.model.moenyRequest.MoneyRequestResponseModel;

import java.util.List;

public interface AccountService {
    boolean isUsernameTaken(String username);

    MoneyRequestResponseModel getRequestDetails(Long userId, String username);

    List<Transaction> getTransactions(Long accountId);

}
