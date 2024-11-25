package com.ssafy.TmT.entity;


import java.time.LocalDateTime;

import com.ssafy.TmT.dto.transaction.TransactionType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Transaction {

	// 거래 ID
	private Long transactionId;	
	
	// 계좌 ID
	private Long accountId;	
	
	//
	private Integer categoryCode;
	
	// 거래 날짜와 시간
	private LocalDateTime transactionDatetime;
	
	// 거래 금액
	private Long amount;
	
	// 거래 후 잔액
	private Long balanceAfter;
	
	// 상호명
	private String merchantName;
	
	// 거래 타입
	private TransactionType transactionType;
	
}
