package com.finaxis.finaxis.services.imp;

import com.finaxis.finaxis.entity.BankAccount;
import com.finaxis.finaxis.repository.BankAccountRepository;
import com.finaxis.finaxis.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Override
    public boolean isPhoneNumberAssociatedWithBank(Long bankId, String phoneNumber) {
        return bankAccountRepository.existsByPhoneNumberAndBank_Id(phoneNumber, bankId);
    }

    @Override
    public boolean isCardNumberAndPasswordValid(Long bankId, String phoneNumber, String cardNumber, String cardPassword) {
        BankAccount bankAccount = bankAccountRepository.findByBank_IdAndPhoneNumberAndCardNumber(bankId, phoneNumber, cardNumber)
                .orElse(null);
        return bankAccount != null && bankAccount.getCardPassword().equals(cardPassword);
    }

    @Override
    public BankAccount getBankAccount(String phoneNumber) {
        return bankAccountRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }
}
