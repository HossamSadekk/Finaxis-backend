package com.finaxis.finaxis.model.moenyRequest;

import com.finaxis.finaxis.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoneyRequestResponseModel {
    String senderUsername;
    String senderBalance;
    String receiverName;
    String receiverCardNumber;
}
