package com.ssafy.TmT.dto.transaction;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {

	// 그냥 필요할까봐 가져옴
	private Long transactionId;
	
	// 어떤 거래 내역인지 알아야 함.
	private String categoryName;
	
	// 거래 시간 : 시,분,초 까지.
	private Timestamp transactionDatetime;
	
	// 결제 금액 : 얼마를 결제했는지
	private Long amount;
	
	// 잔액	: 얼마가 남았는지
	private Long balanceAfter;
	
	// 거래명	: 거래 내역 이름
	private String merchantName;
	
}
