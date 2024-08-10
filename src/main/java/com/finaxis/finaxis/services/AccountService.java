package com.finaxis.finaxis.services;

import com.finaxis.finaxis.model.moenyRequest.MoneyRequestResponseModel;

public interface AccountService {
    boolean isUsernameTaken(String username);
    MoneyRequestResponseModel getRequestDetails(Long userId,String username);
}
