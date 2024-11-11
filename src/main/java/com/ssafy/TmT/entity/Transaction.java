package com.ssafy.TmT.entity;

import java.time.LocalDate;


public class Transaction {

	// 거래 ID
	private Long transactionId;
	
	// 계좌 ID
	private Long accountId;
	
	// 거래 날짜와 시간
	private LocalDate transactionDate;
	
	// 거래 금액
	private Long amount;
	
	// 거래 후 잔액
	private Long balanceAfter;
	
	// 상호명
	private String merchantName;
	
}
