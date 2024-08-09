package com.finaxis.finaxis.model.user;

import com.finaxis.finaxis.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseModel {
    private Long id;
    private Double balance;
    private String cardNumber;
    private String username;
    private Long bankId;
    private List<Transaction> transactions;
}
