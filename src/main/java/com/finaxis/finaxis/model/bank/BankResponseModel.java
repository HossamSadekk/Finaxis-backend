package com.finaxis.finaxis.model.bank;

import com.finaxis.finaxis.entity.Bank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankResponseModel {
    List<Bank> banks;
}
