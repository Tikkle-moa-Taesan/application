package com.ssafy.TmT.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long accountId;
    private String accountName;
    private String bankName;
    private Long balance;
    private String accountType;   // ENUM('free', 'savings')
    private LocalDate maturityDate;
    private Double interestRate;
}
