package com.finaxis.finaxis.repository;

import com.finaxis.finaxis.entity.Account;
import com.finaxis.finaxis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByCardNumber(String cardNumber);

    boolean existsByUsername(String username);

    Optional<Account> findByUser_Id(Long id);

    @Query("SELECT a FROM Account a WHERE a.user.id = :userId ORDER BY a.id ASC")
    List<Account> findFirstAccountByUserId(@Param("userId") Long userId);

    Optional<Account> findByUsername(String username);

    @Modifying
    @Query("UPDATE Account a SET a.balance = a.balance - :amount WHERE a.id = :accountId")
    void decrementBalance(@Param("accountId") Long accountId, @Param("amount") Double amount);

    @Modifying
    @Query("UPDATE Account a SET a.balance = a.balance + :amount WHERE a.id = :accountId")
    void incrementBalance(@Param("accountId") Long accountId, @Param("amount") Double amount);
}
