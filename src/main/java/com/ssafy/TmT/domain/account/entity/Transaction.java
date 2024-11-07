package com.ssafy.TmT.domain.account.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("transaction_id")
    private Long transactionId;

    @JsonProperty("transaction_type")
    private String transactionType;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("transaction_date")
    private Timestamp transactionDate;

    @JsonProperty("description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
