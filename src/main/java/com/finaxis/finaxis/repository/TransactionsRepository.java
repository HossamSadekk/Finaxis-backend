package com.finaxis.finaxis.repository;

import com.finaxis.finaxis.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transaction,Long> {
    @Query("SELECT t FROM Transaction t WHERE (t.senderAccount.id = :accountId OR t.receiverAccount.id = :accountId) AND t.status = 'COMPLETED'")
    List<Transaction> findCompletedTransactionsByAccountId(@Param("accountId") Long accountId);
}
