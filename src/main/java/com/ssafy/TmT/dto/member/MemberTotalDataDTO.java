package com.ssafy.TmT.dto.member;

import java.sql.Timestamp;
import java.util.List;

import com.ssafy.TmT.dto.account.AccountDTO;
import com.ssafy.TmT.dto.budget.BudgetDTO;
import com.ssafy.TmT.dto.transaction.BudgetTransactionDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberTotalDataDTO {
    private Long memberId;
    private String memberName;
    private String picture;
    private Timestamp memberCreatedAt;
    private List<AccountDTO> accounts;         // 계좌 정보 리스트
    private BudgetDTO budget;                 // 예산 정보
    private List<BudgetTransactionDTO> transactions; // 거래 내역 리스트
}