package com.finaxis.finaxis.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bank_account")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(unique = true, nullable = false)
    private String cardNumber;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String cardPassword;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

}