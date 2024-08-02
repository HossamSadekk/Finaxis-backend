package com.finaxis.finaxis.services.imp;

import com.finaxis.finaxis.model.bank.BankResponseModel;
import com.finaxis.finaxis.repository.BankRepository;
import com.finaxis.finaxis.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl implements BankService {
    @Autowired
    BankRepository bankRepository;

    @Override
    public BankResponseModel getAllBanks() {
        return BankResponseModel.builder().banks(bankRepository.findAll()).build();
    }
}
