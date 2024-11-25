package com.ssafy.TmT.dto.member;

import com.ssafy.TmT.dto.transaction.TransactionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyBudgetTransactionRequest {
	
	private String merchantName;
	private Long amount;
	private int categoryCode;
	private TransactionType transactionType;

}
