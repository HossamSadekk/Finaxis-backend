package com.finaxis.finaxis.services.imp;

import com.finaxis.finaxis.repository.AccountRepository;
import com.finaxis.finaxis.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public boolean isUsernameTaken(String username) {
        return accountRepository.existsByUsername(username);
    }
}
