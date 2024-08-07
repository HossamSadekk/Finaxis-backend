package com.finaxis.finaxis.repository;

import com.finaxis.finaxis.entity.Bank;
import com.finaxis.finaxis.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    boolean existsByPhoneNumberAndBank_Id(String phoneNumber, Long id);

    Optional<BankAccount> findByBank_IdAndPhoneNumberAndCardNumber(Long id, String phoneNumber, String cardNumber);

    Optional<BankAccount> findByPhoneNumber(String phoneNumber);
}
