package com.finaxis.finaxis.services;

import com.finaxis.finaxis.entity.Account;
import com.finaxis.finaxis.entity.BankAccount;
import com.finaxis.finaxis.entity.User;

public interface UserService {
    Account createAccountForUser(User user, BankAccount bankAccount, String username);
    User findUserByUsername(String username);
}
