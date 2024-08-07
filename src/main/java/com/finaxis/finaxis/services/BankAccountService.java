package com.finaxis.finaxis.services;

import com.finaxis.finaxis.entity.BankAccount;

public interface BankAccountService {
    boolean isPhoneNumberAssociatedWithBank(Long bankId, String phoneNumber);

    boolean isCardNumberAndPasswordValid(Long bankId, String phoneNumber, String cardNumber, String cardPassword);

    BankAccount getBankAccount(String phoneNumber);

}
