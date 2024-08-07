package com.finaxis.finaxis.repository;

import com.finaxis.finaxis.entity.Account;
import com.finaxis.finaxis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByCardNumber(String cardNumber);

    boolean existsByUsername(String username);
}
