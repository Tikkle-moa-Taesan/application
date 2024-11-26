package com.ssafy.TmT.dto.member;

import com.ssafy.TmT.dto.transaction.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ModifyBudgetTransactionRequest {
	
	private String merchantName;
	private Long amount;
	private int categoryCode;
	private TransactionType transactionType;

}
