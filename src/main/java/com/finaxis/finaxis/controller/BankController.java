package com.finaxis.finaxis.controller;

import com.finaxis.finaxis.model.bank.BankResponseModel;
import com.finaxis.finaxis.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/banks")
public class BankController {
    @Autowired
    BankService bankService;

    @GetMapping
    public ResponseEntity<BankResponseModel> getAllBanks() {
        BankResponseModel bankResponseModel = bankService.getAllBanks();
        return ResponseEntity.status(HttpStatus.OK).body(
                bankResponseModel
        );
    }
}
