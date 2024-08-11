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

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public TransactionDetailsResponseModel requestMoney(String senderUsername, String receiverUsername, Double amount, String note) {
        Account senderAccount = accountRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new EntityNotFoundException("Sender account not found"));

        Account receiverAccount = accountRepository.findByUsername(receiverUsername)
                .orElseThrow(() -> new EntityNotFoundException("Receiver account not found"));

        Transaction transaction = Transaction.builder()
                .senderAccount(senderAccount)
                .receiverAccount(receiverAccount)
                .amount(amount)
                .status(TransactionStatus.PENDING)
                .type(TransactionType.REQUEST)
                .note(note)
                .build();

        transactionRepository.save(transaction);

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

    @Override
    @Transactional
    public TransactionDetailsResponseModel respondToMoneyRequest(Long transactionId, boolean accept) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));

        if (transaction.getType() != TransactionType.REQUEST) {
            throw new IllegalArgumentException("This transaction is not a money request");
        }

        if (!transaction.getStatus().equals(TransactionStatus.PENDING)) {
            throw new IllegalStateException("This transaction has already been processed");
        }

        if (accept) {
            Account receiverAccount = transaction.getReceiverAccount();
            if (receiverAccount.getBalance() < transaction.getAmount()) {
                throw new InsufficientFundsException("Insufficient funds in receiver's account");
            }

            accountRepository.decrementBalance(receiverAccount.getId(), transaction.getAmount());
            accountRepository.incrementBalance(transaction.getSenderAccount().getId(), transaction.getAmount());

            transaction.setStatus(TransactionStatus.COMPLETED);
        } else {
            transaction.setStatus(TransactionStatus.FAILED);
        }

        transactionRepository.save(transaction);

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

    @Override
    public List<TransactionDetailsResponseModel> getPendingRequestTransactions(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        List<Transaction> pendingTransactions = transactionRepository
                .findByReceiverAccountAndStatusAndType(account, TransactionStatus.PENDING, TransactionType.REQUEST);

        return pendingTransactions.stream()
                .map(transaction -> new TransactionDetailsResponseModel(
                        transaction.getId(),
                        transaction.getSenderAccount().getUsername(),
                        transaction.getReceiverAccount().getUsername(),
                        transaction.getAmount(),
                        transaction.getStatus(),
                        transaction.getType(),
                        transaction.getNote(),
                        transaction.getCreatedAt(),
                        transaction.getUpdatedAt()))
                .collect(Collectors.toList());
    }
}
