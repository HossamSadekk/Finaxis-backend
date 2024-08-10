package com.finaxis.finaxis.services.imp;

import com.finaxis.finaxis.entity.Account;
import com.finaxis.finaxis.model.moenyRequest.MoneyRequestResponseModel;
import com.finaxis.finaxis.repository.AccountRepository;
import com.finaxis.finaxis.services.AccountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public boolean isUsernameTaken(String username) {
        return accountRepository.existsByUsername(username);
    }

    @Override
    public MoneyRequestResponseModel getRequestDetails(Long userId, String username) {
        List<Account> accounts = accountRepository.findFirstAccountByUserId(userId);

        if (accounts.isEmpty()) {
            throw new EntityNotFoundException("No accounts found for the user");
        }

        // Assuming you want the first account in the list
        Account senderAccount = accounts.get(0);

        Account receiverAccount = accountRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Receiver account not found"));

        return new MoneyRequestResponseModel(
                senderAccount.getUsername(),
                senderAccount.getBalance().toString(),
                receiverAccount.getUsername(),
                receiverAccount.getCardNumber()
        );
    }

}
