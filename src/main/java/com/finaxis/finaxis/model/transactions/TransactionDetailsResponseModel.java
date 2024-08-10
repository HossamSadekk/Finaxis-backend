package com.finaxis.finaxis.model.transactions;

import com.finaxis.finaxis.entity.TransactionStatus;
import com.finaxis.finaxis.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailsResponseModel {
    Long transactionId;
    String senderUsername;
    String receiverUsername;
    Double amount;
    TransactionStatus status;
    TransactionType type;
    String note;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
