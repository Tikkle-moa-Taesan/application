package com.ssafy.TmT.dto.admin;

import java.sql.Timestamp;

import com.ssafy.TmT.dto.transaction.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsertTransactionRequest {
	
	private Long transactionId;	// 이게 필요한가
	
	private Long accountId;
	
	private int categoryCode;
	
	private Timestamp transactionDatetime;
	
	private Long amount;
	
	private Long balanceAfter;	// 이거는.. 계산해줘야할듯
	
	private String merchantName;
	
	private TransactionType transactionType;	// 이넘 처리 할까
}
