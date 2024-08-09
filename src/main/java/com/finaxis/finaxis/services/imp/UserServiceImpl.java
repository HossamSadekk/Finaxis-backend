package com.finaxis.finaxis.services.imp;

import com.finaxis.finaxis.entity.Account;
import com.finaxis.finaxis.entity.BankAccount;
import com.finaxis.finaxis.entity.Transaction;
import com.finaxis.finaxis.entity.User;
import com.finaxis.finaxis.exception.AccountAlreadyExistsException;
import com.finaxis.finaxis.exception.UsernameAlreadyExistsException;
import com.finaxis.finaxis.model.user.AccountResponseModel;
import com.finaxis.finaxis.repository.AccountRepository;
import com.finaxis.finaxis.repository.TransactionsRepository;
import com.finaxis.finaxis.repository.UserRepository;
import com.finaxis.finaxis.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionsRepository transactionsRepository;

    @Override
    public Account createAccountForUser(User user, BankAccount bankAccount, String username) {
        Optional<Account> existingAccount = accountRepository.findByCardNumber(bankAccount.getCardNumber());

        if (existingAccount.isPresent()) {
            // Handle the case where the account already exists
            throw new AccountAlreadyExistsException("An account with this card number already exists.");
        }
        if (accountRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException("The username is already taken.");
        }

        Account account = Account.builder()
                .balance(bankAccount.getBalance())
                .cardNumber(bankAccount.getCardNumber())
                .user(user)
                .bank(bankAccount.getBank())
                .username(username)
                .build();

        user.getAccounts().add(account);

        Account savedAccount = accountRepository.save(account);

        userRepository.save(user);

        return savedAccount;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public AccountResponseModel getFirstAccountOfUser(Long userId) {
        List<Account> accounts = accountRepository.findFirstAccountByUserId(userId);

        if (accounts.isEmpty()) {
            throw new EntityNotFoundException("No accounts found for the user");
        }
        Account firstAccount = accounts.get(0);

        List<Transaction> completedTransactions = transactionsRepository.findCompletedTransactionsByAccountId(firstAccount.getId());


        return new AccountResponseModel(
                firstAccount.getId(),
                firstAccount.getBalance(),
                firstAccount.getCardNumber(),
                firstAccount.getUsername(),
                firstAccount.getBank().getId(),
                completedTransactions);
    }
}
