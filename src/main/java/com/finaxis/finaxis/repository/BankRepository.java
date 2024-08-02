package com.finaxis.finaxis.repository;

import com.finaxis.finaxis.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
