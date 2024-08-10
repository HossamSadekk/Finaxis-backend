package com.finaxis.finaxis.services.imp;

import com.finaxis.finaxis.entity.Account;
import com.finaxis.finaxis.entity.Transaction;
import com.finaxis.finaxis.entity.TransactionStatus;
import com.finaxis.finaxis.entity.TransactionType;
import com.finaxis.finaxis.exception.InsufficientFundsException;
import com.finaxis.finaxis.model.transactions.TransactionDetailsResponseModel;
import com.finaxis.finaxis.repository.AccountRepository;
import com.finaxis.finaxis.repository.TransactionsRepository;
import com.finaxis.finaxis.services.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImp implements TransactionService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionsRepository transactionRepository;

    @Override
    @Transactional
    public TransactionDetailsResponseModel transferMoney(String senderUsername, String receiverUsername, Double amount, String note) {
        // Find sender and receiver accounts
        Account senderAccount = accountRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new EntityNotFoundException("Sender account not found"));

        Account receiverAccount = accountRepository.findByUsername(receiverUsername)
                .orElseThrow(() -> new EntityNotFoundException("Receiver account not found"));

        // Check if sender has enough balance
        if (senderAccount.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds in sender's account");
        }

        // Decrement sender balance and increment receiver balance
        accountRepository.decrementBalance(senderAccount.getId(), amount);
        accountRepository.incrementBalance(receiverAccount.getId(), amount);

        // Create a transaction record
        Transaction transaction = Transaction.builder()
                .senderAccount(senderAccount)
                .receiverAccount(receiverAccount)
                .amount(amount)
                .status(TransactionStatus.COMPLETED)
                .type(TransactionType.TRANSFER)
                .note(note)
                .build();

        transactionRepository.save(transaction);

        // Return transaction details
        return new TransactionDetailsResponseModel(
                transaction.getId(),
                transaction.getSenderAccount().getUsername(),
                transaction.getReceiverAccount().getUsername(),
                transaction.getAmount(),
                transaction.getStatus(),
                transaction.getType(),
                transaction.getNote(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }
}
